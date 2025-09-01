public class DeleteCommand extends Command{

    private final int index;

    public DeleteCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {

        // Throw exception when number provided is not within index
        if (tasks.isOutOfRange(index)) {
            throw new RebootException("Only numbers from 1 to " + tasks.size() + " are allowed");
        }

        Task t = tasks.get(index - 1);
        tasks.remove(t);
        storage.writeFile(tasks.getAll());
        ui.showMessage("    Deleting task\n      " + t +
                "\n    " + tasks.size() + " tasks in the list\n");
    }
}
