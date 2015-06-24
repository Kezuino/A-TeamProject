package ateamproject.kezuino.com.github.admin.inputs;

import java.util.Arrays;

public class Command<TOwner extends IAdministrable> {
    /**
     * Name of the command.
     */
    protected String name;
    /**
     * Arguments that were given to this command. Can be null.
     */
    protected String[] args;
    private ICommandFunction<TOwner, Command> action;

    protected Command() {
    }

    public Command(String name, String[] args) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Parameter name must not be null.");
        name = name.toLowerCase();
        this.name = name;
        this.args = args;
    }

    /**
     * Creates a {@link Command} from the given {@code input}. Can return null.
     *
     * @param input Input from the user.
     * @return {@link Command} that was parsed from the {@code input}. Can be null if format was invalid.
     */
    public static Command fromString(String input) {
        if (input == null | input.trim().isEmpty()) return null;
        String[] cmd = input.split("\\s+");
        Command result = new Command();

        if (cmd.length > 0) result.name = cmd[0];
        if (cmd.length > 1) result.args = Arrays.copyOfRange(cmd, 1, cmd.length);

        return result;
    }

    public String[] getArgs() {
        return args;
    }

    public String getName() {
        return name;
    }

    public ICommandFunction<TOwner, Command> getAction() {
        return action;
    }

    public void setAction(ICommandFunction<TOwner, Command> action) {
        this.action = action;
    }
}
