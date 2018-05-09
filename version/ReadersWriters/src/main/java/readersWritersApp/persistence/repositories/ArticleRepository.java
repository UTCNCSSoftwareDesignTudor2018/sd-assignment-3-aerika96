package readersWritersApp.persistence.repositories;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import org.hibernate.Session;
import readersWritersApp.clientsServer.protocol.CommunicationProtocol;
import readersWritersApp.clientsServer.protocol.Observable;
import readersWritersApp.clientsServer.server.MultiServerThread;
import readersWritersApp.connection.ConnectionFactory;
import readersWritersApp.persistence.entities.Article;
import readersWritersApp.persistence.entities.Writer;

import javax.sound.midi.Instrument;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArticleRepository implements ArticleRepositoryInterface {

    private static final String selectAll = "SELECT * FROM articles";
    private static final String selectStatementString = "SELECT * FROM articles WHERE";
    private static final String updateArticleString = "UPDATE articles SET body = ?, title = ?, articleAbstract = ? WHERE ID= ?";
    private static final String insertArtcileStatement ="INSERT INTO articles(title,articleAbstract,body, writer) VALUES (?,?,?,?)";

    private Connection connection;
    private ResultSet resultSet = null;
    private java.sql.Statement statement2 = null;
    private java.sql.PreparedStatement statement = null;
    private CallableStatement callableStatement = null;

    public ArticleRepository(){
    }

    @Override
    public synchronized List findAll() {
        List<Article> result = new ArrayList<Article>();
        try{

            connection = (Connection) ConnectionFactory.getConnection("JDBCConnection");
            statement2 = connection.createStatement();
            resultSet = statement2.executeQuery(selectAll);
            while(resultSet.next()){
                Article article = new Article(
                        resultSet.getInt("ID"),
                        resultSet.getString("title"),
                        resultSet.getString("articleAbstract"),
                        resultSet.getString("body"),
                        new WriterRepository().findById(resultSet.getInt("writer"))
                );
                result.add(article);
            }
            connection.close();
            statement2.close();
            resultSet.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }

    public synchronized Article findById(Integer id) {
        Article result = null;
        try{

            connection = (Connection) ConnectionFactory.getConnection("JDBCConnection");
            statement = connection.prepareStatement(selectStatementString+" ID= "+id);
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                result = new Article(
                        resultSet.getInt("ID"),
                        resultSet.getString("title"),
                        resultSet.getString("articleAbstract"),
                        resultSet.getString("body"),
                        new WriterRepository().findById(resultSet.getInt("writer"))
                );
            }
            connection.close();
            resultSet.close();
            statement.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return result;
    }

    @Override
    public synchronized Article findByTitle(String title) {
        Article result = null;
        try{

            connection = (Connection) ConnectionFactory.getConnection("JDBCConnection");
            statement = connection.prepareStatement(selectStatementString+" title= \""+title+"\"");
            resultSet = statement.executeQuery();
            if(resultSet.next()){
                result = new Article(
                        resultSet.getInt("ID"),
                        resultSet.getString("title"),
                        resultSet.getString("articleAbstract"),
                        resultSet.getString("body"),
                        new WriterRepository().findById(resultSet.getInt("writer"))
                );
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
    public synchronized Article updateArticle(Article article) {
        try {

            connection = (Connection) ConnectionFactory.getConnection("JDBCConnection");
            statement =  connection.prepareStatement(updateArticleString);
            statement.setString(1,article.getBody());
            statement.setString(2,article.getTitle());
            statement.setString(3,article.getArticleAbstract());
            statement.setInt(4,article.getID());

            statement.executeUpdate();
            connection.close();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return article;

    }

    @Override
    public synchronized Article insertArticle(Article article, Writer writer, List<Article> related) {
        Article art= null;
        try{

            connection = (Connection) ConnectionFactory.getConnection("JDBCConnection");
            statement = connection.prepareStatement(insertArtcileStatement,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,article.getTitle());
            statement.setString(2,article.getArticleAbstract());
            statement.setString(3,article.getBody());
            statement.setInt(4,writer.getID());
            statement.executeUpdate();

            resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                int insertedId = resultSet.getInt(1);
                 art = findById(insertedId);
            }
            connection.close();
            statement.close();
            resultSet.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return art;
    }


}
