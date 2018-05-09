package readersWritersApp.persistence.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Table(name = "writers")
@JsonIgnoreProperties("inspection")
@Transactional
public class Writer {
   private Integer ID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String institution;
    private List<Article> articles;

    public Writer(){}

    public Writer(String username, String password, String firstName, String lastName){
        this.username = username;
        this.password = password;
        this.firstName =  firstName;
        this.lastName = lastName;
    }

    public Writer(Integer id,String username, String password, String firstName, String lastName, String institution){
        this.ID =id;
        this.username = username;
        this.password = password;
        this.firstName =  firstName;
        this.lastName = lastName;
        this.institution = institution;
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Column
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Column
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Column
    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    @OneToMany (mappedBy = "writer", fetch = FetchType.EAGER)
    @JsonManagedReference
    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
