package ateamproject.kezuino.com.github.network.packet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indicates that this field can be serialized and in which order it should occur.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface PacketField {
    /**
     * Gets the indexed order in which the fields should be serialized and deserialized.
     *
     * @return Indexed order in which the fields should be serialized and deserialized.
     */
    byte value();
}
