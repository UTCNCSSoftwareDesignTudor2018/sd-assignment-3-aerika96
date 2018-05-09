package guiApp.clients;

import java.io.IOException;

public class MessageReceiver implements Runnable {

    private Client client;

    public MessageReceiver(Client client){
        this.client =  client;

    }

    public void run() {
        String inputLine;
        try {
            while (null != (inputLine = client.getIn().readLine())) {
               client.getController().setState(inputLine);
               inputLine =  client.getIn().readLine();
               client.getController().processResponse(inputLine);

            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
}
