package ateamproject.kezuino.com.github.network;

import ateamproject.kezuino.com.github.network.packet.Packet;

public interface IServer {
    /**
     * Starts up this {@link IServer} listening on connections to this computer on a port.
     */
    void start();

    /**
     * Stops this {@link IServer} from listening to clients and drops any active connections. {@link IServer} can be started up again using {@link #start()}.
     */
    void stop();

    /**
     * Sends a {@link Packet} over the network.
     *
     * @param packet
     */
    void send(Packet packet);
}
