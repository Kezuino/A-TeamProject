package ateamproject.kezuino.com.github.admin.inputs;

@FunctionalInterface
public interface ICommandFunction<TOwner extends IAdministrable, Command> {
    /**
     * Executes the {@link Command} with the given name and arguments.
     *
     * @param owner   {@link TOwner} object that this {@link ICommandFunction} will be executed for.
     * @param command {@link Command} with the given name and arguments.
     * @return If false, {@link Command} should propagate to the next {@link Command}
     */
    boolean action(TOwner owner, Command command);
}
