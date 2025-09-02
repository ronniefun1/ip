package reboot.command;

import reboot.task.Deadline;
import reboot.task.Event;
import reboot.task.Task;
import reboot.task.Todo;
import reboot.TaskList;
import reboot.Ui;
import reboot.Storage;
import reboot.RebootException;

/**
 * Represents a command to add a new task to the tasklist.
 */
public class AddCommand extends Command{

    private final Task task;

    /**
     * Constructs a new AddCommand with the given Todo task.
     * @param task Todo task to be added to the tasklist.
     */
    public AddCommand(Todo task) {
        this.task = task;
    }

    /**
     * Constructs a new AddCommand with the given Deadline task.
     * @param task Deadline task to be added to the tasklist.
     */
    public AddCommand(Deadline task) {
        this.task = task;
    }

    /**
     * Constructs a new AddCommand with the given Event task.
     * @param task Event task to be added to the tasklist.
     */
    public AddCommand(Event task) {
        this.task = task;
    }

    /**
     * Adds the specified task to the tasklist.
     * Updates the tasklist file via storage.
     * Informs the user via the ui.
     *
     * @param tasks Tasklist takes in the task.
     * @param ui Ui outputs message.
     * @param storage Storage appends line to file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {
        tasks.add(task);
        storage.appendLine(task.toFileString());
        ui.showMessage("    Updated\n      " + task +
                "\n    " + tasks.size() + " tasks in the list");
    }
}
