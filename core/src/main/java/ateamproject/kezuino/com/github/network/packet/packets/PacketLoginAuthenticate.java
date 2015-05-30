package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketLoginAuthenticate extends Packet<UUID> {
    @PacketField(0)
    protected String emailAddress;
    @PacketField(1)
    protected String password;
    @PacketField(2)
    protected Object connectObject;

    public PacketLoginAuthenticate() {
    }

    public PacketLoginAuthenticate(String emailAddress, String password, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public PacketLoginAuthenticate(String emailAddress, String password, Object connectObject, UUID... senderAndReceivers) {
        this(emailAddress, password, senderAndReceivers);
        this.connectObject = connectObject;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Object getConnectObject() {
        return connectObject;
    }
}
