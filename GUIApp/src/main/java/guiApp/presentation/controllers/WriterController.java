package guiApp.presentation.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.TypeFactory;
import guiApp.clients.WriterClient;
import guiApp.models.Article;
import guiApp.models.Writer;
import guiApp.presentation.views.WriterView;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.security.sasl.SaslClient;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class WriterController {

    private WriterView writerView;
    private Writer writer;

    private WriterClient writerClient;
    private ObjectMapper mapper;

    public  WriterController ( Writer writer, WriterClient writerClient){
        writerView = new WriterView();
        writerView.addFistNameListener(new FirstNameListener());
        writerView.addLastNameListener(new LastNameListener());
        writerView.addInstitutionListener(new InstitutionListener());
        writerView.addUserNameListener(new UsernameListener());
        writerView.addPublishListener(new PublishListener());
        writerView.addReadListener(new ReadListener());
        writerView.addEditListener(new EditListener());
        writerView.addDeleteListener(new DeleteListener());


        this.writer =writer;
        writerView.setFirstNameData(writer.getFirstName());
        writerView.setLastNameData(writer.getLastName());
        writerView.setInstitutionData(writer.getInstitution());
        writerView.setUsernameData(writer.getUsername());

        this.writerClient = writerClient;
        mapper = new ObjectMapper();

        this.displayArticles();
        this.displayMyArticles();

    }

    public void displayArticles(){
        writerClient.writeToServer("ALL ARTICLES");
        writerClient.writeToServer("input");

       // TypeReference<List<Article>> mapType = new TypeReference<List<Article>>() {};
       // mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonSting = writerClient.readFromServer();
        try {
            List<Article> articles = mapper.readValue(jsonSting, TypeFactory.defaultInstance().constructCollectionType(List.class, Article.class));
            writerView.setArticles(articles);
        } catch (IOException e) {
            e.printStackTrace();
            writerView.setArticles(new ArrayList<Article>());
        }

    }

    public void displayMyArticles(){
        writerClient.writeToServer("ALL ARTICLES");
        writerClient.writeToServer("input");

        // TypeReference<List<Article>> mapType = new TypeReference<List<Article>>() {};
        // mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonSting = writerClient.readFromServer();
        try {
            System.out.println(jsonSting);
            List<Article> articles = mapper.readValue(jsonSting, TypeFactory.defaultInstance().constructCollectionType(List.class, Article.class));
            List<Article> myArticles =  new ArrayList<Article>();
            for (Article a: articles
                 ) {
                writerClient.writeToServer("WRITER OF ARTICLE");
                writerClient.writeToServer(mapper.writeValueAsString(a));
                String jsonString = writerClient.readFromServer();
                Writer wr = mapper.readValue(jsonString, Writer.class);
                try {
                    if (wr.getID() == writer.getID()) {
                        myArticles.add(a);
                    }
                }catch(NullPointerException e){
                    
                }
            }
            writerView.setAllMyArticles(myArticles);
        } catch (IOException e) {
            e.printStackTrace();
            writerView.setArticles(new ArrayList<Article>());
        }catch(NullPointerException e){

        }

    }

    class FirstNameListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {

            try {
                String input = JOptionPane.showInputDialog("Enter your first name:");
                writer.setFirstName(input);
                writerClient.writeToServer("CHANGE PERSONAL DATA");

                String jsonInString = mapper.writeValueAsString(writer);
                writerClient.writeToServer(jsonInString);

                String jsonString = writerClient.readFromServer();
                Writer writer =  mapper.readValue(jsonString,Writer.class);

                writerView.setFirstNameData(writer.getFirstName());

            }catch (NullPointerException ex){

            }  catch (IOException e1) {
                e1.printStackTrace();
            }


        }
    }

    class LastNameListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            try {
                String input = JOptionPane.showInputDialog("Enter your last name:");
                writer.setLastName(input);
                writerClient.writeToServer("CHANGE PERSONAL DATA");

                String jsonInString = mapper.writeValueAsString(writer);
                writerClient.writeToServer(jsonInString);

                String jsonString = writerClient.readFromServer();
                Writer writer =  mapper.readValue(jsonString,Writer.class);

                writerView.setLastNameData(writer.getLastName());

            }catch (NullPointerException ex){

            }  catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    class InstitutionListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            try {
                String input = JOptionPane.showInputDialog("Enter your institution:");
                writer.setInstitution(input);
                writerClient.writeToServer("CHANGE PERSONAL DATA");

                String jsonInString = mapper.writeValueAsString(writer);
                writerClient.writeToServer(jsonInString);

                String jsonString = writerClient.readFromServer();
                Writer writer =  mapper.readValue(jsonString,Writer.class);

                writerView.setInstitutionData(writer.getInstitution());

            }catch (NullPointerException ex){

            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }
    class UsernameListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            try {
                String input = JOptionPane.showInputDialog("Enter your new username:");
                writer.setUsername(input);
                writerClient.writeToServer("CHANGE PERSONAL DATA");

                String jsonInString = mapper.writeValueAsString(writer);
                writerClient.writeToServer(jsonInString);

                String jsonString = writerClient.readFromServer();
                Writer writer =  mapper.readValue(jsonString,Writer.class);

                writerView.setUsernameData(writer.getUsername());

            }catch (NullPointerException ex){

            } catch (IOException e1) {
                e1.printStackTrace();
            }

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
                jsonString = writerClient.readFromServer();
                System.out.println(jsonString);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }

        }
    }

    class ReadListener implements  ActionListener{

        public void actionPerformed(ActionEvent e) {
            String title = writerView.getallMyArticles();
            writerClient.writeToServer("ARTICLE BY TITLE");
            writerClient.writeToServer(title);
            String jsonString = writerClient.readFromServer();
            try {
                Article art = mapper.readValue(jsonString, Article.class);
                JFrame frame = new JFrame(art.getTitle());
                String longMessage ="Title\n" + art.getTitle()+ "\n\n\n\n"+
                        "Abstract\n" + art.getArticleAbstract()+ "\n\n\n"+
                        "Article body\n"+ art.getBody();
                JTextArea textArea = new JTextArea(20, 100);
                textArea.setText(longMessage);
                textArea.setEditable(false);

                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                frame.setPreferredSize(new Dimension(450, 200));
                // wrap a scrollpane around it
                JScrollPane scrollPane = new JScrollPane(textArea);

                // display them in a message dialog
                JOptionPane.showMessageDialog(frame,scrollPane);
            } catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }


    class EditListener implements ActionListener{
        private JTextArea textArea;
        private Article art;

        public void actionPerformed(ActionEvent e) {
            String title = writerView.getallMyArticles();
            writerClient.writeToServer("ARTICLE BY TITLE");
            writerClient.writeToServer(title);
            String jsonString = writerClient.readFromServer();
            try {
                art = mapper.readValue(jsonString, Article.class);
                JFrame frame = new JFrame(art.getTitle());
                String longMessage =
                        "Article body\n"+ art.getBody();
                textArea= new JTextArea(20, 100);
                textArea.setText(longMessage);
                textArea.setEditable(true);

                JButton button = new JButton("Save");
                button.addActionListener(new SaveListener(this));
                frame.getContentPane().add(button);

                frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
                frame.setPreferredSize(new Dimension(450, 200));
                // wrap a scrollpane around it
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.add(button);
                frame.setLayout(new BorderLayout());
                frame.add(button, BorderLayout.NORTH);
                frame.add(scrollPane, BorderLayout.CENTER);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

                // display them in a message dialog
                //JOptionPane.showMessageDialog(frame,scrollPane);

            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        public Article getArt() {
            return art;
        }

        public String getTextArea(){
            return textArea.getText();
        }
    }

    class SaveListener implements ActionListener{

        private EditListener editListener;

        public SaveListener(EditListener editListener){
            this.editListener= editListener;
        }

        public void actionPerformed(ActionEvent e) {
            Article article = editListener.getArt();
            String newBody = editListener.getTextArea();
            article.setBody(newBody);
            String jsonString = null;
            try {
                writerClient.writeToServer("UPDATE");
                jsonString = mapper.writeValueAsString(article);
                writerClient.writeToServer(jsonString);
                jsonString = writerClient.readFromServer();
                System.out.println(jsonString);
            } catch (JsonProcessingException e1) {
                e1.printStackTrace();
            }
            writerClient.writeToServer(jsonString);
        }
    }

    class DeleteListener implements ActionListener{

        public void actionPerformed(ActionEvent e) {
            String title = writerView.getallMyArticles();
            writerClient.writeToServer("DELETE BY TITLE");
            writerClient.writeToServer(title);
            String jsonString = writerClient.readFromServer();
        }
    }
}
