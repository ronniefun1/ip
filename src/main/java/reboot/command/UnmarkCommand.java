package reboot.command;

import reboot.RebootException;
import reboot.Storage;
import reboot.TaskList;
import reboot.gui.Gui;
import reboot.task.Task;

/**
 * Represents a command that marks a task as incomplete in the tasklist.
 */
public class UnmarkCommand extends Command {
    private final int index;

    /**
     * Constructs a new UnmarkCommand with the specified index.
     * @param index index of the task to be mark as incomplete.
     */
    public UnmarkCommand(int index) {
        this.index = index;
    }

    /**
     * Unmarks the specified task as incomplete in the tasklist.
     * Updates the tasklist file via storage.
     * Informs the user via the gui.
     *
     * @param tasks Tasklist with the task to be mark as incomplete.
     * @param gui Gui outputs message.
     * @param storage Storage writes lines to update file.
     */
    @Override
    public String execute(TaskList tasks, Gui gui, Storage storage) throws RebootException {

        if (tasks.isOutOfRange(index)) {
            throw new RebootException("Only numbers from 1 to " + tasks.size() + " are allowed");
        }

        assert index >= 0 && index < tasks.size() : "Delete index out of bounds";

        tasks.updateRecurringTasks();
        Task t = tasks.get(index - 1);
        t.unmark();
        storage.writeFile(tasks.getAll());
        return gui.showMessage("Unmarked\n" + t);
    }
}
