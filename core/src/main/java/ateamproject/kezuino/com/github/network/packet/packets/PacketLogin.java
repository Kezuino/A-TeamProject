package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketLogin extends Packet<UUID> {
    @PacketField(0)
    protected String username;
    @PacketField(1)
    protected String password;

    public PacketLogin() {
    }

    public PacketLogin(String username, String password, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}