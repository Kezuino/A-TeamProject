/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author Kez and Jules
 */
public class PacketScoreChanged extends Packet {
    @PacketField(0)
    protected int change;
    @PacketField(1)
    protected ManipulationType manipulationType;

    public PacketScoreChanged() {
    }

    public PacketScoreChanged(UUID sender, UUID... receivers) {
        super(sender, receivers);
    }

    public PacketScoreChanged(int score, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.manipulationType = ManipulationType.INCREASE;
    }

    public PacketScoreChanged(int score, ManipulationType type, UUID sender, UUID... receivers) {
        this(sender, receivers);
        this.change = score;
        this.manipulationType = type;
    }

    public ManipulationType getManipulationType() {
        return this.manipulationType;
    }

    public int getChange() {
        return this.change;
    }

    public enum ManipulationType implements Serializable {
        DECREASE,//when playing or in a lobby
        INCREASE//when the client closes the program or logs out
    }
}
