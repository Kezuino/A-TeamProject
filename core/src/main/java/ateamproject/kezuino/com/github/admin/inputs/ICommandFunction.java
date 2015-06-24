package ateamproject.kezuino.com.github.admin.inputs;

@FunctionalInterface
public interface ICommandFunction<TOwner extends IAdministrable, TCommand extends Command> {
    /**
     * Executes the {@link TCommand} with the given name and arguments.
     *
     * @param owner   {@link TOwner} object that this {@link ICommandFunction} will be executed for.
     * @param command {@link TCommand} with the given name and arguments.
     * @return If false, {@link TCommand} should propagate to the next {@link TCommand}
     */
    boolean action(TOwner owner, TCommand command);
}
