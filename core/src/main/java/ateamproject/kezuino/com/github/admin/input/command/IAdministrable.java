package ateamproject.kezuino.com.github.admin.input.command;

import java.io.InputStream;
import java.io.OutputStream;

public interface IAdministrable<TOwner extends IAdministrable> {
    void setInput(InputStream in);

    void setOutput(OutputStream out);

    /**
     * Sets the input and output streams for the {@link IAdministrable} object. Default implementation returns null.
     * Object that implements this interface should provide a {@link Console}.
     *
     * @param in  {@link InputStream} to give to the {@link Console}.
     * @param out {@link OutputStream} to give to the {@link Console}.
     * @return Null by default. Requires implementation.
     */
    default Console<TOwner> createConsole(InputStream in, OutputStream out) {
        if (in == null) throw new IllegalArgumentException("Parameter in must not be null.");
        if (out == null) throw new IllegalArgumentException("Parameter out must not be null.");

        setInput(in);
        setOutput(out);
        return null;
    }
}
