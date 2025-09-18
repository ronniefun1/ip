package reboot.command;

import reboot.RebootException;
import reboot.Storage;
import reboot.TaskList;
import reboot.gui.Gui;
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
     * Informs the user via the gui.
     *
     * @param tasks Tasklist to delete the task from.
     * @param gui Gui outputs message.
     * @param storage Storage rewrites file.
     */
    @Override
    public String execute(TaskList tasks, Gui gui, Storage storage) throws RebootException {

        if (tasks == null) {
            throw new RebootException("Unable to delete from empty list");
        }

        if (tasks.isOutOfRange(index)) {
            throw new RebootException("Only numbers from 1 to " + tasks.size() + " are allowed");
        }

        assert index >= 0 && index < tasks.size() + 1 : "Delete index out of bounds";

        Task t = tasks.get(index - 1);
        tasks.remove(t);
        storage.writeFile(tasks.getAll());
        return gui.showMessage("Deleting task " + t + "\n"
                + tasks.size() + " tasks in the list\n");
    }
}
