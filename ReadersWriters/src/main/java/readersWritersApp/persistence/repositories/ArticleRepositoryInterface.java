package readersWritersApp.persistence.repositories;

import org.springframework.stereotype.Repository;
import readersWritersApp.persistence.entities.Article;
import readersWritersApp.persistence.entities.Writer;

import java.util.List;

@Repository
public interface ArticleRepositoryInterface {

    public List findAll();
    public Article findByTitle(String title);
    public Article updateArticle (Article article);
    public Article insertArticle (Article article, Writer writer,List<Article> related);


}
