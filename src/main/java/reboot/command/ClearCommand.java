package reboot.command;

import reboot.RebootException;
import reboot.Storage;
import reboot.TaskList;
import reboot.Ui;

/**
 * Represents a command that clears the current tasklist.
 */
public class ClearCommand extends Command {

    /**
     * Clears the current tasklist.
     * Updates the tasklist file via storage.
     * Informs the user via the ui.
     *
     * @param tasks Tasklist to clear.
     * @param ui Ui outputs message.
     * @param storage Storage loads tasks into the file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {
        tasks.clear();
        storage.writeFile(tasks.getAll());
        ui.showMessage("    Cleared tasklist");
    }
}
