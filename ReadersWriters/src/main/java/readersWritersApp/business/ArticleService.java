package readersWritersApp.business;

import org.springframework.stereotype.Service;
import readersWritersApp.persistence.entities.Article;
import readersWritersApp.persistence.entities.Writer;
import readersWritersApp.persistence.repositories.ArticleRepository;
import readersWritersApp.persistence.repositories.ArticleRepositoryInterface;
import readersWritersApp.persistence.repositories.WriterRepository;

import javax.inject.Inject;
import java.util.List;

@Service()
public class ArticleService {

    private ArticleRepository articleRepository;
    private WriterRepository writerRepository;

    public ArticleService(){
        this.articleRepository = new ArticleRepository();
    }

    public List<Article> getAll(){
        return articleRepository.findAll();
    }

    public Article getByTitle(String title){
        return articleRepository.findByTitle(title);
    }

    public Article addNewArticle(Article article, Writer writer, List<Article>related){


       // writerRepository.updateWriter(article.getWriter());
        articleRepository.insertArticle(article, writer, related);
        /*for (Article a:article.getRelated()
             ) {
            articleRepository.updateArticle(a);
        }
        */
        return article;
    }
}
