package guiApp.presentation.views;

import guiApp.models.Article;
import guiApp.models.Writer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class WriterView {

    JButton changeUsername;
    JButton changeFirstName;
    JButton changeLastName;
    JButton changeInstitution;
    JButton publishArticle;
    JButton read;
    JButton delete;
    JButton edit;
    JButton allRead;


    JLabel usernameData;
    JLabel firstNameData;
    JLabel lastNameData;
    JLabel institutionData;

    JList relatedArticles;
    JList allMyArticles;
    JList allArts;

    JTextField title;
    JTextArea articleAbstract;
    JTextArea body;

    JPanel newArticleCenter;


    private JFrame mainFrame = new JFrame("Writer Page");

    public WriterView(){
        changeUsername = new JButton("CHANGE");
        changeFirstName = new JButton("CHANGE");
        changeLastName =  new JButton("CHANGE");
        changeInstitution =new JButton("CHANGE");
        publishArticle = new JButton("PUBLISH");
        read = new JButton("READ");
        edit = new JButton("EDIT");
        delete = new JButton("DELETE");
        allRead = new JButton("READ");

        usernameData = new JLabel();
        firstNameData = new JLabel();
        lastNameData = new JLabel();
        institutionData = new JLabel();


        title = new JTextField("Title");
        articleAbstract = new JTextArea("Abstract");
        body = new JTextArea("Article body");

        /*
        Profile Page
         */
        JPanel profile = new JPanel();
        profile.add(new JLabel("Username: "));
        profile.add(usernameData);
        profile.add(changeUsername);
        profile.add(new JLabel("First Name: "));
        profile.add(firstNameData);
        profile.add(changeFirstName);
        profile.add(new JLabel("Last Name: "));
        profile.add(lastNameData);
        profile.add(changeLastName);
        profile.add(new JLabel("Institution: "));
        profile.add(institutionData);
        profile.add(changeInstitution);
        profile.setSize(new Dimension(600,400));
        profile.setLayout(new GridLayout(0,3));
        JPanel personal = new JPanel();
        personal.setLayout(new BorderLayout());
        personal.add(profile,BorderLayout.CENTER);
        personal.add(new JPanel(), BorderLayout.NORTH);
        personal.add(new JPanel(), BorderLayout.SOUTH);


        /*
        New Article
         */


        JPanel newArticle =  new JPanel();
        JPanel newArticleNorth = new JPanel();
        newArticleCenter = new JPanel();
        newArticleNorth.add(new JLabel("Title "));
        newArticleNorth.add(title);
        newArticleNorth.add(new JLabel("Abstract"));
        articleAbstract.setLineWrap(true);
        articleAbstract.setWrapStyleWord(true);
        articleAbstract.setEditable(true);
        newArticleNorth.add(articleAbstract);
        newArticleNorth.add(new JLabel("Article body"));
        newArticleNorth.setLayout(new GridLayout(0,1));
        body.setColumns(600);
        body.setLineWrap(true);
        body.setRows(250);
        body.setWrapStyleWord(true);
        body.setEditable(true);
        newArticleCenter.add(new JScrollPane(body));
        newArticleCenter.add(new JLabel("Related articles"));
        relatedArticles = new JList();
        relatedArticles.setVisibleRowCount(5);
        relatedArticles.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        newArticleCenter.add(new JScrollPane(relatedArticles));
        newArticleCenter.setLayout(new GridLayout(0,1));
        publishArticle.setSize(new Dimension(30,20));
        newArticle.setLayout(new BorderLayout());
        newArticle.add(newArticleNorth, BorderLayout.NORTH);
        newArticle.add(newArticleCenter,BorderLayout.CENTER);
        newArticle.add(publishArticle,BorderLayout.SOUTH);

        /*
        Own Articles

        * */

        JPanel myArticles = new JPanel();
        JPanel buttons = new JPanel();
        allMyArticles = new JList();
        allMyArticles.setVisibleRowCount(400);
        allMyArticles.setFixedCellWidth(600);
        allMyArticles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        buttons.add(read);
        buttons.add(edit);
        buttons.add(delete);
        buttons.setLayout(new GridLayout(0,3));
        allMyArticles.setLayout( new BorderLayout());
        myArticles.add(allMyArticles, BorderLayout.CENTER);
        myArticles.add(buttons,BorderLayout.SOUTH);


        /*
        * All articles
        * */

        JPanel allArticles = new JPanel();
        JPanel button = new JPanel();
        allArts = new JList();
        allArts.setVisibleRowCount(400);
        allArts.setFixedCellWidth(600);
        allArts.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        button.add(allRead);
        button.setLayout(new GridLayout(0,1));
        allArts.setLayout( new BorderLayout());
        allArticles.add(allArts, BorderLayout.CENTER);
        allArticles.add(button,BorderLayout.SOUTH);


        JTabbedPane tabbedPane = new JTabbedPane();


        tabbedPane.addTab("Profile", profile);
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        tabbedPane.addTab("Publish", newArticle);
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_1);

        tabbedPane.addTab("My Articles", myArticles);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_1);

        tabbedPane.addTab("All Articles", allArticles);
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_1);


        mainFrame.setLayout(new BorderLayout());
        mainFrame.add(tabbedPane,BorderLayout.CENTER);
        mainFrame.pack();
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setVisible(true);
        mainFrame.setSize(800,650);

    }




    public String getUsernameData() {
        return usernameData.getText();
    }

    public void setUsernameData(String usernameData) {
        this.usernameData.setText(usernameData);
    }

    public String getFirstNameData() {
        return firstNameData.getText();
    }

    public void setFirstNameData(String firstNameData) {
        this.firstNameData.setText(firstNameData);
    }

    public String getLastNameData() {
        return lastNameData.getText();
    }

    public void setLastNameData(String lastNameData) {
        this.lastNameData.setText(lastNameData);
    }

    public String getInstitutionData() {
        return institutionData.getText();
    }

    public void setInstitutionData(String institutionData) {
        this.institutionData.setText(institutionData);
    }

    public String getTitle() {
        return title.getText();
    }

    public String getArticleAbstract() {
        return articleAbstract.getText();
    }


    public String getBody() {
        return body.getText();
    }

    public void setArticles(List<Article> articles){
        String titles[] = new String[articles.size()];
        int i=0;
        for (Article article: articles
             ) {
                titles[i] = article.getTitle();
                i++;
        }
        relatedArticles.setListData(titles);


    }


    public String [] getArticles(){
        Object obj[ ] = relatedArticles.getSelectedValues();
        String res[] = new String[obj.length];
        for(int i = 0; i < obj.length; i++)
        {
            res[i]= (String) obj[i];
        }
        return res;
    }

    public void setAllMyArticles(List<Article> articles){
        String titles[] = new String[articles.size()];
        int i=0;
        for (Article article: articles
                ) {
            titles[i] = article.getTitle();
            i++;
        }
        allMyArticles.setListData(titles);


    }


    public String getallArts(){
        Object obj = allArts.getSelectedValue();
        String  res = (String) obj;
        return res;
    }

    public void setAllArts(List<Article> articles){
        String titles[] = new String[articles.size()];
        int i=0;
        for (Article article: articles
                ) {
            titles[i] = article.getTitle();
            i++;
        }
        allArts.setListData(titles);


    }


    public String getallMyArticles(){
        Object obj = allMyArticles.getSelectedValue();
        String  res = (String) obj;
        return res;
    }
    public void addUserNameListener (ActionListener st){
        changeUsername.addActionListener(st);
    }
    public void addFistNameListener (ActionListener st){
        changeFirstName.addActionListener(st);
    }
    public void addLastNameListener (ActionListener st){
        changeLastName.addActionListener(st);
    }
    public void addInstitutionListener (ActionListener st){
        changeInstitution.addActionListener(st);
    }

    public void addPublishListener (ActionListener st){
        publishArticle.addActionListener(st);
    }

    public void addReadListener (ActionListener st){
        read.addActionListener(st);
    }
    public void addEditListener (ActionListener st){
        edit.addActionListener(st);
    }
    public void addDeleteListener (ActionListener st){
        delete.addActionListener(st);
    }
    public void addReadAllListener(ActionListener st) {allRead.addActionListener(st);}

}
