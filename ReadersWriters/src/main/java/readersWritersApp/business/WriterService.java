package readersWritersApp.business;

import org.springframework.stereotype.Service;
import readersWritersApp.persistence.entities.Writer;
import readersWritersApp.persistence.repositories.WriterRepository;

import javax.inject.Inject;

@Service()
public class WriterService {

    @Inject
    WriterRepository writerRepository;

    public Writer getByUserName (String username){

         return writerRepository.findAll().get(0);
    }
}
