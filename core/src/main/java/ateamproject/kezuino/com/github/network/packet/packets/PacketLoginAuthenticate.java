package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketLoginAuthenticate extends Packet<UUID> {
    @PacketField(0)
    protected String username;
    @PacketField(1)
    protected String password;
    @PacketField(2)
    protected Object connectObject;

    public PacketLoginAuthenticate() {
    }

    public PacketLoginAuthenticate(String username, String password, Object connectObject, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.username = username;
        this.password = password;
        this.connectObject = connectObject;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Object getConnectObject() {
        return connectObject;
    }
}
