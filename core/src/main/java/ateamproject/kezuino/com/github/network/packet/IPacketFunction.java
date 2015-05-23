package ateamproject.kezuino.com.github.network.packet;

@FunctionalInterface
public interface IPacketFunction<T, TResult> {
    TResult function(T packet);
}
