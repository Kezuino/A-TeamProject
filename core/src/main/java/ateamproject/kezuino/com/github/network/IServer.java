package ateamproject.kezuino.com.github.network;

public interface IServer {
    /**
     * Starts up this {@link IServer} listening on connections to this computer on a port.
     */
    void start();

    /**
     * Stops this {@link IServer} from listening to clients and drops any active connections. {@link IServer} can be started up again using {@link #start()}.
     */
    void stop();
}
