package ateamproject.kezuino.com.github.multiplayer;

import java.rmi.Remote;

public interface IProtocolUDP {
    /**
     * Sends information to a receiving end (can be server or client).
     *
     * @param array Information to send.
     */
    void send(byte[] array);
}
