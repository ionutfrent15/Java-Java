package repository;

import model.Produs;
import model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ProdusRepository {
    private SessionFactory sessionFactory;

    public ProdusRepository() {
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Produs produs){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(produs);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    public Produs findOne(Integer id){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Produs produs = session.createQuery("from Produs where id = :id", Produs.class)
                        .setInteger("id", id)
                        .getSingleResult();
                tx.commit();
                return produs;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return null;
    }

    public List<Produs> findAll(){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Produs> produsList = session.createQuery("from Produs", Produs.class)
                        .list();
                return produsList;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return null;
    }

}
