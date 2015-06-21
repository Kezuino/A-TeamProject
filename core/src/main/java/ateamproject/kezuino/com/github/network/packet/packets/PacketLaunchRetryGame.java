/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import java.util.UUID;

/**
 *
 * @author Sven Keunen
 */
public class PacketLaunchRetryGame extends Packet  {
    protected boolean paused;

    public PacketLaunchRetryGame() {
    }

    public PacketLaunchRetryGame(boolean paused, UUID... senderAndReceivers) {
        super(senderAndReceivers);
        this.paused = paused;
    }

    public boolean isPaused() {
        return paused;
    }
}