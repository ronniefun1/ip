package reboot.command;

import reboot.RebootException;
import reboot.Storage;
import reboot.TaskList;
import reboot.Ui;

/**
 * Represents a command that ends the conversation with the chatbot.
 */
public class ExitCommand extends Command {

    /**
     * Gives the user a bye message via the ui.
     *
     * @param tasks The current Tasklist.
     * @param ui Ui outputs message.
     * @param storage Storage writes to the file.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {
        ui.showBye();
    }

    /**
     * Changes isExit to true to exit the while loop in Reboot.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
