package ateamproject.kezuino.com.github.network.packet;

import ateamproject.kezuino.com.github.network.IClient;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public abstract class Packet<TResult> {
    protected static HashMap<Class<?>, IPacketFunction> registeredFunctions;
    protected static HashMap<Class<?>, IPacketAction> registeredActions;

    static {
        registeredFunctions = new HashMap<>();
        registeredActions = new HashMap<>();
    }

    /**
     * Gets or sets the {@link IClient} that issued this {@link Packet}.
     * If null, sender was the current executing context (usually the server).
     */
    protected UUID sender;
    /**
     * Gets or sets the {@link ateamproject.kezuino.com.github.network.packet.Packet.PacketAudience} that is interested in this {@link Packet}.
     */
    protected PacketAudience audience;
    /**
     * Gets or sets the {@link HashSet} of {@link IClient clients} that should receive this {@link Packet}.
     */
    protected HashSet<UUID> receivers;

    /**
     * Result that was set after executing the {@link Packet}.
     */
    protected TResult result;

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
        receivers = new HashSet<>();
        if (senderAndReceivers == null || senderAndReceivers.length == 0) return;

        if (senderAndReceivers.length > 0) sender = senderAndReceivers[0];
        if (senderAndReceivers.length > 1) {
            Arrays.copyOfRange(senderAndReceivers, 1, senderAndReceivers.length - 1);
            receivers.addAll(Arrays.asList(senderAndReceivers));
        }
    }

    /**
     * Executes the {@link Packet}'s function if it was registered and puts the result in {@link Packet#result}.
     *
     * @param packet {@link Packet} to execute.
     * @param <T>    Any type of {@link Packet}.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Packet> T execute(T packet) {
        if (packet == null) throw new IllegalArgumentException("Parameter packet must not be null.");
        IPacketFunction func = registeredFunctions.get(packet.getClass());
        if (func == null) {
            // Try action instead.
            IPacketAction action = registeredActions.get(packet.getClass());
            if (action == null) return null;
            action.action(packet);
            return null;
        }
        // Try function.
        packet.setResult(func.function(packet));
        return packet;
    }

    /**
     * Registers the {@link Packet}'s class and binds an execution to it.
     *
     * @param clazz    {@link Packet} type to bind.
     * @param function Method to execute when this {@link Packet} is executed.
     */
    public static <T extends Packet, TResult> void registerFunc(Class<T> clazz, IPacketFunction<T, TResult> function) {
        if (registeredFunctions.get(clazz) != null)
            throw new IllegalStateException(String.format("Packet %s cannot be registered twice.", clazz.getSimpleName()));
        registeredFunctions.put(clazz, function);
    }

    /**
     * Registers the {@link Packet}'s class and binds an execution to it.
     *
     * @param clazz  {@link Packet} type to bind.
     * @param action Method to execute when this {@link Packet} is executed.
     */
    public static <T extends Packet> void registerAction(Class<T> clazz, IPacketAction<T> action) {
        if (registeredActions.get(clazz) != null)
            throw new IllegalStateException(String.format("Packet %s cannot be registered twice.", clazz.getSimpleName()));
        registeredActions.put(clazz, action);
    }

    public static void unregisterAll() {
        if (registeredFunctions != null) registeredFunctions.clear();
        if (registeredActions != null) registeredActions.clear();
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
    public HashSet<UUID> getReceivers() {
        return receivers;
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
        throw new NotImplementedException();
    }

    /**
     * Deserializes the byte array given by {@code data} and stores it in this {@link Packet}.
     */
    public void deserialize(byte[] data) {
        throw new NotImplementedException();
    }

    /**
     * Possible audiences that will be wanting this {@link Packet}.
     */
    public enum PacketAudience {
        /**
         * All {@link IClient clients} on the server.
         */
        BROADCAST_SERVER,

        /**
         * All {@link IClient} connected to a specific {@link ateamproject.kezuino.com.github.network.Game}.
         */
        BROADCAST,

        /**
         * Specific {@link IClient}.
         */
        TARGET,

        /**
         * Sender is also the receiver.
         */
        SELF
    }
}
