package reboot;

import reboot.task.Task;

import java.util.Scanner;

public class Ui {

    Scanner scanner = new Scanner(System.in);

    public void showLine() {
        System.out.println("    ____________________________________________________________");
    }

    public void showWelcome() {
        showLine();
        System.out.println("    Hello! I'm reboot.Reboot the best chatbot\n" +
                "    What can the best chatbot do for you?");
        showLine();
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showMessage(String message) {
        showLine();
        System.out.println(message);
        showLine();
    }

    public void showTaskList (TaskList tasks) {
        showLine();
        System.out.print("    Here are the tasks in your list:\n");
        for (int i = 1; i < tasks.size() + 1; i++) {
            Task t = tasks.get(i - 1);
            System.out.println("    " + i + ". " + t);
        }
        showLine();
    }

    public void showBye() {
        showLine();
        System.out.println("    Bye. Rate 5 stars.");
        showLine();
    }

    public void showError(String message) {
        showLine();
        System.out.println("    " + message);
        showLine();
    }
}
