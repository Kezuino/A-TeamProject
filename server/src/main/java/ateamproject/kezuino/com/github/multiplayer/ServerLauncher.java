package ateamproject.kezuino.com.github.multiplayer;

import ateamproject.kezuino.com.github.network.rmi.Server;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerLauncher {

    public static void main(String[] args) {
        if (args.length > 0) {
            System.setProperty("java.rmi.server.hostname", args[0]);
        } else {
            System.setProperty("java.rmi.server.hostname", "localhost");
        }

        try {
            Server server = Server.getInstance();
            server.start();

            // Enable server logging and input.
            server.createConsole(System.in, System.out).acceptCommands();
        } catch (RemoteException ex) {
            Logger.getLogger(ServerLauncher.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
