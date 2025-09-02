package reboot.command;

import reboot.*;
import reboot.task.Task;

/**
 * Represents a command that deletes a task from the current tasklist.
 */
public class DeleteCommand extends Command {

    private final int index;

    /**
     * Constructs a new DeleteCommand with the given task index.
     * @param index Index of the task to be deleted.
     */
    public DeleteCommand(int index) {
        this.index = index;
    }

    /**
     * Deletes the specified task from the tasklist.
     * Updates the tasklist file via storage.
     * Informs the user via the ui.
     *
     * @param tasks Tasklist to delete the task from.
     * @param ui Ui outputs message.
     * @param storage Storage rewrites file.
     */
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
