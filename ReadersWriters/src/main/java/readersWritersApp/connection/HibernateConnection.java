package readersWritersApp.connection;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateConnection {
    private static HibernateConnection singleInstance = new HibernateConnection();
    private static Session session;

    private HibernateConnection(){}

    private Session createSession(){
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        return session;
    }

    public static Session getSession() {
        return singleInstance.createSession();
    }
}
