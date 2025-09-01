package reboot.command;

import reboot.*;
import reboot.task.Task;

public class MarkCommand extends Command {

    private final int index;

    public MarkCommand(int index) {
        this.index = index;
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws RebootException {

        if (tasks.isOutOfRange(index)) {
            throw new RebootException("Only numbers from 1 to " + tasks.size() + " are allowed");
        }

        Task t = tasks.get(index - 1);
        t.mark();
        storage.writeFile(tasks.getAll());
        ui.showMessage("    Marked\n" + "      " + t);
    }
}
