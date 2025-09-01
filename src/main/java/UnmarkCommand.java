public class UnmarkCommand extends Command{
    private final int index;

    public UnmarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {

        if (tasks.isOutOfRange(index)) {
            throw new RebootException("Only numbers from 1 to " + tasks.size() + " are allowed");
        }

        Task t = tasks.get(index - 1);
        t.unmark();
        storage.writeFile(tasks.getAll());
        ui.showMessage("    Unmarked\n" + "      " + t);
    }
}
