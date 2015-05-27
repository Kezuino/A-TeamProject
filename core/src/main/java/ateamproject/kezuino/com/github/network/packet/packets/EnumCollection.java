/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ateamproject.kezuino.com.github.network.packet.packets;

/**
 *
 * @author Sven
 */
public class EnumCollection {
        /**
     * A state in which a person opposed to a clan can be.
     */
    public enum InvitationType {

        /**
         * Nothing will be displayed.
         */
        NONE,

        /**
         * The user can invite people to its clan.
         */
        INVITE,

        /**
         * The user can accept the invite to this clan.
         */
        ACCEPT
    }

    /**
     * A state in which a person opposed to a clan can be.
     */
    public enum ManagementType {

        /**
         * User can remove this clan.
         */
        REMOVE,

        /**
         * User can leave this clan.
         */
        LEAVE,

        /**
         * User can reject an offer to leave this clan.
         */
        REJECT
    }
}
