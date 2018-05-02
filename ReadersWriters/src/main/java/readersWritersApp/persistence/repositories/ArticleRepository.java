package readersWritersApp.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import readersWritersApp.persistence.entities.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {


}
