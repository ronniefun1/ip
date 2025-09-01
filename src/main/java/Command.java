public abstract class Command {

    public abstract void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException;

    public boolean isExit() {
        return false; // default is not exit
    }
}
