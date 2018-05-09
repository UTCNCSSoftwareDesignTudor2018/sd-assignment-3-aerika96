package readersWritersApp.business;

import org.springframework.stereotype.Service;
import readersWritersApp.clientsServer.protocol.CommunicationProtocol;
import readersWritersApp.clientsServer.protocol.Observable;
import readersWritersApp.clientsServer.server.MultiServerThread;
import readersWritersApp.persistence.entities.Article;
import readersWritersApp.persistence.entities.Writer;
import readersWritersApp.persistence.repositories.ArticleRepository;
import readersWritersApp.persistence.repositories.ArticleRepositoryInterface;
import readersWritersApp.persistence.repositories.WriterRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service()
public class ArticleService implements Observable {


    private static final ArticleService articleService = new ArticleService();

    List<CommunicationProtocol> observers;


    private ArticleRepository articleRepository ;
    private WriterRepository writerRepository;

    private ArticleService(){
        articleRepository = new ArticleRepository();
        observers = new ArrayList<>();
    }

    public synchronized static ArticleService getArticleService(){
        return articleService;
    }

    public synchronized List<Article> getAll(){
        return articleRepository.findAll();
    }

    public synchronized Article getByTitle(String title){
        return articleRepository.findByTitle(title);
    }

    public synchronized Article addNewArticle(Article article, Writer writer, List<Article>related){


       // writerRepository.updateWriter(article.getWriter());
        articleRepository.insertArticle(article, writer, related);
        /*for (Article a:article.getRelated()
             ) {
            articleRepository.updateArticle(a);
        }
        */
        List<Article> arts= articleRepository.findAll();
        notifyObservers(arts,"added");
        return article;
    }

    public synchronized void updateArticle(Article article){
        articleRepository.updateArticle(article);
        notifyObservers(article,"edited");
    }

    public synchronized void deleteArticle (Article art){

        art.setBody(null);
        art.setRelated(null);
        art.setRelatedTo(null);
        art.setTitle(null);
        art.setArticleAbstract(null);
        articleRepository.updateArticle(art);
        List<Article> arts = articleRepository.findAll();
        notifyObservers(arts,"deleted");
    }

    @Override
    public synchronized void notifyObservers(Object data, String partCase) {
        for (CommunicationProtocol t:observers
                ) {
            t.notifyMyServer(data,partCase);
        }
    }

    @Override
    public synchronized void addObserver(Object o) {
        observers.add((CommunicationProtocol)o);
        System.out.println("Observer added");
    }
}
