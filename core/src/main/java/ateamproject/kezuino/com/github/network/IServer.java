package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.rmi.Lobby;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.util.ArrayList;

public interface IServer {
    /**
     * Starts up this {@link IServer} listening on connections to this computer on port {@value Registry#REGISTRY_PORT}.
     */
    void start();

    /**
     * Stops this {@link IServer} from listening to clients and drops any active connections. {@link IServer} can be started up again using {@link #start()}.
     */
    void stop();
}
