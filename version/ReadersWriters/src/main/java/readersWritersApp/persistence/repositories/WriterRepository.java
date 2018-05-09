package readersWritersApp.persistence.repositories;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import org.hibernate.Session;
import org.hibernate.annotations.Synchronize;
import readersWritersApp.connection.ConnectionFactory;
import readersWritersApp.persistence.entities.Writer;


import java.sql.*;


public class WriterRepository implements WriterRepositoryInterface {

    private static final String selectAll = "SELECT * FROM writers";
    private static final String selectStatementString = "SELECT * FROM writers WHERE";
    private static final String updateWriterString = "UPDATE writers SET firstName = ?, lastName = ?, institution = ?, username = ? WHERE ID = ?";

    private Connection connection;
    private ResultSet resultSet = null;
    private java.sql.PreparedStatement statement = null;
    private CallableStatement callableStatement = null;

    public WriterRepository(){
    }


    @Override
    public synchronized Writer findById(Integer id) {
        Writer result = null;
        try{

            connection = (Connection) ConnectionFactory.getConnection("JDBCConnection");
            statement = connection.prepareStatement(selectStatementString+" id="+id);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                result = new Writer(resultSet.getInt("ID"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("institution"));
            }
            connection.close();
            statement.close();
            resultSet.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public synchronized Writer findByUsername(String username) {
        Writer result = null;
        try{

            connection = (Connection) ConnectionFactory.getConnection("JDBCConnection");
            statement = connection.prepareStatement(selectStatementString+" username= \""+username+"\"");
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                result = new Writer(resultSet.getInt("ID"),resultSet.getString("username"),resultSet.getString("password"),resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("institution"));
            }
            connection.close();
            statement.close();
            resultSet.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public synchronized void updateWriter(Writer writer) {
        try {

            connection = (Connection) ConnectionFactory.getConnection("JDBCConnection");
            statement =  connection.prepareStatement(updateWriterString);
            statement.setString(1,writer.getFirstName());
            statement.setString(2,writer.getLastName());
            statement.setString(3,writer.getInstitution());
            statement.setString(4,writer.getUsername());
            statement.setInt(5,writer.getID());

            statement.executeUpdate();
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
