package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.util.UUID;

public class PacketKickFromLobby extends Packet<Boolean>  {

     @PacketField(0)
    protected UUID lobbyId;

    public UUID getLobbyId() {
        return lobbyId;
    }

    public UUID getMember() {
        return member;
    }
      @PacketField(1)
    protected UUID member;
    
    public PacketKickFromLobby(UUID lobbyid, UUID member, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.lobbyId = lobbyid;
        this.member = member;
    }
}