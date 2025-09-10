package reboot;

import reboot.command.AddCommand;
import reboot.command.ClearCommand;
import reboot.command.Command;
import reboot.command.Commands;
import reboot.command.DeleteCommand;
import reboot.command.ErrorCommand;
import reboot.command.ExitCommand;
import reboot.command.FindCommand;
import reboot.command.ListCommand;
import reboot.command.MarkCommand;
import reboot.command.UnmarkCommand;
import reboot.task.Deadline;
import reboot.task.Event;
import reboot.task.Todo;

/**
 * Represents a parser that will make sense of user input.
 */
public class Parser {

    /**
     * Returns a command for the chatbot to complete from specified input.
     * If unable to understand input, throw IllegalArgumentException.
     * @param input Input of user to be converted to chatbot command.
     */
    public static Command parse(String input) throws RebootException {
        try {
            String[] words = input.split(" ", 2);
            Commands command = Commands.valueOf(words[0].toUpperCase());

            switch (command) {
            case BYE:
                return new ExitCommand();
            case LIST:
                return new ListCommand();
            case MARK:
                if (!Parser.isInteger(words[1])) {
                    throw new RebootException("Proper usage: mark {task index}");
                }

                assert words[1] != null : "Index should not be null";

                if (Integer.parseInt(words[1]) < 1) {
                    throw new RebootException("Ensure that task index is more than 0");
                }

                return new MarkCommand(Integer.parseInt(words[1]));
            case UNMARK:
                if (!Parser.isInteger(words[1])) {
                    throw new RebootException("Proper usage: unmark {task index}");
                }

                if (Integer.parseInt(words[1]) < 1) {
                    throw new RebootException("Ensure that task index is more than 0");
                }

                assert words[1] != null : "Index should not be null";

                return new UnmarkCommand(Integer.parseInt(words[1]));
            case TODO:
                if (words.length == 1) {
                    throw new RebootException("Proper usage: todo {description}");
                }

                assert words[1] != null : "Description should not be null";

                return new AddCommand(new Todo(words[1], false));
            case DEADLINE:
                if (words.length == 1) {
                    throw new RebootException("Proper usage: deadline {description} /by {due date}");
                }

                assert words[1] != null : "Description should not be null";

                // Get the description and due date of task
                words = words[1].split(" /by ");

                if (words.length == 1) {
                    throw new RebootException("Proper usage: deadline {description} /by {due date}");
                }

                assert words[1] != null : "Due date should not be null";

                return new AddCommand(new Deadline(words[0], false, words[1]));
            case EVENT:
                if (words.length == 1) {
                    throw new RebootException(
                            "Proper usage: event {description} /from {start date} /to {end date}");
                }

                assert words[1] != null : "Description should not be null";

                // Separate the description and the start/end dates
                words = words[1].split(" /from ");

                if (words.length == 1) {
                    throw new RebootException(
                            "Proper usage: event {description} /from {start date} /to {end date}");
                }

                assert words[1] != null : "Dates should not be null";

                String description = words[0];

                // Separate the start and end dates
                words = words[1].split(" /to ");

                if (words.length == 1) {
                    throw new RebootException(
                            "Proper usage: event {description} /from {start date} /to {end date}");
                }

                assert words[1] != null : "End date should not be null";

                return new AddCommand(new Event(description, false, words[0], words[1]));
            case DELETE:
                if (!Parser.isInteger(words[1])) {
                    throw new RebootException("Proper usage: delete {task index}");
                }

                if (Integer.parseInt(words[1]) < 1) {
                    throw new RebootException("Ensure that task index is more than 0");
                }

                assert words[1] != null : "Index should not be null";

                return new DeleteCommand(Integer.parseInt(words[1]));
            case CLEAR:
                return new ClearCommand();
            case FIND:
                if (words.length == 1) {
                    throw new RebootException(
                            "Proper usage: find {keyword}");
                }

                assert words[1] != null : "Keyword should not be null";

                return new FindCommand(words[1]);
            default:
                return new ErrorCommand("I do not understand your language");
            }
        } catch (RebootException e) {
            return new ErrorCommand(e.getMessage());
        } catch (IllegalArgumentException e) {
            return new ErrorCommand("I do not understand your language");
        }
    }

    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
