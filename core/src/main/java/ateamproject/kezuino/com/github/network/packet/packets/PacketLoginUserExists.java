package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketLoginUserExists extends Packet<Boolean> {
    @PacketField(0)
    protected String email;


    public PacketLoginUserExists(String email, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
