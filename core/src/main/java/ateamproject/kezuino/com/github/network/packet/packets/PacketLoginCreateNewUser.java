package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketLoginCreateNewUser extends Packet<Boolean> {

    @PacketField(0)
    protected String username;

    @PacketField(1)
    protected String email;

    public PacketLoginCreateNewUser(String username, String email, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.username = username;
        this.email = email;
    }

    public PacketLoginCreateNewUser(String username, String email) {
        this.username = username;
        this.email = email;
    }
    

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
