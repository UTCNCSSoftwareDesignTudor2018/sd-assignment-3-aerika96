package readersWritersApp.clientsServer.protocol;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import readersWritersApp.business.WriterService;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Qualifier;


public class CommunicationProtocol {

    @Inject
    private WriterService writerService;


    @Required
    public void setWriterService(WriterService writerService){
        this.writerService = writerService;
    }
    private static final int WAITING = 0;
    private static final int LOG_IN =1;

    private static final int NUMJOKES = 5;

    private int state = WAITING;


    public void setState(String state){
        if(state.equalsIgnoreCase("LOG IN")){
            this.state = LOG_IN;
        }
    }

    public String processInput(String theInput) {
        String theOutput = null;

        if(state == LOG_IN){
            try {
                theOutput = writerService.getByUserName(theInput).getPassword();
            }catch (NullPointerException e){
                theOutput = new String("not found");
            }
        }


        return theOutput;
    }


}
