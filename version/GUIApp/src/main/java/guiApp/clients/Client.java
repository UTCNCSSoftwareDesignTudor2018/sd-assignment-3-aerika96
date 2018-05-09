package guiApp.clients;

import guiApp.presentation.controllers.Controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Client {

    private List<String> responses;

    private String hostName;
    private int portNumber;
    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    private Controller controller;

    public Client(String hostName, String portNumber) throws UnknownHostException,IOException {
        this.hostName = hostName ;
        this.portNumber = Integer.parseInt(portNumber);
        this.responses =new ArrayList<String>();

        this.socket = new Socket(this.hostName, this.portNumber);
        out = new PrintWriter(this.socket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(this.socket.getInputStream()));

    }

    public List<String> getResponses() {
        return responses;
    }

    public void setResponses(List<String> responses) {
        this.responses = responses;
    }


    public PrintWriter getOut() {
        return out;
    }

    public void setOut(PrintWriter out) {
        this.out = out;
    }

    public BufferedReader getIn() {
        return in;
    }

    public void setIn(BufferedReader in) {
        this.in = in;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;

    }

    public void writeToServer(String data){
        out.println(data);
    }

    public String readFromServer(){
        String inputLine = null;
        try {
            inputLine = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputLine;
    }


}
