package guiApp.clients;

import guiApp.presentation.controllers.LogInController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;




public class WriterClient {

    private LogInController logInController;

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

        logInController = new LogInController(this);
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

    public void writeToServer(String data){
        out.println(data);
    }

    public String readFromServer(){
        String data  = null;
        try {
            data = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }



}
