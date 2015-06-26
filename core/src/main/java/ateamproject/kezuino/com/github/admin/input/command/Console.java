package ateamproject.kezuino.com.github.admin.input.command;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.*;

public class Console<TOwner extends IAdministrable> implements ICommandRunner<TOwner, Command>, Closeable {
    protected Scanner in;
    protected PrintWriter out;
    protected TOwner owner;
    protected HashMap<String, CommandDefinition> registeredCommands;

    /**
     * If true, this {@link Console} is currently able to accept input and write output.
     */
    protected boolean isRunning;
    /**
     * Creates a new {@link Console} with the specified {@link TOwner}.
     *
     * @param owner Object that the commands will be executed on.
     * @param in    {@link InputStream} that will receive commands.
     * @param out   {@link OutputStream} that will be used to write feedback to user.
     */
    public Console(TOwner owner, InputStream in, OutputStream out) {
        if (owner == null) throw new IllegalArgumentException("Parameter owner must not be null.");
        if (in == null) throw new IllegalArgumentException("Parameter in must not be null.");
        if (out == null) throw new IllegalArgumentException("Parameter out must not be null.");

        this.registeredCommands = new HashMap<>();

        this.owner = owner;
        this.in = new Scanner(in);
        this.out = new PrintWriter(out, true);
    }

    public Scanner getIn() {
        return in;
    }

    public PrintWriter getOut() {
        return out;
    }

    /**
     * @see #isRunning()
     */
    public boolean isRunning() {
        return isRunning;
    }

    @Override
    public void close() {
        isRunning = false;
        if (out != null) out.flush();
    }


    @Override
    public void acceptCommands() {
        isRunning = true;
        Thread thread = new Thread(() -> {
            while (isRunning) {
                try {
                    runCommand(in.nextLine());
                    Thread.sleep(10);
                } catch (NoSuchElementException | InterruptedException ignored) {
                }
            }
        });
        thread.start();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void runCommand(String input) {
        Command cmd = Command.fromString(input);
        if (cmd != null) {
            for (Map.Entry<String, CommandDefinition> set : registeredCommands.entrySet()) {
                if (set.getKey().equals(cmd.getName()) && set.getValue().getAction().action(owner, cmd)) return;
            }
        }
        out.printf("Unknown command: '%s'%n", input);
    }

    @Override
    public void registerCommand(CommandDefinition<TOwner> command, ICommandFunction<TOwner, Command> action) {
        if (registeredCommands.containsKey(command.getName()) || registeredCommands.containsValue(command))
            throw new IllegalStateException("Given command was already added to the registered commands.");

        command.setAction(action);
        registeredCommands.put(command.getName(), command);
    }

    /**
     * Gets the names of the registered {@link CommandDefinition commands}.
     *
     * @return Names of the registered {@link CommandDefinition commands}.
     */
    public String[] getCommands() {
        String[] result = new String[registeredCommands.values().size()];
        int index = 0;
        for (CommandDefinition command : registeredCommands.values()) {
            result[index++] = command.getHelp();
        }
        return result;
    }
}
