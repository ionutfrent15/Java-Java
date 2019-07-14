package service;

import dto.ProdusDTO;
import model.Admin;
import model.Produs;
import model.Stoc;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import repository.AdminRepository;
import repository.ProdusRepository;
import repository.StocRepository;
import repository.UserRespository;
import utils.EventType;
import utils.IClient;
import utils.IServer;
import utils.UpdateEvent;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class Server implements IServer {
    private AdminRepository adminRepository;
    private UserRespository userRespository;
    private ProdusRepository produsRepository;
    private StocRepository stocRepository;

    static SessionFactory sessionFactory;

    private List<IClient> loggedClients = new ArrayList<>();

    public Server(AdminRepository adminRepository, UserRespository userRespository, ProdusRepository produsRepository, StocRepository stocRepository) {
        try{
            initialize();
            this.adminRepository = adminRepository;
            this.userRespository = userRespository;
            this.produsRepository = produsRepository;
            this.stocRepository = stocRepository;
            adminRepository.setSessionFactory(sessionFactory);
            userRespository.setSessionFactory(sessionFactory);
            produsRepository.setSessionFactory(sessionFactory);
            stocRepository.setSessionFactory(sessionFactory);
        } catch (Exception e){
            System.err.println("Exception " + e);
            e.printStackTrace();
        }
//        finally {
//            close();
//        }
    }

    static void initialize() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources( registry ).buildMetadata().buildSessionFactory();
        }
        catch (Exception e) {
            StandardServiceRegistryBuilder.destroy( registry );
        }
    }

    static void close(){
        if (sessionFactory != null) {
            sessionFactory.close();
        }

    }

    private void notifyAllClients(UpdateEvent updateEvent) throws RemoteException {
        for (IClient loggedClient : loggedClients) {
            loggedClient.update(updateEvent);
        }
    }

    @Override
    public boolean loginAdmin(Admin admin) throws RemoteException {
        Admin found = adminRepository.findOne(admin.getUsername());
        if(found == null){
            return false;
        }
        return found.getPassword().equals(admin.getPassword());
    }

    @Override
    public boolean loginUser(User user) throws RemoteException {
        User found = userRespository.findOne(user.getUsername());
        if(found == null){
            return false;
        }
        return found.getPassword().equals(user.getPassword());
    }

    @Override
    public void addClient(IClient client) throws RemoteException {
        loggedClients.add(client);
    }

    @Override
    public List<Produs> getAllProdus() throws RemoteException {
        return produsRepository.findAll();
    }

    private ProdusDTO createDTO(Stoc stoc){
        return new ProdusDTO(stoc.getProdus().getId(), stoc.getProdus().getDenumire(),
                stoc.getProdus().getPret(),
                stoc.getCantitate());
    }

    @Override
    public List<ProdusDTO> getAllProdusDTO() throws RemoteException {
        List<Stoc> stocs = stocRepository.findAll();
        return stocs.stream()
            .map(stoc -> createDTO(stoc))
            .collect(Collectors.toList());
    }

    @Override
    public void adaugaStoc(String denumire, double pret, int cantitate) throws RemoteException {
        Produs produs = new Produs(denumire, "", pret);
        Stoc stoc = new Stoc(produs, cantitate);
        stocRepository.save(stoc);
        notifyAllClients(new UpdateEvent(EventType.ADD, null, createDTO(stoc)));
    }

    @Override
    public void stergeProdus(int id) throws RemoteException {
        Stoc oldStoc = stocRepository.findOne(id);
        stocRepository.delete(id);
        notifyAllClients(new UpdateEvent(EventType.DELETE, createDTO(oldStoc), null));
    }

    @Override
    public void actualizeazaProdus(int id, String denumire, double pret, int cantitate) throws RemoteException {
        Stoc oldStoc = stocRepository.findOne(id);
        Produs produs = new Produs(denumire, "", pret);
        produs.setId(id);
        Stoc stoc = new Stoc(produs, cantitate);
        stoc.setId(oldStoc.getId());

        stocRepository.update(stoc);
        notifyAllClients(new UpdateEvent(EventType.UPDATE, createDTO(oldStoc), createDTO(stoc)));
    }

    @Override
    public void comandaProdus(int id, int cantitate) throws RemoteException {
        Produs produs = produsRepository.findOne(id);
        actualizeazaProdus(id, produs.getDenumire(), produs.getPret(), cantitate);
    }

    @Override
    public void logout(IClient client) throws RemoteException {
        loggedClients.remove(client);
    }

}
