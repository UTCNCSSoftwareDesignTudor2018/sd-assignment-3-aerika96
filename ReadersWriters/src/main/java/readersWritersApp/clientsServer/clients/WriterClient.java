package readersWritersApp.clientsServer.clients;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javassist.bytecode.stackmap.BasicBlock;
import presentation.views.LogInView;

import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

public class WriterClient {
    private LogInView logInView;
    private String hostName;
    private int portNumber;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public WriterClient(String hostName, String portNumber) throws UnknownHostException,IOException{
        this.hostName = hostName ;
        this.portNumber = Integer.parseInt(portNumber);

        this.socket = new Socket(this.hostName, this.portNumber);
        out = new PrintWriter(this.socket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(this.socket.getInputStream()));
        logInView = new LogInView();
        logInView.addLoginListener(new LoginListener());
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        WriterClient writerClient;

        try{
            writerClient = new WriterClient(args[0], args[1]);
        }catch(UnknownHostException e){
            System.out.println("Unknown host");
            System.exit(1);
        }catch (IOException e){
            System.out.println("IOexception");
            System.exit(1);
        }

    }

    class LoginListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent arg0) {
           String userName = logInView.getUserName();
           String password = logInView.getPassWord();
           out.println("LOG IN");
           out.println(producesRequest(userName));
           try{
               String pwd = in.readLine();
               if(pwd.equalsIgnoreCase(password)){
                   System.out.println("Logged in successfully");
               }
               else if(pwd.equalsIgnoreCase("not found")){

                   System.out.println("Something incorrect");
               }
           }catch(IOException e){

           }catch(NullPointerException e){
               System.out.println("Received null");
           }

        }

       // @Produces(MediaType.APPLICATION_JSON)
        public String producesRequest(String userName){
            return userName;
        }


    }


}
