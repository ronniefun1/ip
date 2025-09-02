package reboot;

import reboot.command.Command;

import java.io.IOException;

/**
 * Represents a chatbot that will be able to track tasks.
 */
public class Reboot {

    private static final String DEFAULT_FILE_PATH = "output/reboot.txt";

    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    /**
     * Constructs a new Reboot chatbot when given a specified file path.
     * @param filePath Location of file to store data of tasklist.
     */
    public Reboot(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the chatbot which will constantly react to user input.
     */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (RebootException e) {
                ui.showError(e.getMessage());
            } catch (IllegalArgumentException e) {
                ui.showError("I do not understand your language");
            }
        }
    }

    public static void main(String[] args) {
        new Reboot(DEFAULT_FILE_PATH).run();
    }
}
