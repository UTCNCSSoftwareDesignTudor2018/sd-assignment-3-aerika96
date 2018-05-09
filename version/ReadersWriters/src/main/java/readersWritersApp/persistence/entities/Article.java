package readersWritersApp.persistence.entities;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Synchronize;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table (name = "articles")
@Transactional
public class Article {

    private Integer ID;
    private String title;
    private String articleAbstract;
    private String body;
    private Writer writer;
    private List<Article> related;
    private List<Article> relatedTo;

    public Article(){}

    public Article(String title, String articleAbstract, String body){
        this.title = title;
        this.articleAbstract =  articleAbstract;
        this.body = body;

    }

    public Article(String title, String articleAbstract, String body, List<Article> related){
        this.title = title;
        this.articleAbstract =  articleAbstract;
        this.body = body;
        this.related = related;
        this.relatedTo = related;

    }

    public Article(Integer id, String title, String articleAbstract, String body, Writer writer){
        this.ID = id;
        this.title = title;
        this.articleAbstract =  articleAbstract;
        this.body = body;
        this.writer=writer;

    }

    public Article(String title, String articleAbstract, String body, Writer writer, List<Article> related){
        this.title = title;
        this.articleAbstract =  articleAbstract;
        this.body = body;
        this.writer = writer;
        this.related = related;
        this.relatedTo = related;

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    @Column
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column
    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    @Column
    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "writer")
    @NotNull
    @JsonBackReference
    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }


   /* @JoinTable(name="tbl_related_articles",
            joinColumns=@JoinColumn(name="articleId"),
            inverseJoinColumns=@JoinColumn(name="relatedId")
    )*/
   @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    public List<Article> getRelated() {
        return related;
    }

    public void setRelated(List<Article> related) {
        this.related = related;
    }


   /* @JoinTable(name="tbl_related_articles",
            joinColumns=@JoinColumn(name="relatedId"),
            inverseJoinColumns=@JoinColumn(name="articleId")
    )
    @JsonIgnore*/
   @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "related")
   @JsonIgnore
    public List<Article> getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(List<Article> relatedTo) {
        this.relatedTo = relatedTo;
    }
}
