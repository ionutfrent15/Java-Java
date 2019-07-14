package repository;

import model.Produs;
import model.Stoc;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class StocRepository {
    private SessionFactory sessionFactory;

    public StocRepository() {
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void save(Stoc stoc){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                session.save(stoc);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null)
                    tx.rollback();
            }
        }
    }

    public Stoc findOne(Integer id){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Produs produs = session.createQuery("from Produs where id = :id", Produs.class)
                        .setInteger("id", id)
                        .getSingleResult();
                Stoc stoc = session.createQuery("from Stoc where produs = :id", Stoc.class)
                        .setInteger("id", id)
                        .getSingleResult();
//                stoc.setProdus(produs);
                tx.commit();
                return stoc;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return null;
    }

    public List<Stoc> findAll(){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                List<Stoc> stocList = session.createQuery("from Stoc ", Stoc.class)
                        .list();
                return stocList;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return null;
    }

    public void delete(int id){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Produs produs = session.createQuery("from Produs where id = :id", Produs.class)
                        .setInteger("id", id)
                        .getSingleResult();
                Stoc stoc = session.createQuery("from Stoc where produs = :id", Stoc.class)
                        .setInteger("id", id)
                        .getSingleResult();
                session.delete(stoc);
                session.delete(produs);
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
    }

    public void update(Stoc stoc){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Stoc oldStoc = session.load(Stoc.class, stoc.getId());
                oldStoc.setProdus(stoc.getProdus());
                oldStoc.setCantitate(stoc.getCantitate());
                tx.commit();
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
    }
}
