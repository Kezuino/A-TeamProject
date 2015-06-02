package ateamproject.kezuino.com.github.network.packet;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class PacketManager {
    /**
     * Cached fields of all the {@link Packet packets} that have been serialized before.
     */
    protected static HashMap<Class<?>, List<Field>> orderedPacketFields;
    /**
     * All functions that have a return value are stored here.
     */
    protected HashMap<Class<?>, IPacketFunction> registeredFunctions;
    /**
     * All actions that do not have a return value are stored here.
     */
    protected HashMap<Class<?>, IPacketAction> registeredActions;

    public PacketManager() {
        registeredFunctions = new HashMap<>();
        registeredActions = new HashMap<>();
        orderedPacketFields = new HashMap<>();
    }

    /**
     * Gets all the {@link PacketField fields} in order that they are specified.
     *
     * @param clazz Type of {@link Packet} to fetch the {@link PacketField fields} from.
     * @return All the {@link PacketField fields} in order that they are specified.
     */
    static <TPacket extends Packet> List<Field> getPacketFields(Class<TPacket> clazz) {
        if (clazz == null) throw new IllegalArgumentException("Parameter packet must not be null.");

        // Return if cached already.
        List<Field> result = PacketManager.orderedPacketFields.get(clazz);
        if (result == null) {
            result = new ArrayList<>();
            PacketManager.orderedPacketFields.put(clazz, result);
        }
        if (!result.isEmpty()) return result;

        // Get PacketField annotated fields.
        Field[] declaredFields = clazz.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            if (declaredFields[i].getAnnotation(PacketField.class) != null) {
                result.add(declaredFields[i]);
            }
        }

        // Sort the fields based on order.
        Collections.sort(result, (f1, f2) -> Byte.compare(f1.getAnnotation(PacketField.class)
                                                            .value(), f2.getAnnotation(PacketField.class).value()));

        return result;
    }

    /**
     * Deserializes the byte array given by {@code data} and stores it in this {@link Packet}.
     *
     * @param data Byte array containg the {@link Packet} information to be deserialized.
     * @return {@link Packet} filled by the data from the given {@code data} byte array.
     */
    @SuppressWarnings("unchecked")
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

            // Read return value.
            boolean hasReturn = finalIn.readBoolean();
            if (hasReturn) {
                result.setResult(finalIn.readObject());
            }
        } catch (IOException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
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

    /**
     * Executes the {@link Packet}'s function if it was registered and puts the result in {@link Packet#result}.
     *
     * @param packet {@link Packet} to execute.
     * @param <T>    Any type of {@link Packet}.
     */
    @SuppressWarnings("unchecked")
    public <T extends Packet> T execute(T packet) {
        if (packet == null) throw new IllegalArgumentException("Parameter packet must not be null.");
        IPacketFunction func = registeredFunctions.get(packet.getClass());
        if (func == null) {
            // Try action instead.
            IPacketAction action = registeredActions.get(packet.getClass());
            if (action == null) return null;
            action.execute(packet);
            return null;
        }
        // Try function.
        packet.setResult(func.execute(packet));
        return packet;
    }

    /**
     * Registers the {@link Packet}'s class and binds an execution to it.
     *
     * @param clazz    {@link Packet} type to bind.
     * @param function Method to execute when this {@link Packet} is executed.
     */
    public <T extends Packet, TResult> void registerFunc(Class<T> clazz, IPacketFunction<T, TResult> function) {
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
    public <T extends Packet> void registerAction(Class<T> clazz, IPacketAction<T> action) {
        if (registeredActions.get(clazz) != null)
            throw new IllegalStateException(String.format("Packet %s cannot be registered twice.", clazz.getSimpleName()));
        registeredActions.put(clazz, action);
    }

    /**
     * Unregisters all methods that have a binding to a method.
     */
    public void unregisterAll() {
        if (registeredFunctions != null) registeredFunctions.clear();
        if (registeredActions != null) registeredActions.clear();
    }
}
