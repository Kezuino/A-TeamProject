package ateamproject.kezuino.com.github.network.packet.packets;

import ateamproject.kezuino.com.github.network.packet.Packet;
import ateamproject.kezuino.com.github.network.packet.PacketField;

import java.io.Serializable;
import java.util.UUID;

public class PacketLoginAuthenticate extends Packet<PacketLoginAuthenticate.ReturnData> {

    @PacketField(0)
    protected String emailAddress;
    @PacketField(1)
    protected String password;
    @PacketField(2)
    protected Object connectObject;

    public PacketLoginAuthenticate() {
    }

    public PacketLoginAuthenticate(String emailAddress, String password, UUID sender, UUID... receivers) {
        super(sender, receivers);
        this.emailAddress = emailAddress;
        this.password = password;
    }

    public PacketLoginAuthenticate(String emailAddress, String password, Object connectObject, UUID sender, UUID... receivers) {
        this(emailAddress, password, sender, receivers);
        this.connectObject = connectObject;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public Object getConnectObject() {
        return connectObject;
    }

    public static class ReturnData implements Serializable {

        protected String username;
        protected String emailadress;
        protected UUID privateId;
        protected UUID publicId;
        protected String message;
        protected boolean successful;

        public ReturnData(String username, String emailadress, UUID privateId, UUID publicId, String message, boolean successful) {
            this.username = username;
            this.emailadress = emailadress;
            this.privateId = privateId;
            this.publicId = publicId;
            this.message = message;
            this.successful = successful;
        }

        public boolean isSuccessful() {
            return successful;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public UUID getPublicId() {
            return publicId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmailadress() {
            return emailadress;
        }

        public void setEmailadress(String emailadress) {
            this.emailadress = emailadress;
        }

        public UUID getPrivateId() {
            return privateId;
        }

        public void setPrivateId(UUID privateId) {
            this.privateId = privateId;
        }

    }
}
