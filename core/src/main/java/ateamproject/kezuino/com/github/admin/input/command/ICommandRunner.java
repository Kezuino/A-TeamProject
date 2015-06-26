package ateamproject.kezuino.com.github.admin.input.command;

interface ICommandRunner<TOwner extends IAdministrable, TCommand extends Command> {
    /**
     * Executes the command on the {@link ICommandRunner}.
     *
     * @param command Command to execute.
     */
    void runCommand(String command);

    void registerCommand(CommandDefinition<TOwner> command, ICommandFunction<TOwner, Command> action);

    /**
     * Waits for input. This method blocks execution.
     */
    void acceptCommands();

    default CommandDefinition<TOwner> createCommand(String name) {
        return new CommandDefinition<>(name);
    }
}
