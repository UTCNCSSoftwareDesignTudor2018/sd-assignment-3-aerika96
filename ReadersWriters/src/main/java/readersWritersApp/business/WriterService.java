package readersWritersApp.business;

import org.springframework.stereotype.Service;
import readersWritersApp.persistence.entities.Writer;
import readersWritersApp.persistence.repositories.WriterRepository;
import readersWritersApp.persistence.repositories.WriterRepositoryInterface;


@Service()
public class WriterService {

   private WriterRepositoryInterface writerRepository;

   public WriterService(){
       writerRepository = new WriterRepository();
   }

   public Writer getById(Integer id){

       return writerRepository.findById(id);
   }

    public Writer getByUserName (String username){

         return writerRepository.findByUsername(username);
    }

    public Writer updateWriter (Writer wr){
       Writer writer = this.getById(wr.getID());
       writer.setFirstName(wr.getFirstName());
       writer.setLastName(wr.getLastName());
       writer.setInstitution(wr.getInstitution());
       writer.setUsername(wr.getUsername());

       writerRepository.updateWriter(writer);
       return writerRepository.findById(writer.getID());

    }
}
