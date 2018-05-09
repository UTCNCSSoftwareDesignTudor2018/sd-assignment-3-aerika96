package readersWritersApp.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnection {

    private static JDBCConnection singleInstance = new JDBCConnection();

    private JDBCConnection(){
        try{
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    /*create tables here*/
    private Connection createConnection(){
        Connection connection = null;
        try{
            connection = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/article_db?user=root&password=informatika96");
        }
        catch(SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    public static Connection getConnection(){
        return singleInstance.createConnection();
    }

    public static void close(Connection connection){
        if(connection!=null){
            try{
                connection.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
    public static void close(Statement statement){
        if(statement!=null){
            try{
                statement.close();
            }
            catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
