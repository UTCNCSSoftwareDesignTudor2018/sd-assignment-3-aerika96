package readersWritersApp.clientsServer.server;

import readersWritersApp.clientsServer.protocol.CommunicationProtocol;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MultiServerThread extends Thread{
    private Socket socket = null;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;
            CommunicationProtocol cp = new CommunicationProtocol();
            while ((inputLine = in.readLine()) != null) {
                cp.setState(inputLine);
                inputLine = in.readLine();
                outputLine = cp.processInput(inputLine);

                out.println(outputLine);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
