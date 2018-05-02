package readersWritersApp.persistence.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
@Table (name = "articles")
public class Article {

    private Integer ID;
    private String title;
    private String articleAbstract;
    private String body;
    private Writer writer;
    private List<Article> related;
    private List<Article> relatedTo;

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
    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    @ManyToMany
    @JoinTable(name="tbl_related_articles",
            joinColumns=@JoinColumn(name="articleId"),
            inverseJoinColumns=@JoinColumn(name="relatedId")
    )
    public List<Article> getRelated() {
        return related;
    }

    public void setRelated(List<Article> related) {
        this.related = related;
    }

    @ManyToMany
    @JoinTable(name="tbl_related_articles",
            joinColumns=@JoinColumn(name="relatedId"),
            inverseJoinColumns=@JoinColumn(name="articleId")
    )
    public List<Article> getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(List<Article> relatedTo) {
        this.relatedTo = relatedTo;
    }
}
