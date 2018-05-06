package readersWritersApp.connection;

import org.hibernate.Session;
import readersWritersApp.connection.HibernateConnection;

import java.sql.*;

public class ConnectionFactory {

    private static Connection connection;
    private static Session session;

    public static Object getConnection(String connectionType){
        if(connectionType == null){
            connection = null;
        }
        else if(connectionType.equalsIgnoreCase("HibernateConnection")){
            session = HibernateConnection.getSession();
            return session;
        }
        return connection;
    }

}
