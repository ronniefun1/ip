package reboot;

import reboot.task.Task;

import java.util.Scanner;

/**
 * Represents a UI that will interact with the user.
 */
public class Ui {

    Scanner scanner = new Scanner(System.in);

    /**
     * Prints a new line to segregate content for clarity.
     */
    public void showLine() {
        System.out.println("    ____________________________________________________________");
    }

    /**
     * Prints the welcome message to the user.
     */
    public void showWelcome() {
        showLine();
        System.out.println("    Hello! I'm reboot.Reboot the best chatbot\n" +
                "    What can the best chatbot do for you?\n");
        showLine();
    }

    /**
     * Reads and returns the input of the user as a string.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the specified message to the user.
     * @param message Message to be printed to user.
     */
    public void showMessage(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    /**
     * Prints the current tasklist of the user.
     * @param tasks Tasklist to be displayed to user.
     */
    public void showTaskList (TaskList tasks) {
        showLine();
        System.out.print("    Here are the tasks in your list:\n");
        for (int i = 1; i < tasks.size() + 1; i++) {
            Task t = tasks.get(i - 1);
            System.out.println("    " + i + ". " + t);
        }
        showLine();
    }

    /**
     * Prints the bye message to the user.
     */
    public void showBye() {
        showLine();
        System.out.println("    Bye. Rate 5 stars.");
        showLine();
    }

    /**
     * Prints the specified error message to the user.
     * @param message Error message to be printed.
     */
    public void showError(String message) {
        showLine();
        System.out.println("    " + message);
        showLine();
    }
}
