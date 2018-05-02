package readersWritersApp.business;

import org.springframework.stereotype.Service;
import readersWritersApp.persistence.repositories.ArticleRepository;

import javax.inject.Inject;

@Service()
public class ArticleService {

    @Inject
    ArticleRepository articleRepository;
}
