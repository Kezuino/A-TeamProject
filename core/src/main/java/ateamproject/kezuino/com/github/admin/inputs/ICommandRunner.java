package ateamproject.kezuino.com.github.admin.inputs;

interface ICommandRunner<TOwner extends IAdministrable, TCommand extends Command> {
    /**
     * Executes the command on the {@link ICommandRunner}.
     *
     * @param command Command to execute.
     */
    void runCommand(String command);

    void registerCommand(Command<TOwner> command, ICommandFunction<TOwner, Command> action);

    /**
     * Waits for input. This method blocks execution.
     */
    void acceptCommands();
}
