package ateamproject.kezuino.com.github.multiplayer;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    /**
     * Stream to where the output by the {@link Server} gets written to.
     */
    public PrintWriter out;

    protected Server(PrintWriter out) {
        this.out = out;
    }

    /**
     * Starts the {@link Server} and listens for clients.
     *
     * @param out {@link PrintStream} to write output to.
     * @return Created server.
     */
    public static Server start(PrintStream out) {
        Server server = new Server(new PrintWriter(out, true));

        // TODO: Startup RMI.
        server.out.println("Starting server..");

        try {
            //stockMarket = new MockEffectsStockMarket();
            Registry registry = LocateRegistry.createRegistry(1099);
            //registry.rebind("stockMarket", this.stockMarket);
        } catch (RemoteException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, ex.getMessage());
        }


        server.out.println("Server started");

        return server;
    }
}
