package reboot.command;

import reboot.RebootException;
import reboot.*;

public class ClearCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {
        tasks.clear();
        storage.writeFile(tasks.getAll());
        ui.showMessage("    Cleared tasklist");
    }
}
