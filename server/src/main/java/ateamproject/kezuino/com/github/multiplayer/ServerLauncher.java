package ateamproject.kezuino.com.github.multiplayer;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerLauncher {

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "darkhellentertainment.com");
        
        // Start server.
        Server server;
        try {
            server = Server.instance();
            server.start();
        } catch (RemoteException ex) {
            Logger.getLogger(ServerLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
}
