package readersWritersApp.clientsServer.protocol;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import readersWritersApp.MultiServer;
import readersWritersApp.business.ArticleService;
import readersWritersApp.business.WriterService;
import readersWritersApp.clientsServer.server.MultiServerThread;
import readersWritersApp.persistence.entities.Article;
import readersWritersApp.persistence.entities.Writer;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class CommunicationProtocol {

   private final WriterService writerService;
   private final ArticleService articleService;
   private ObjectMapper mapper;
   private MultiServerThread server;

    private static final int WAITING = 0;
    private static final int LOG_IN =1;
    private static final int CHANGE_PERSONAL_DATA=2;
    private static final int ALL_ARTICLES = 3;
    private static final int ARTICLE_BY_TITLE = 4;
    private static final int ADD_ARTICLE = 5;
    private static final int WRITER_OF_ARTICLE =6;
    private static final int UPDATE_ARTICLE =7;
    private static final int DELETE_BY_TITLE = 8;


    private int state = WAITING;

    public CommunicationProtocol(MultiServerThread server){
        writerService = new WriterService();
        articleService = new ArticleService();
        mapper = new ObjectMapper();
        this.server = server;
    }



    public String setState(String state){
        if(state.equalsIgnoreCase("LOG IN")){
            this.state = LOG_IN;
            return state;
        }
        else if(state.equalsIgnoreCase("CHANGE PERSONAL DATA")){
            this.state = CHANGE_PERSONAL_DATA;
            return state;
        }
        else if(state.equalsIgnoreCase("ALL ARTICLES")){
            this.state = ALL_ARTICLES;
            return state;
        }
        else if(state.equalsIgnoreCase("ARTICLE BY TITLE")){
            this.state = ARTICLE_BY_TITLE;
            return state;
        }
        else if(state.equalsIgnoreCase("ADD NEW ARTICLE")){
            this.state = ADD_ARTICLE;
            return state;
        }
        else if(state.equalsIgnoreCase("WRITER OF ARTICLE")){
            this.state = WRITER_OF_ARTICLE;
            return state;

        }
        else if(state.equalsIgnoreCase("UPDATE")){
            this.state = UPDATE_ARTICLE;
            return state;
        }
        else if(state.equalsIgnoreCase("DELETE BY TITLE")){
            this.state = DELETE_BY_TITLE;
            return state;
        }
        return "problem";
    }

    public String processInput(String theInput)  {
       String theOutput = null;
        try {
        if(state == LOG_IN){

                String jsonInString = mapper.writeValueAsString(writerService.getByUserName(theInput));
                theOutput = jsonInString;

        }
        else if(state == CHANGE_PERSONAL_DATA){

            Writer writer =  mapper.readValue(theInput,Writer.class);
            String jsonInString = mapper.writeValueAsString(writerService.updateWriter(writer));
            theOutput = jsonInString;

        }
        else if(state == ALL_ARTICLES){
            List<Article> articles =  new ArrayList<Article>( articleService.getAll());
           // mapper.enable(SerializationFeature.INDENT_OUTPUT);
            String jsonString = mapper.writeValueAsString(articles);
            theOutput = jsonString;

        }
        else if(state == ARTICLE_BY_TITLE){
            Article article = articleService.getByTitle(theInput);
            String jsonString = mapper.writeValueAsString(article);
            theOutput = jsonString;
        }
        else if(state == ADD_ARTICLE){
            Article article  = mapper.readValue(theInput, Article.class);
            Writer writer = mapper.readValue(server.readFromClient(),Writer.class);
            List<String> titles = mapper.readValue(server.readFromClient(), TypeFactory.defaultInstance().constructCollectionType(List.class, String.class));
            List<Article> related = new ArrayList<Article>();

          /*  for (String t: titles
                 ) {
                related.add(articleService.getByTitle(t));
            }
            // Article newArticle = new Article(article.getTitle(),article.getArticleAbstract(),article.getBody(),writer,related);
*/
            articleService.addNewArticle(article, writer, related);
            theOutput = new String("Done");
        }
        else if(state == WRITER_OF_ARTICLE){
            Article article = mapper.readValue(theInput, Article.class);
            Writer writer = articleService.getByTitle(article.getTitle()).getWriter();
            String jsonString = mapper.writeValueAsString(writer);
            theOutput = jsonString;

        }
        else if(state == UPDATE_ARTICLE){
            Article article = mapper.readValue(theInput, Article.class);
            Article art = articleService.getByTitle(article.getTitle());
            art.setBody(article.getBody());
            articleService.updateArticle(art);
            theOutput = mapper.writeValueAsString(art);
        }
        else if(state == DELETE_BY_TITLE){
            Article art = articleService.getByTitle(theInput);
            articleService.deleteArticle(art);
        }
        }catch (NullPointerException e){
            theOutput = null;
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        }  catch (IOException e) {
            e.printStackTrace();
        }


        return theOutput;
    }


}
