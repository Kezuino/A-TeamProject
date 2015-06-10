package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.render.screens.RefreshableScreen;

import java.util.UUID;

public class PacketScreenUpdate extends Packet {
    @PacketField(0)
    private Class<?> screenClass;
    
    public PacketScreenUpdate() {
    }

    public PacketScreenUpdate(UUID... senderAndReceivers) {
        super(senderAndReceivers);
    }
    
    public PacketScreenUpdate(Class<?> clazz, UUID... senderAndReceivers) {
        this(senderAndReceivers);
        this.screenClass = clazz;
    }
    
    public Class<?> getScreenClass() {
        return this.screenClass;
    }
}