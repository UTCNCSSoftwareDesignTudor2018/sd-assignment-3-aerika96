package guiApp.clients;

import guiApp.presentation.controllers.MainController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;




public class WriterClient extends Client{




    public WriterClient(String hostName, String portNumber) throws UnknownHostException,IOException{
        super(hostName,portNumber);
        this.setController(new MainController(this));
    }

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.err.println(
                    "Usage: java EchoClient <host name> <port number>");
            System.exit(1);
        }

        Client writerClient;

        try{
            writerClient = new WriterClient(args[0], args[1]);
            new MessageReceiver(writerClient).run();

        }catch(UnknownHostException e){
            System.out.println("Unknown host");
            System.exit(1);
        }catch (IOException e){
            System.out.println("IOexception");
            System.exit(1);
        }

    }

    public void writeToServer(String data){
        this.getOut().println(data);
    }

    public String readFromServer(){
        String data  = null;
        try {
            data = this.getIn().readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data;
    }



}
