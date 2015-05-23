package ateamproject.kezuino.com.github.network.packet;

@FunctionalInterface
public interface IPacketAction<T> {
    void action(T packet);
}
