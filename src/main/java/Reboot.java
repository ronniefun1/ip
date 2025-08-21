import java.util.Scanner;

public class Reboot {
    public static void main(String[] args) {
        String solidLn = "    ____________________________________________________________\n";

        System.out.println(solidLn +
                "    Hello! I'm Reboot\n" +
                "    What can I do for you?\n" +
                solidLn);

        // Create a Scanner
        Scanner scanner = new Scanner(System.in);

        // Get String input
        String input = scanner.nextLine();

        // Get first word from input
        String[] words = input.split(" ", 2);
        String firstWord = words[0];

        // Initialise tasklist and numTasks
        Task[] tasklist = new Task[100];
        int numTasks = 0;

        // Loop when input is not bye
        while (!input.equals("bye")) {
            switch (firstWord) {
                case "list": {
                    System.out.print(solidLn +
                            "\n    Here are the tasks in your list:\n");
                    for (int i = 1; i < numTasks + 1; i++) {
                        Task t = tasklist[i - 1];
                        System.out.println("    " + i + ". " + t);
                    }
                    System.out.println(solidLn);
                }
                    break;
                case "mark": {
                    int num = Integer.parseInt(words[1]);
                    Task t = tasklist[num - 1];
                    t.mark();
                    System.out.println(solidLn +
                            "    Marked\n" +
                            "      " + t + "\n" +
                            solidLn);
                }
                    break;
                case "unmark": {
                    int num = Integer.parseInt(words[1]);
                    Task t = tasklist[num - 1];
                    t.unmark();
                    System.out.println(solidLn +
                            "    Unmarked\n" +
                            "      " + t + "\n" +
                            solidLn);
                }
                    break;
                case "todo": {
                    Task t = new Todo(words[1]);
                    tasklist[numTasks] = t;
                    numTasks++;
                    System.out.println(solidLn +
                            "    Updated\n      " + t +
                            "\n    " + numTasks + " tasks in the list\n" +
                            solidLn);
                }
                    break;
                case "deadline": {
                    words = words[1].split(" /by ");
                    Task t = new Deadline(words[0], words[1]);
                    tasklist[numTasks] = t;
                    numTasks++;
                    System.out.println(solidLn +
                            "    Updated\n      " + t +
                            "\n    " + numTasks + " tasks in the list\n" +
                            solidLn);
                }
                    break;
                case "event": {
                    words = words[1].split(" /from ");
                    String tmp = words[0];
                    words = words[1].split(" /to ");
                    Task t = new Event(tmp, words[0], words[1]);
                    tasklist[numTasks] = t;
                    numTasks++;
                    System.out.println(solidLn +
                            "    Updated\n      " + t +
                            "\n    " + numTasks + " tasks in the list\n" +
                            solidLn);
                }
                break;
                default:
                    tasklist[numTasks] = new Task(input);
                    numTasks++;
                    System.out.println(solidLn +
                            "    added: " + input + "\n" +
                            solidLn);
            }

            // Get next input
            input = scanner.nextLine();

            // Get first word from input
            words = input.split(" ", 2);
            firstWord = words[0];

        }

        // Close the Scanner
        scanner.close();

        System.out.println(solidLn +
                "    Bye. Hope to see you again soon!\n" +
                solidLn);

    }
}
