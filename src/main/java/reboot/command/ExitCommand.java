package reboot.command;

import reboot.*;

public class ExitCommand extends Command {

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {
        ui.showBye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}
