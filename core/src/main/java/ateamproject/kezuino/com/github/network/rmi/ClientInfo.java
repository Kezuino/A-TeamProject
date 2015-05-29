package ateamproject.kezuino.com.github.network.rmi;

public class ClientInfo extends ateamproject.kezuino.com.github.network.ClientInfo {
    protected IProtocolClient rmi;

    public ClientInfo(IProtocolClient client) {
        super();
        this.rmi = client;
    }

    public IProtocolClient getRmi() {
        return rmi;
    }
}
