package guiApp.models;


import java.util.List;

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

    public Article(String title, String articleAbstract, String body, Writer writer){
        this.title = title;
        this.articleAbstract =  articleAbstract;
        this.body = body;
        this.writer = writer;

    }

    public Article(String title, String articleAbstract, String body, Writer writer, List<Article> related){
        this.title = title;
        this.articleAbstract =  articleAbstract;
        this.body = body;
        this.writer = writer;
        this.related = related;
        this.relatedTo = related;
    }


    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }


    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }


    public List<Article> getRelated() {
        return related;
    }

    public void setRelated(List<Article> related) {
        this.related = related;
    }

    public List<Article> getRelatedTo() {
        return relatedTo;
    }

    public void setRelatedTo(List<Article> relatedTo) {
        this.relatedTo = relatedTo;
    }
}
