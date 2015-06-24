package ateamproject.kezuino.com.github.admin.inputs;

/**
 * Factory pattern for building a {@link CommandDefinition}.
 *
 * @param <TOwner> Type of {@link IAdministrable}.
 */
public class CommandDefinitionBuilder<TOwner extends IAdministrable> {
    protected CommandDefinition<TOwner> command;

    public static CommandDefinitionBuilder create(String name) {
        CommandDefinitionBuilder builder = new CommandDefinitionBuilder();
        builder.command = new CommandDefinition(name);
        return builder;
    }

    public CommandDefinitionBuilder setName(String name) {
        command.setName(name);
        return this;
    }

    public CommandDefinitionBuilder setDescription(String description) {
        command.setDescription(description);
        return this;
    }

    public CommandDefinition<TOwner> get() {
        return command;
    }
}
