package guiApp.presentation.controllers;

import guiApp.clients.WriterClient;
import guiApp.models.Writer;
import guiApp.presentation.views.LogInView;
import org.codehaus.jackson.map.ObjectMapper;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LogInController {

    private LogInView logInView;
    private WriterController writerController;
    private WriterClient writerClient;

    private ObjectMapper mapper;

    public LogInController(WriterClient writerClient){
        this.writerClient = writerClient;
        logInView = new LogInView();
        logInView.addLoginListener(new LoginListener());
        mapper =  new ObjectMapper();
    }

    class LoginListener implements ActionListener {

        public void actionPerformed(ActionEvent arg0) {
            String userName = logInView.getUserName();
            String password = logInView.getPassWord();
            writerClient.writeToServer("LOG IN");
            writerClient.writeToServer(producesRequest(userName));
            try{
                String jsonString = writerClient.readFromServer();
                Writer writer =  mapper.readValue(jsonString,Writer.class);
                if(writer.getPassword().equalsIgnoreCase(password)){
                    System.out.println("Successfully logged in");
                    writerController = new WriterController(writer,writerClient);
                }
                else{

                    System.out.println("Something incorrect");
                }
            }catch(IOException e){

            }catch(NullPointerException e){
                logInView.showErrorMessage("Incorrect username or password");
                System.out.println("Received null");
            }

        }

        // @Produces(MediaType.APPLICATION_JSON)
        public String producesRequest(String userName){
            return userName;
        }


    }
}
