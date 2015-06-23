package ateamproject.kezuino.com.github.network;

public interface INetworkComponent extends AutoCloseable {
    /**
     * Starts the {@link INetworkComponent} to receiving network traffic.
     */
    void start();

    /**
     * Stops the {@link INetworkComponent} from receiving network traffic.
     */
    default void stop() {
        try {
            close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
