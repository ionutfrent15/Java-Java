package utils;

import dto.ProdusDTO;
import model.Admin;
import model.Produs;
import model.Stoc;
import model.User;

import java.rmi.RemoteException;
import java.util.List;

public interface IServer {
    boolean loginAdmin(Admin admin) throws RemoteException;
    boolean loginUser(User user) throws RemoteException;
    void addClient(IClient client) throws RemoteException;
    List<Produs> getAllProdus() throws RemoteException;
    List<ProdusDTO> getAllProdusDTO() throws RemoteException;
    void adaugaStoc(String denumire, double pret, int cantitate) throws RemoteException;
    void stergeProdus(int id) throws RemoteException;
    void actualizeazaProdus(int id, String denumire, double pret, int cantitate) throws RemoteException;
    void comandaProdus(int id, int cantitate) throws RemoteException;
    void logout(IClient client) throws RemoteException;
}
