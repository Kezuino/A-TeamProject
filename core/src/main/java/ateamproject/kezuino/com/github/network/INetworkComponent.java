package ateamproject.kezuino.com.github.network;

public interface INetworkComponent {
    /**
     * Starts the {@link INetworkComponent} to receiving network traffic.
     */
    void start();

    /**
     * Stops the {@link INetworkComponent} from receiving network traffic.
     */
    void stop();
}
