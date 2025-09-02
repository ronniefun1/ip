package reboot.command;

import reboot.TaskList;
import reboot.Ui;
import reboot.Storage;
import reboot.RebootException;

/**
 * Represents a command that displays the current tasklist if not empty.
 */
public class ListCommand extends Command {

    /**
     * Checks if tasklist is empty.
     * If empty, output message to inform user that list is empty.
     * If not empty, display the current tasklist.
     *
     * @param tasks Tasklist to be displayed.
     * @param ui Ui outputs message.
     * @param storage Storage writes lines to file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {
        if (tasks.isEmpty()) {
            ui.showMessage("list function is useless without any tasks");
        } else {
            ui.showTaskList(tasks);
        }
    }
}
