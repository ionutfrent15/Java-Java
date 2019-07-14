package repository;

import model.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AdminRepository {

    private SessionFactory sessionFactory;

    public AdminRepository() {
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Admin findOne(String username){
        try(Session session = sessionFactory.openSession()) {
            Transaction tx = null;
            try {
                tx = session.beginTransaction();
                Admin admin = session.createQuery("from Admin where id = :user", Admin.class)
                                .setString("user", username)
                                .getSingleResult();

                return admin;
            } catch (RuntimeException ex) {
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
        return null;
    }
}
