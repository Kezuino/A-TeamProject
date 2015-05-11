package ateamproject.kezuino.com.github.multiplayer;

import java.io.PrintStream;
import java.io.PrintWriter;

public class Server {
    public PrintWriter out;

    protected Server(PrintWriter out) {
        this.out = out;
    }

    public static Server start(PrintStream out) {
        Server server = new Server(new PrintWriter(out, true));

        // TODO: Startup RMI.
        server.out.println("Starting server..");
        server.out.println("Server started");

        return server;
    }
}
