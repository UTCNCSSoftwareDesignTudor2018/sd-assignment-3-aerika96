package readersWritersApp.persistence.repositories;

import org.hibernate.Session;
import readersWritersApp.connection.ConnectionFactory;
import readersWritersApp.persistence.entities.Article;
import readersWritersApp.persistence.entities.Writer;

import java.util.List;

public class ArticleRepository implements ArticleRepositoryInterface {
    private Session session;

    @Override
    public List<Article> findAll() {
        this.session =(Session) ConnectionFactory.getConnection("hibernateconnection");
        List result = session.createQuery("from Article ").list();
        session.close();
        return result;

    }

    @Override
    public Article findByTitle(String title) {
        this.session =(Session) ConnectionFactory.getConnection("hibernateconnection");
        String hql = "SELECT article FROM Article article WHERE article.title =:title";
        Article article = (Article) session.createQuery(hql).setParameter("title",title).uniqueResult();
        session.close();
        return  article;
    }

    @Override
    public Article insertArticle(Article article, Writer writer, List<Article> related) {
        Session sess =(Session) ConnectionFactory.getConnection("hibernateconnection");
        Article newArticle = new Article(article.getTitle(),article.getArticleAbstract(),article.getBody(),writer,related);
      //  session.beginTransaction();



        sess.clear();
        sess.save(newArticle);
        writer.getArticles().add(newArticle);
        sess.update(writer);
        for (Article a: newArticle.getRelated()
                ) {
            a.getRelated().add(newArticle);
            a.getRelatedTo().add(newArticle);
            sess.update(a);
        }

     /*   for (Article a: newArticle.getRelated()
                ) {
        }*/
      //  session.getTransaction().commit();
        sess.close();
        return newArticle;

    }

    @Override
    public Article updateArticle(Article article) {
        this.session =(Session) ConnectionFactory.getConnection("hibernateconnection");
        session.beginTransaction();
        session.update(article);
        session.getTransaction().commit();
        session.close();
        return article;
    }


}
