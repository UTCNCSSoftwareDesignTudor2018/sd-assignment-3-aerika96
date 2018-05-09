package readersWritersApp.persistence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import readersWritersApp.persistence.entities.Writer;

@Repository
public interface WriterRepositoryInterface {


    public Writer findById(Integer id);
    public Writer findByUsername(String username);
    public void updateWriter(Writer writer);

}
