package ateamproject.kezuino.com.github.admin.input.command;

public class Command {
    private String name;


    /**
     * Creates a {@link CommandDefinition} from the given {@code input}. Can return null.
     *
     * @param input Input from the user.
     * @return {@link CommandDefinition} that was parsed from the {@code input}. Can be null if format was invalid.
     */
    public static Command fromString(String input) {
        if (input == null || input.trim().isEmpty()) return null;
        String[] cmd = input.split("\\s+");
        Command result = new Command();

        if (cmd.length > 0) result.name = cmd[0];
        //if (cmd.length > 1) result.args = Arrays.copyOfRange(cmd, 1, cmd.length);

        return result;
    }

    public String getName() {
        return name;
    }
}
