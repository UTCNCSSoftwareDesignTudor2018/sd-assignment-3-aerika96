package guiApp.presentation.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import guiApp.clients.WriterClient;
import guiApp.models.Article;
import guiApp.models.Writer;
import guiApp.presentation.views.LogInView;
import guiApp.presentation.views.WriterView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainController implements Controller {
    private LogInView logInView;
    private WriterView writerView;
    private WriterClient writerClient;
    private ObjectMapper mapper;
    private Writer writer;
    private String password;
    private JTextArea textArea;
    private JTextArea readTextArea;
    private Article art=null;
    JScrollPane scrollPane;
    JScrollPane readScrollPane;

    private static final int WAITING =0 ;
    private static final int LOG_IN =1;
    private static final int CHANGE_PERSONAL_DATA =2;
    private static final int ALL_ARTICLES=3;
    private static final int MY_ARTICLES =4;
    private static final int ARTICLE_BY_TITLE =5;
    private static final int UPDATE = 6;
    private static final int WRITER =7;

    private int state;

    public MainController(WriterClient writerClient){
        this.writerClient = writerClient;
        mapper = new ObjectMapper();
        logInView = new LogInView();
        logInView.addLoginListener(new LoginListener());
    }

    class LoginListener implements ActionListener {



        public void actionPerformed(ActionEvent arg0) {
            String userName = logInView.getUserName();
            password = logInView.getPassWord();
            writerClient.writeToServer("LOG IN");
            writerClient.writeToServer(userName);
        }
    }

    class FirstNameListener implements  ActionListener{

        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog("Enter your first name:");
            writer.setFirstName(input);
            writerClient.writeToServer("CHANGE PERSONAL DATA");

            String jsonInString = null;
            try {
                jsonInString = mapper.writeValueAsString(writer);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
            writerClient.writeToServer(jsonInString);
        }
    }
    class LastNameListener implements  ActionListener{

        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog("Enter your last name:");
            writer.setLastName(input);
            writerClient.writeToServer("CHANGE PERSONAL DATA");

            String jsonInString = null;
            try {
                jsonInString = mapper.writeValueAsString(writer);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
            writerClient.writeToServer(jsonInString);
        }
    }

    class UsernameListener implements  ActionListener{

        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog("Enter your username name:");
            writer.setUsername(input);
            writerClient.writeToServer("CHANGE PERSONAL DATA");

            String jsonInString = null;
            try {
                jsonInString = mapper.writeValueAsString(writer);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
            writerClient.writeToServer(jsonInString);
        }
    }

    class InstitutionListener implements  ActionListener{

        public void actionPerformed(ActionEvent e) {
            String input = JOptionPane.showInputDialog("Enter your institution:");
            writer.setInstitution(input);
            writerClient.writeToServer("CHANGE PERSONAL DATA");

            String jsonInString = null;
            try {
                jsonInString = mapper.writeValueAsString(writer);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
            writerClient.writeToServer(jsonInString);
        }
    }

    class PublishListener implements  ActionListener{

        public void actionPerformed(ActionEvent e) {
            String title = writerView.getTitle();
            String articleAbstract = writerView.getArticleAbstract();
            String body = writerView.getBody();
            String titles[] =writerView.getArticles();

            List<String> relatedArticles = new ArrayList<String>();

            for (String t:titles
                    ) {
                relatedArticles.add(t);
            }

            Article newArticle = new Article(title,articleAbstract,body);

            writerClient.writeToServer("ADD NEW ARTICLE");
            try {
                String jsonString = mapper.writeValueAsString(newArticle);
                writerClient.writeToServer(jsonString);
                jsonString = mapper.writeValueAsString(writer);
                writerClient.writeToServer(jsonString);
                jsonString = mapper.writeValueAsString(relatedArticles);
                writerClient.writeToServer(jsonString);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }

        }
    }

    class EditListener implements  ActionListener{
        public void actionPerformed(ActionEvent e) {
            String title = writerView.getallMyArticles();
            writerClient.writeToServer("ARTICLE BY TITLE");
            writerClient.writeToServer(title);
            writerClient.writeToServer("edit");
            JFrame frame = new JFrame();
            // setTextArea();



        }


    }


    class ReadListener implements  ActionListener{

        public void actionPerformed(ActionEvent e) {
            String title = writerView.getallMyArticles();
            writerClient.writeToServer("ARTICLE BY TITLE");
            writerClient.writeToServer(title);
            writerClient.writeToServer("read");

            JFrame frame = new JFrame();




        }

    }

    class SaveListener implements ActionListener{


        public SaveListener(){
        }

        public void actionPerformed(ActionEvent e) {
            Article article = art;
            String newBody = textArea.getText();
            article.setBody(newBody);
            String jsonString = null;
            try {
                writerClient.writeToServer("UPDATE");
                jsonString = mapper.writeValueAsString(article);
                writerClient.writeToServer(jsonString);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
                writerClient.writeToServer(jsonString);
            }
        }
    }

    class DeleteListener implements  ActionListener{

        public void actionPerformed(ActionEvent e) {
            String title = writerView.getallMyArticles();
            writerClient.writeToServer("DELETE BY TITLE");
            writerClient.writeToServer(title);
        }
    }

    class ReadAllListener implements  ActionListener{

        public void actionPerformed(ActionEvent e) {
            String title = writerView.getallArts();
            writerClient.writeToServer("ARTICLE BY TITLE");
            writerClient.writeToServer(title);
            writerClient.writeToServer("read");
        }
    }

    public void displayArticles(){
        writerClient.writeToServer("ALL ARTICLES");
        writerClient.writeToServer("input");
        //getAllArticles();

        // TypeReference<List<Article>> mapType = new TypeReference<List<Article>>() {};
        // mapper.enable(SerializationFeature.INDENT_OUTPUT);


    }

    public void displayAllReadable(){

        writerClient.writeToServer("ALL ARTICLES");
        writerClient.writeToServer("input");
    }

    public void displayMyArticles(){
        writerClient.writeToServer("ALL MY ARTICLES");
        try {
            writerClient.writeToServer(mapper.writeValueAsString(writer));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // getAllMyArticles();

    }


    public void setState(String input){
        if(input.equalsIgnoreCase("LOG IN")){
            this.state = LOG_IN;
        }
        else if(input.equalsIgnoreCase("CHANGE PERSONAL DATA")){
            this.state = CHANGE_PERSONAL_DATA;
        }
        else if(input.equalsIgnoreCase("ALL ARTICLES")){
            this.state = ALL_ARTICLES;
        }
        else if(input.equalsIgnoreCase("ALL MY ARTICLES")){
            this.state =MY_ARTICLES;
        }
        else if(input.equalsIgnoreCase("ARTICLE BY TITLE")){
            this.state = ARTICLE_BY_TITLE;
        }
        else if(input.equalsIgnoreCase("WRITER")){
            this.state = WRITER;
        }
        else if(input.equalsIgnoreCase("UPDATE")){
            this.state = UPDATE;
        }

    }

    public void processResponse(String input){
        try {
            if (state == LOG_IN) {
                Writer writer = mapper.readValue(input, Writer.class);
                if (writer.getPassword().equalsIgnoreCase(this.password)) {
                    System.out.println("Successfully logged in");
                    this.writer = writer;
                    createWriterView();
                } else {

                    System.out.println("Something incorrect");
                }
            }
            else if(state == CHANGE_PERSONAL_DATA){
                Writer writer = null;
                writer = mapper.readValue(input,Writer.class);
                writerView.setFirstNameData(writer.getFirstName());
                writerView.setLastNameData(writer.getLastName());
                writerView.setUsernameData(writer.getUsername());
                writerView.setInstitutionData(writer.getInstitution());
            }
            else if(state == ALL_ARTICLES){
                try {
                     List<Article> articles = mapper.readValue(input, TypeFactory.defaultInstance().constructCollectionType(List.class, Article.class));
                    writerView.setArticles(articles);
                    writerView.setAllArts(articles);
                 }catch(NullPointerException e){
                     writerView.setAllMyArticles(new ArrayList<Article>());
                }
            }
            else if(state ==MY_ARTICLES){
                try {
                    List<Article> articles = mapper.readValue(input, TypeFactory.defaultInstance().constructCollectionType(List.class, Article.class));
                    writerView.setAllMyArticles(articles);
                }catch(NullPointerException e){
                    writerView.setAllMyArticles(new ArrayList<Article>());
                }
            }
            else if(state == ARTICLE_BY_TITLE){
                String mode = input;
                input = writerClient.readFromServer();
                this.art = mapper.readValue(input, Article.class);
                JFrame frame = new JFrame();

                String longMessage;
                if(mode.equalsIgnoreCase("edit")) {
                    longMessage =art.getBody();
                    textArea = new JTextArea(20, 100);
                    textArea.setText(longMessage);
                    textArea.setEditable(true);
                    scrollPane = new JScrollPane(textArea);
                    JButton button = new JButton("Save");
                    button.addActionListener(new SaveListener());
                    frame.getContentPane().add(button);

                    frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    frame.setPreferredSize(new Dimension(450, 200));
                    // wrap a scrollpane around it
                    scrollPane.add(button);
                    frame.setLayout(new BorderLayout());
                    frame.add(button, BorderLayout.NORTH);
                    frame.add(scrollPane, BorderLayout.CENTER);
                    frame.pack();
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                }

                else {
                    longMessage = "Title\n" + art.getTitle() + "\n\n\n\n" +
                            "Abstract\n" + art.getArticleAbstract() + "\n\n\n" +
                            "Article body\n" + art.getBody();
                    readTextArea = new JTextArea(20, 100);
                    readTextArea.setText(longMessage);
                    readTextArea.setEditable(false);
                    readScrollPane = new JScrollPane(readTextArea);

                    frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                    frame.setPreferredSize(new Dimension(450, 200));
                    // wrap a scrollpane around it

                    // display them in a message dialog
                    JOptionPane.showMessageDialog(frame,readScrollPane);
                }
            }
            else if(state == UPDATE){
                String json = writerClient.readFromServer();
                if(json.equalsIgnoreCase("edited")){
                }
                else if(json.equalsIgnoreCase("added")){
                    displayAllReadable();
                    displayMyArticles();
                    displayArticles();
                }
                else if(json.equalsIgnoreCase("deleted")){
                      displayArticles();
                      displayMyArticles();
                      displayAllReadable();

                }
            }



        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void createWriterView(){
        writerView = new WriterView();
        writerView.addFistNameListener(new FirstNameListener());
        writerView.addLastNameListener( new LastNameListener());
        writerView.addInstitutionListener(new InstitutionListener());
        writerView.addUserNameListener( new UsernameListener());
        writerView.addPublishListener( new PublishListener());
        writerView.addReadListener(new ReadListener());
        writerView.addEditListener(new EditListener());
        writerView.addDeleteListener(new DeleteListener());
        writerView.addReadAllListener(new ReadAllListener());
        writerView.setFirstNameData(writer.getFirstName());
        writerView.setLastNameData(writer.getLastName());
        writerView.setInstitutionData(writer.getInstitution());
        writerView.setUsernameData(writer.getUsername());
        displayArticles();
        displayMyArticles();
        displayAllReadable();
    }



}
