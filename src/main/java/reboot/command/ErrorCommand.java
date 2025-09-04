package reboot.command;
import reboot.Storage;
import reboot.TaskList;
import reboot.gui.Gui;

public class ErrorCommand extends Command{
    private final String message;

    public ErrorCommand(String message) {
        this.message = message;
    }

    @Override
    public String execute(TaskList tasks, Gui ui, Storage storage) {
        return ui.showError(message);
    }
}
