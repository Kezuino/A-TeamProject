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
public class PacketHandleInvitation extends Packet<Boolean> {

    @PacketField(0)
    protected InvitationType invite;

    @PacketField(1)
    protected String clanName;

    @PacketField(2)
    protected String emailadres;

    @PacketField(3)
    protected String nameOfInvitee;

    public PacketHandleInvitation(InvitationType invite, String clanName, String emailadres, String nameOfInvitee, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.invite = invite;
        this.clanName = clanName;
        this.emailadres = emailadres;
        this.nameOfInvitee = nameOfInvitee;
    }

    public InvitationType getInvite() {
        return invite;
    }

    public String getClanName() {
        return clanName;
    }

    public String getEmailadres() {
        return emailadres;
    }

    public String getNameOfInvitee() {
        return nameOfInvitee;
    }

}
