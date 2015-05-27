package ateamproject.kezuino.com.github.network.packet.enums;

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
