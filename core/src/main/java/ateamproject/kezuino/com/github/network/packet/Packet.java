package ateamproject.kezuino.com.github.network.packet;

import ateamproject.kezuino.com.github.network.IClient;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@SuppressWarnings("unchecked")
public abstract class Packet<TResult> {
    /**
     * All functions that have a return value are stored here.
     */
    protected static HashMap<Class<?>, IPacketFunction> registeredFunctions;
    /**
     * All actions that do not have a return value are stored here.
     */
    protected static HashMap<Class<?>, IPacketAction> registeredActions;

    /**
     * Cached fields of all the {@link Packet packets} that have been serialized before.
     */
    protected static HashMap<Class<?>, List<Field>> orderedPacketFields;

    static {
        registeredFunctions = new HashMap<>();
        registeredActions = new HashMap<>();
        orderedPacketFields = new HashMap<>();
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


    /**
     * Unregisters all methods that have a binding to a method.
     */
    public static void unregisterAll() {
        if (registeredFunctions != null) registeredFunctions.clear();
        if (registeredActions != null) registeredActions.clear();
    }

    /**
     * Gets all the {@link PacketField fields} in order that they are specified.
     *
     * @param clazz Type of {@link Packet} to fetch the {@link PacketField fields} from.
     * @return All the {@link PacketField fields} in order that they are specified.
     */
    private static <TPacket extends Packet> List<Field> getPacketFields(Class<TPacket> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Parameter packet must not be null.");

        // Return if cached already.
        List<Field> result = Packet.orderedPacketFields.get(clazz);
        if (result == null) {
            result = new ArrayList<>();
            Packet.orderedPacketFields.put(clazz, result);
        }
        if (!result.isEmpty()) return result;

        // Get PacketField annotated fields.
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i <declaredFields.length; i++) {
            if (declaredFields[i].getAnnotation(PacketField.class) != null) {
                result.add(declaredFields[i]);
            }
        }

        // Sort the fields based on order.
        Collections.sort(result, (f1, f2) -> Byte.compare(f1.getAnnotation(PacketField.class).value(), f2.getAnnotation(PacketField.class).value()));

        return result;
    }


    /**
     * Deserializes the byte array given by {@code data} and stores it in this {@link Packet}.
     *
     * @param data Byte array containg the {@link Packet} information to be deserialized.
     * @return {@link Packet} filled by the data from the given {@code data} byte array.
     */
    public static <T extends Packet> T deserialize(byte[] data) {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        ObjectInput in = null;
        Packet result = null;

        try {
            in = new ObjectInputStream(bis);

            // Build package name.
            String packageName = Packet.class.getTypeName();

            // Read packet type
            packageName = packageName.substring(0, packageName.lastIndexOf('.')) + ".packets.Packet" + in.readUTF();

            // Initialize packet.
            try {
                result = (T) Class.forName(packageName).getConstructor().newInstance();
            } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }

            if (result == null) {
                throw new IllegalStateException("Packet could not be found or casted to the right Packet type.");
            }

            // Read sender.
            if (in.readByte() == 1) {
                result.sender = new UUID(in.readLong(), in.readLong());
            }

            // Read receivers.
            short receiverCount = in.readShort();
            for (int i = 0; i < receiverCount; i++) {
                result.receivers.add(new UUID(in.readLong(), in.readLong()));
            }

            // Read all fields in order to byte stream.
            final ObjectInput finalIn = in;
            final Packet finalResult = result;
            for (Field f : getPacketFields(result.getClass())) {
                try {
                    f.setAccessible(true);
                    f.set(finalResult, finalIn.readObject());
                } catch (IOException | IllegalAccessException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            try {
                bis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return (T) result;
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
        // Create byte stream.
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(bos);
            final ObjectOutput finalOut = out;

            // Write packet type.
            out.writeUTF(this.getClass().getSimpleName().replace("Packet", ""));

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
            getPacketFields(this.getClass()).forEach(f -> {
                try {
                    f.setAccessible(true);
                    finalOut.writeObject(f.get(this));
                } catch (IOException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            });

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
