/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;
import ateamproject.kezuino.com.github.network.packet.enums.ManagementType;

import java.util.UUID;

/**
 *
 * @author Sven
 */
public class PacketHandleManagement extends Packet<Boolean> {

    @PacketField(0)
    protected ManagementType manage;

    @PacketField(1)
    protected String clanName;

    @PacketField(3)
    protected String emailadres;

    public PacketHandleManagement(ManagementType manage, String clanName, String emailadres, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.manage = manage;
        this.clanName = clanName;
        this.emailadres = emailadres;
    }

    public PacketHandleManagement(ManagementType manage, String clanName, String emailadres) {
        this.manage = manage;
        this.clanName = clanName;
        this.emailadres = emailadres;
    }

    public ManagementType getManage() {
        return manage;
    }

    public String getClanName() {
        return clanName;
    }

    public String getEmailadres() {
        return emailadres;
    }

}
