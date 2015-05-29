package ateamproject.kezuino.com.github.network.rmi;

public class ClientInfo extends ateamproject.kezuino.com.github.network.ClientInfo {
    /**
     * RMI interface for communicating with the client through RMI.
     */
    protected IProtocolClient rmi;

    /**
     * Initializes a group of information representing the connected client.
     *
     * @param client RMI interface that's connected to the client.
     */
    public ClientInfo(IProtocolClient client) {
        super();
        this.rmi = client;
    }

    /**
     * Gets the RMI interface for communicating with the client through RMI.
     *
     * @return RMI interface for communicating with the client through RMI.
     */
    public IProtocolClient getRmi() {
        return rmi;
    }
}
