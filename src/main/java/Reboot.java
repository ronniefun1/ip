import java.util.Scanner;
import java.util.ArrayList;

public class Reboot {
    public static void main(String[] args) {
        String solidLn = "    ____________________________________________________________\n";

        System.out.println(solidLn +
                "    Hello! I'm Reboot the best chatbot\n" +
                "    What can the best chatbot do for you?\n" +
                solidLn);

        // Create a Scanner
        Scanner scanner = new Scanner(System.in);

        // Get String input
        String input = scanner.nextLine();

        // Get first word from input
        String[] words = input.split(" ", 2);
        String firstWord = words[0];

        // Initialise tasklist and numTasks
        ArrayList<Task> tasklist = new ArrayList<>();
        int numTasks = 0;

        // Loop when input is not bye
        while (!input.equals("bye")) {
            try {
                switch (firstWord) {
                    case "list": {
                        // If no tasks have been added, throw exception
                        if (numTasks == 0) {
                            throw new RebootException("list function is useless without any tasks");
                        }

                        System.out.print(solidLn +
                                "\n    Here are the tasks in your list:\n");

                        // Output list
                        for (int i = 1; i < numTasks + 1; i++) {
                            Task t = tasklist.get(i - 1);
                            System.out.println("    " + i + ". " + t);
                        }
                        System.out.println(solidLn);
                    }
                        break;
                    case "mark": {
                        // Throw exception if there is no index given
                        if (words.length == 1) {
                            throw new RebootException("Proper usage: mark {task index}");
                        }

                        // Get the index for the task to be marked
                        int num = Integer.parseInt(words[1]);

                        // Throw exception when number provided is not within index
                        if (num > numTasks) {
                            throw new RebootException("Only numbers from 1 to " + numTasks + " are allowed");
                        }

                        // Mark and output task marked
                        Task t = tasklist.get(num - 1);
                        t.mark();
                        System.out.println(solidLn +
                                "    Marked\n" +
                                "      " + t + "\n" +
                                solidLn);
                    }
                        break;
                    case "unmark": {
                        // Throw exception if there is no index given
                        if (words.length == 1) {
                            throw new RebootException("Proper usage: unmark {task index}");
                        }

                        // Get the index for the task to be marked
                        int num = Integer.parseInt(words[1]);

                        // Throw exception when number provided is not within index
                        if (num > numTasks) {
                            throw new RebootException(
                                    "Only numbers from 1 to " + numTasks + " are allowed");
                        }

                        // Unmark and output task unmarked
                        Task t = tasklist.get(num - 1);
                        t.unmark();
                        System.out.println(solidLn +
                                "    Unmarked\n" +
                                "      " + t + "\n" +
                                solidLn);
                    }
                    break;
                    case "todo": {
                        // Throw exception when description of task not provided
                        if (words.length == 1) {
                            throw new RebootException("Proper usage: todo {description}");
                        }

                        // Create new task and add it to list
                        Task t = new Todo(words[1]);
                        tasklist.add(t);
                        numTasks++;
                        System.out.println(solidLn +
                                "    Updated\n      " + t +
                                "\n    " + numTasks + " tasks in the list\n" +
                                solidLn);
                    }
                    break;
                    case "deadline": {
                        // Throw exception when description of task not provided
                        if (words.length == 1) {
                            throw new RebootException("Proper usage: deadline {description} /by {due date}");
                        }

                        // Get the description and due date of task
                        words = words[1].split(" /by ");

                        // Throw exception if either fields are empty
                        if (words.length == 1) {
                            throw new RebootException("Proper usage: deadline {description} /by {due date}");
                        }

                        // Create new task and add it to list
                        Task t = new Deadline(words[0], words[1]);
                        tasklist.add(t);
                        numTasks++;
                        System.out.println(solidLn +
                                "    Updated\n      " + t +
                                "\n    " + numTasks + " tasks in the list\n" +
                                solidLn);
                    }
                    break;
                    case "event": {
                        // Throw exception when description of task not provided
                        if (words.length == 1) {
                            throw new RebootException(
                                    "Proper usage: event {description} /from {start date} /to {end date}");
                        }

                        // Separate the description and the start/end dates
                        words = words[1].split(" /from ");

                        //Throw exception if description or dates are missing
                        if (words.length == 1) {
                            throw new RebootException(
                                    "Proper usage: event {description} /from {start date} /to {end date}");
                        }

                        String tmp = words[0]; // Description

                        // Separate the start and end dates
                        words = words[1].split(" /to ");

                        //Throw exception if either date is missing
                        if (words.length == 1) {
                            throw new RebootException(
                                    "Proper usage: event {description} /from {start date} /to {end date}");
                        }

                        // Create new task and add it to list
                        Task t = new Event(tmp, words[0], words[1]);
                        tasklist.add(t);
                        numTasks++;
                        System.out.println(solidLn +
                                "    Updated\n      " + t +
                                "\n    " + numTasks + " tasks in the list\n" +
                                solidLn);
                    }
                    break;
                    case "delete": {
                        // Throw exception if there is no index given
                        if (words.length == 1) {
                            throw new RebootException("Proper usage: delete {task index}");
                        }

                        // Get the index for the task to be deleted
                        int num = Integer.parseInt(words[1]);

                        // Throw exception when number provided is not within index
                        if (num > numTasks) {
                            throw new RebootException(
                                    "Only numbers from 1 to " + numTasks + " are allowed");
                        }

                        // Delete and output task deleted
                        Task t = tasklist.get(num - 1);
                        tasklist.remove(t);
                        numTasks--;
                        System.out.println(solidLn +
                                "\n    Deleting task\n      " + t +
                                "\n    " + numTasks + " tasks in the list\n" +
                                solidLn);
                    }
                        break;

                    default:
                        throw new RebootException("I don't quite understand your language");
                }
            }
            catch (RebootException e) {
                System.out.println(solidLn + "    " + e.getMessage() + "\n" + solidLn);
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
                "    Bye. Rate 5 stars.\n" +
                solidLn);

    }
}
