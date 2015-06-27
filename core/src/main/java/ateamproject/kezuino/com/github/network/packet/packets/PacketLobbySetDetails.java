package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.Game;
import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.singleplayer.Map;

import java.io.Serializable;
import java.util.UUID;

public class PacketLobbySetDetails extends Packet {

    @PacketField(0)
    protected PacketLobbySetDetails.Data data;

    public PacketLobbySetDetails() {
    }

    public PacketLobbySetDetails(UUID sender, UUID... receivers) {
        super(sender, receivers);
    }

    public PacketLobbySetDetails(Data data, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.data = data;
    }

    public Data getData() {
        return data;
    }

    public static class Data implements Serializable {
        /**
         * Name of the {@link Game}.
         */
        protected String name;
        /**
         * File name of the {@link Map} to load.
         */
        protected String map;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMap() {
            return map;
        }

        public void setMap(String map) {
            this.map = map;
        }
    }
}
