package ateamproject.kezuino.com.github.network.packet;

@FunctionalInterface
public interface IPacketAction<T extends Packet> {
    void execute(T packet);
}
