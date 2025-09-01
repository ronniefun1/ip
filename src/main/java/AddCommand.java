public class AddCommand extends Command{

    private final Task task;

    public AddCommand(Todo task) {
        this.task = task;
    }

    public AddCommand(Deadline task) {
        this.task = task;
    }

    public AddCommand(Event task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {
        tasks.add(task);
        storage.appendLine(task.toFileString());
        ui.showMessage("    Updated\n      " + task +
                "\n    " + tasks.size() + " tasks in the list");
    }
}
