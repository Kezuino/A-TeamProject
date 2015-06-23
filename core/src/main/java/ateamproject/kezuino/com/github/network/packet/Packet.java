package ateamproject.kezuino.com.github.network.packet;

import ateamproject.kezuino.com.github.network.IClientInfo;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.UUID;
import java.util.stream.Collectors;

public abstract class Packet<TResult> {

    /**
     * Gets or sets the {@link IClientInfo} that issued this {@link Packet}.
     * If null, sender was the current executing context (usually the server).
     */
    protected UUID sender;
    /**
     * Gets or sets the {@link ateamproject.kezuino.com.github.network.packet.Packet.PacketAudience} that is interested in this {@link Packet}.
     */
    protected PacketAudience audience;
    /**
     * Gets or sets the {@link HashSet} of {@link IClientInfo clients} that should receive this {@link Packet}.
     */
    protected HashSet<UUID> receivers;

    /**
     * Result that was set after executing the {@link Packet}.
     */
    protected TResult result;

    /**
     * Empty constructor for serializing.
     */
    public Packet() {
        receivers = new HashSet<>();
    }

    /**
     * Initializes the fields of the {@link Packet} and sets it's {@link #sender} and all (optional) {@link #receivers}.
     *
     * @param senderAndReceivers First index is the sender, the others are all the receivers of this {@link Packet}.
     *                           <p>
     *                           Can be empty to indicate loopback. Example: {@code new Packet()}.
     *                           </p>
     *                           <p>
     *                           Or first index only for broadcast. <b>Example:</b> {@code new Packet(null)} <b>or</b> {@code new Packet(server)}
     *                           </p>
     */
    protected Packet(UUID... senderAndReceivers) {
        this();
        if (senderAndReceivers == null || senderAndReceivers.length == 0) return;

        if (senderAndReceivers.length > 0) sender = senderAndReceivers[0];
        if (senderAndReceivers.length > 1) {
            senderAndReceivers = Arrays.copyOfRange(senderAndReceivers, 1, senderAndReceivers.length - 1);
            receivers.addAll(Arrays.asList(senderAndReceivers));
        }
    }

    public TResult getResult() {
        return result;
    }

    public void setResult(TResult result) {
        this.result = result;
    }

    /**
     * Gets the {@link HashSet} of {@link UUID clients} that should receive this {@link Packet}.
     *
     * @return {@link HashSet} of {@link UUID clients} that should receive this {@link Packet}.
     */
    public UUID[] getReceivers() {
        return receivers.toArray(new UUID[receivers.size()]);
    }

    /**
     * Sets the receivers of this {@link Packet}.
     *
     * @param receivers List of {@link UUID}. Meaning all private id's that should receive this {@link Packet}.
     */
    public void setReceivers(UUID[] receivers) {
        this.receivers.clear();
        Collections.addAll(this.receivers, receivers);
    }

    /**
     * Gets the {@link UUID} that issued this {@link Packet}.
     * If null, sender was the current executing context (usually the server).
     * <p>
     * See also {@link #sender}.
     *
     * @return {@link UUID} that issued this {@link Packet}.
     */
    public UUID getSender() {
        return sender;
    }

    /**
     * Sets the {@link UUID} that issued this {@link Packet}.
     *
     * @param sender {@link UUID} that issued this {@link Packet}.
     */
    public void setSender(UUID sender) {
        this.sender = sender;
    }

    /**
     * Gets the {@link ateamproject.kezuino.com.github.network.packet.Packet.PacketAudience} that is interested in this {@link Packet}.
     *
     * @return {@link ateamproject.kezuino.com.github.network.packet.Packet.PacketAudience} that is interested in this {@link Packet}.
     */
    public PacketAudience getAudience() {
        if (sender != null) {
            if (receivers.size() == 0) {
                return PacketAudience.BROADCAST_SERVER;
            } else if (receivers.size() <= 1) {
                return PacketAudience.TARGET;
            } else {
                return PacketAudience.BROADCAST;
            }
        }
        return PacketAudience.SELF;
    }

    /**
     * Serializes the {@link PacketField fields} in order and returns the bytes.
     *
     * @return Bytes that contain the data of the {@link PacketField fields} in order.
     */
    public byte[] serialize() {
        // Create byte stream.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(bos);
            final ObjectOutput finalOut = out;

            // Write packet type.
            String packetClassName = this.getClass().getSimpleName();
            out.writeUTF(packetClassName.substring("Packet".length(), packetClassName.length() - "Packet".length()));

            // Write sender.
            if (sender != null) {
                out.writeByte(1);
                out.writeLong(sender.getMostSignificantBits());
                out.writeLong(sender.getLeastSignificantBits());
            } else {
                out.writeByte(0);
            }

            // Write receivers.
            out.writeShort(receivers.size());
            for (UUID receiver : receivers) {
                out.writeLong(receiver.getMostSignificantBits());
                out.writeLong(receiver.getLeastSignificantBits());
            }

            // Write all fields in order to byte stream.
            PacketManager.getPacketFields(this.getClass()).forEach(f -> {
                try {
                    f.setAccessible(true);
                    finalOut.writeObject(f.get(this));
                } catch (IOException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

            // Write return value.
            Object object = getResult();
            if (object != null) {
                finalOut.writeBoolean(true);
                finalOut.writeObject(object);
            } else {
                finalOut.writeBoolean(false);
            }

            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close all streams nicely.
            try {
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bos.toByteArray();
    }

    /**
     * Possible audiences that will be wanting this {@link Packet}.
     */
    public enum PacketAudience {
        /**
         * All {@link IClientInfo clients} on the server.
         */
        BROADCAST_SERVER,

        /**
         * All {@link IClientInfo} connected to a specific {@link ateamproject.kezuino.com.github.network.Game}.
         */
        BROADCAST,

        /**
         * Specific {@link IClientInfo}.
         */
        TARGET,

        /**
         * Sender is also the receiver.
         */
        SELF
    }
}
