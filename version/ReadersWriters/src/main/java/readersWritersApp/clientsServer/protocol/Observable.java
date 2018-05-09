package readersWritersApp.clientsServer.protocol;

public interface Observable {

    public void notifyObservers(Object data, String partCase);
    public void addObserver(Object o);
}
