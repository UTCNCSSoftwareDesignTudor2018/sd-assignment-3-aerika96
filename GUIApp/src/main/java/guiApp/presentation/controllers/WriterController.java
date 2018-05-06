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

import javax.swing.*;
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


        this.writer =writer;
        writerView.setFirstNameData(writer.getFirstName());
        writerView.setLastNameData(writer.getLastName());
        writerView.setInstitutionData(writer.getInstitution());
        writerView.setUsernameData(writer.getUsername());

        this.writerClient = writerClient;
        mapper = new ObjectMapper();

        this.displayArticles();

    }

    public void displayArticles(){
        writerClient.writeToServer("ALL ARTICLES");
        writerClient.writeToServer("input");

       // TypeReference<List<Article>> mapType = new TypeReference<List<Article>>() {};
       // mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String jsonSting = writerClient.readFromServer();
        try {
            System.out.println(jsonSting);
            List<Article> articles = mapper.readValue(jsonSting, TypeFactory.defaultInstance().constructCollectionType(List.class, Article.class));
            writerView.setArticles(articles);
        } catch (IOException e) {
            e.printStackTrace();
            writerView.setArticles(new ArrayList<Article>());
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
}
