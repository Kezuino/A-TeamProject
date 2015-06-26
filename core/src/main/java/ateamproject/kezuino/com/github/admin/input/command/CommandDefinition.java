package ateamproject.kezuino.com.github.admin.input.command;

public class CommandDefinition<TOwner extends IAdministrable> {
    /**
     * Name of the command.
     */
    private String name;
    /**
     * Arguments that were given to this command. Can be null.
     */
    private String[] args;
    private String description;
    private ICommandFunction<TOwner, Command> action;

    protected CommandDefinition() {
    }

    protected CommandDefinition(String name) {
        if (name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Parameter name must not be null.");

        name = name.toLowerCase();
        this.name = name;
    }

    protected CommandDefinition(String name, String[] args) {
        this(name);
        this.args = args;
    }

    public String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    ICommandFunction<TOwner, Command> getAction() {
        return action;
    }

    void setAction(ICommandFunction<TOwner, Command> action) {
        this.action = action;
    }

    /**
     * Gets the formatted help documentation for this command.
     *
     * @return Formatted help documentation for this command
     */
    public String getHelp() {
        StringBuilder builder = new StringBuilder();
        builder.append('\t').append(getName());
        if (description != null && !description.trim().isEmpty()) builder.append(" - ").append(description);
        return builder.toString();
    }
}
