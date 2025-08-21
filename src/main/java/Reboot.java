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

        // Initialise tasklist and numTasks
        String[] tasklist = new String[100];
        int numTasks = 0;

        // Loop when input is not bye
        while (!input.equals("bye")) {
            if (input.equals(("list"))) {
                System.out.print(solidLn);
                for (int i = 1; i < numTasks + 1; i++) {
                    System.out.println("    " + i + ". " + tasklist[i - 1]);
                }
                System.out.println(solidLn);
            } else {
                tasklist[numTasks] = input;
                numTasks++;
                System.out.println(solidLn +
                        "    added: " + input + "\n" +
                        solidLn);
            }
            input = scanner.nextLine();
        }

        // Close the Scanner
        scanner.close();

        System.out.println(solidLn +
                "    Bye. Hope to see you again soon!\n" +
                solidLn);

    }
}
