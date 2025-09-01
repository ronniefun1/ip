package reboot.command;

import reboot.*;

public class ListCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {
        if (tasks.isEmpty()) {
            ui.showMessage("list function is useless without any tasks");
        } else {
            ui.showTaskList(tasks);
        }
    }
}
