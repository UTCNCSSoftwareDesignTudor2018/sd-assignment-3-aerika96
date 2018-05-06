package readersWritersApp.clientsServer.server;


import readersWritersApp.clientsServer.protocol.CommunicationProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiServerThread extends Thread{
    private Socket socket = null;
    private PrintWriter out;
    BufferedReader in;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);

            in = new BufferedReader(
                new InputStreamReader(
                        socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {

        try  {
            String inputLine, outputLine;
            CommunicationProtocol cp = new CommunicationProtocol(this);
            while ((inputLine = in.readLine()) != null) {
                String state;
                do {
                    state = cp.setState(inputLine);
                    inputLine = in.readLine();
                }while (state.equalsIgnoreCase("problem"));
                //inputLine = in.readLine();
                outputLine = cp.processInput(inputLine);

                out.println(outputLine);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeToClient(String output){
        this.out.println(output);
    }

    public String readFromClient(){
        try {
            return this.in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

}
