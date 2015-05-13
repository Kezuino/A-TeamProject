package ateamproject.kezuino.com.github.multiplayer.packets;

public interface IPacket {
    /**
     * Converts the fields of this {@see IPacket} to a {@link byte} array.
     * Serialization must be ordered and always return the same data when the fields are the same.
     *
     * @return Ordered sequence of bytes that contain the data of this {@link IPacket}.
     */
    byte[] serialize();

    /**
     * Converts the data in the {@link byte} array to the fields of this {@link IPacket}.
     *
     * @param data {@link byte} array to convert to the fields of this {@link IPacket}.
     */
    void deserialize(byte[] data);
}
