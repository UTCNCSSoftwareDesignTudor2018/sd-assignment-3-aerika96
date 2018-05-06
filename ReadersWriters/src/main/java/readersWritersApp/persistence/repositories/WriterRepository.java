package readersWritersApp.persistence.repositories;

import org.hibernate.Session;
import readersWritersApp.connection.ConnectionFactory;
import readersWritersApp.persistence.entities.Writer;

public class WriterRepository implements WriterRepositoryInterface {
    private Session session;

    @Override
    public Writer findById(Integer id){
        session = (Session) ConnectionFactory.getConnection("hibernateconnection");
        Writer writer = session.get(Writer.class,id);
        session.close();
        return writer;
    }

    @Override
    public Writer findByUsername(String username) {
        session = (Session) ConnectionFactory.getConnection("hibernateconnection");

        String hql = "SELECT writer FROM Writer writer WHERE writer.username =:username";

        Writer writer = (Writer) session.createQuery(hql).setParameter("username",username).uniqueResult();
        session.close();
        return writer;

    }

    @Override
    public void updateWriter(Writer wr) {


        session = (Session) ConnectionFactory.getConnection("hibernateconnection");
        session.beginTransaction();
        session.update(wr);
        session.getTransaction().commit();
        session.close();
    }
}
