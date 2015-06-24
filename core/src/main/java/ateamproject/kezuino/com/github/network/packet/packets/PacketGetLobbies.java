/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.network.packet.packets.PacketGetLobbies.GetLobbiesData;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Fatih
 */
public class PacketGetLobbies extends Packet<List<GetLobbiesData>> {

    private boolean isClanGame;
    
    public PacketGetLobbies(boolean isClanGame, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.isClanGame = isClanGame;
    }

    public static class GetLobbiesData implements Serializable{

        public String name;
        public UUID lobbyId;
        public int membersCount;
        public String hostName;

        public GetLobbiesData(String name, UUID lobbyId, int membersCount, String hostName) {
            this.name = name;
            this.lobbyId = lobbyId;
            this.membersCount = membersCount;
            this.hostName = hostName;
        }
    }

    public boolean isClanGame(){
        return this.isClanGame;
    }
}
