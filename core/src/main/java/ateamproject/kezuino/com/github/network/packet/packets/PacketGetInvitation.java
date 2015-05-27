/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.singleplayer.ClanFunctions.InvitationType;
import java.util.UUID;

/**
 *
 * @author Sven
 */
public class PacketGetInvitation extends Packet<InvitationType> {

    @PacketField(0)
    protected String emailadres;

    @PacketField(1)
    protected String clanName;

    public PacketGetInvitation(String emailadres, String clanName, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.emailadres = emailadres;
        this.clanName = clanName;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public String getClanName() {
        return clanName;
    }

}
