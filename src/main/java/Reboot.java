import java.util.Scanner;

public class Reboot {
    public static void main(String[] args) {
        System.out.println("    ____________________________________________________________\n" +
                "    Hello! I'm Reboot\n" +
                "    What can I do for you?\n" +
                "    ____________________________________________________________\n");

        // Create a Scanner
        Scanner scanner = new Scanner(System.in);

        // Get String input
        String input = scanner.nextLine();

        // Loop when input is not bye
        while (!input.equals("bye")) {
            System.out.println("    ____________________________________________________________\n    " +
                    input +
                    "\n    ____________________________________________________________\n");

            input = scanner.nextLine();
        }

        // Close the Scanner
        scanner.close();

        System.out.println("    ____________________________________________________________\n" +
                "    Bye. Hope to see you again soon!\n" +
                "    ____________________________________________________________\n");
    }
}
