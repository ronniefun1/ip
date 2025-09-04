package reboot.command;

import reboot.RebootException;
import reboot.Storage;
import reboot.TaskList;
import reboot.Ui;

/**
 * Represents a command to find a task with specified keyword.
 */
public class FindCommand extends Command {

    private final String keyword;

    /**
     * Constructs a FindCommand with the specified keyword.
     * @param keyword Keyword to search for in the tasklist.
     */
    public FindCommand(String keyword) {
        this.keyword = keyword;
    }

    /**
     * Finds all available tasks which contains the specified keyword.
     * Outputs the list via ui.
     * @param tasks Tasklist to search for results.
     * @param ui Ui to output results.
     * @param storage Storage for writing and loading files.
     */
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {

        TaskList results = tasks.findTasks(keyword);

        if (results.isEmpty()) {
            ui.showMessage("No tasks found matching: " + keyword);
        } else {
            ui.showSearchResult(results, keyword);
        }
    }
}
