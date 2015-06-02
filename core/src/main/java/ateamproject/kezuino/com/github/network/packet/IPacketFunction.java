package ateamproject.kezuino.com.github.network.packet;

@FunctionalInterface
public interface IPacketFunction<T extends Packet, TResult> {
    TResult execute(T packet);
}
