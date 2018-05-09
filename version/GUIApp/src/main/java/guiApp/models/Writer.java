package guiApp.models;


import java.util.List;

public class Writer {
    private Integer ID;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String institution;
    private List<Article> articles;

    public Writer(){
    }

    public Writer(String username, String password, String firstName, String lastName, String institution){
        this.username = username;
        this.password = password;
        this.firstName =  firstName;
        this.lastName = lastName;
        this.institution = institution;
    }



    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }


    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }


    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }
}
