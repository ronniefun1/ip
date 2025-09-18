package reboot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
import reboot.task.recurring.RecurringDeadline;
import reboot.task.recurring.RecurringEvent;
import reboot.task.recurring.RecurringTodo;

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
                if (words.length == 1) {
                    throw new RebootException("Proper usage: mark {task index}");
                }

                if (Parser.isNotInteger(words[1])) {
                    throw new RebootException("Proper usage: mark {task index}");
                }

                assert words[1] != null : "Index should not be null";

                if (Integer.parseInt(words[1]) < 1) {
                    throw new RebootException("Ensure that task index is more than 0");
                }

                return new MarkCommand(Integer.parseInt(words[1]));
            case UNMARK:
                if (words.length == 1) {
                    throw new RebootException("Proper usage: unmark {task index}");
                }

                if (Parser.isNotInteger(words[1])) {
                    throw new RebootException("Proper usage: unmark {task index}");
                }

                if (Integer.parseInt(words[1]) < 1) {
                    throw new RebootException("Ensure that task index is more than 0");
                }

                return new UnmarkCommand(Integer.parseInt(words[1]));
            case TODO:
                if (words.length == 1) {
                    throw new RebootException("Proper usage: todo {description}");
                }

                assert words[1] != null : "Description should not be null";

                String[] todoStrings = words[1].split(" /recurring ", 2);

                boolean isRecurringTodo = todoStrings.length != 1;

                if (isRecurringTodo) {
                    if (isNotValidRecurrence(todoStrings[1])) {
                        return new ErrorCommand("Only daily/monthly/yearly recurrence is supported");
                    }
                    return new AddCommand(new RecurringTodo(todoStrings[0], false, todoStrings[1]));
                } else {
                    return new AddCommand(new Todo(words[1], false));
                }

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

                String[] deadlineStrings = words[1].split(" /recurring ", 2);

                boolean isRecurringDeadline = deadlineStrings.length != 1;

                if (isRecurringDeadline) {
                    if (isNotValidRecurrence(deadlineStrings[1])) {
                        return new ErrorCommand("Only daily/monthly/yearly recurrence is supported");
                    }
                    try {
                        // Try to parse with datetime
                        LocalDateTime dueDateTime = parseDateAndTime(deadlineStrings[0]);
                        if (isBeforeToday(dueDateTime)) {
                            return new ErrorCommand("Deadline task is already over");
                        }
                        return new AddCommand(new RecurringDeadline(
                                words[0], false, dueDateTime, deadlineStrings[1]));
                    } catch (Exception e) {
                        // Otherwise parse as date only
                        LocalDate dueDate = parseDateOnly(deadlineStrings[0]);
                        if (isBeforeToday(dueDate)) {
                            return new ErrorCommand("Deadline task is already over");
                        }
                        return new AddCommand(new RecurringDeadline(
                                words[0], false, dueDate, deadlineStrings[1]));
                    }
                } else {
                    try {
                        // Try to parse with datetime
                        LocalDateTime dueDateTime = parseDateAndTime(deadlineStrings[0]);
                        if (isBeforeToday(dueDateTime)) {
                            return new ErrorCommand("Deadline task is already over");
                        }
                        return new AddCommand(new Deadline(words[0], false, dueDateTime));
                    } catch (Exception e) {
                        // Otherwise parse as date only
                        LocalDate dueDate = parseDateOnly(deadlineStrings[0]);
                        if (isBeforeToday(dueDate)) {
                            return new ErrorCommand("Deadline task is already over");
                        }
                        return new AddCommand(new Deadline(words[0], false, dueDate));
                    }
                }
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

                String[] dates = words[1].split(" /to ");

                if (dates.length == 1) {
                    throw new RebootException(
                            "Proper usage: event {description} /from {start date} /to {end date}");
                }

                String[] eventStrings = dates[1].split(" /recurring ", 2);

                boolean isRecurringEvent = eventStrings.length != 1;

                if (isRecurringEvent) {
                    if (isNotValidRecurrence(eventStrings[1])) {
                        return new ErrorCommand("Only daily/monthly/yearly recurrence is supported");
                    }
                    try {
                        // Try to parse with datetime
                        LocalDateTime startDateTime = parseDateAndTime(dates[0]);
                        LocalDateTime endDateTime = parseDateAndTime(eventStrings[0]);
                        if (isBeforeToday(startDateTime) || isBeforeToday(endDateTime)) {
                            return new ErrorCommand("Event is already over");
                        }
                        return new AddCommand(new RecurringEvent(
                                description, false, startDateTime, endDateTime, eventStrings[1]));
                    } catch (Exception e) {
                        // Otherwise parse as date only
                        LocalDate startDate = parseDateOnly(dates[0]);
                        LocalDate endDate = parseDateOnly(eventStrings[0]);
                        if (isBeforeToday(startDate) || isBeforeToday(endDate)) {
                            return new ErrorCommand("Event is already over");
                        }
                        return new AddCommand(new RecurringEvent(
                                description, false, startDate, endDate, eventStrings[1]));
                    }
                } else {
                    try {
                        // Try to parse with datetime
                        LocalDateTime startDateTime = parseDateAndTime(dates[0]);
                        LocalDateTime endDateTime = parseDateAndTime(eventStrings[0]);
                        if (isBeforeToday(startDateTime) || isBeforeToday(endDateTime)) {
                            return new ErrorCommand("Event is already over");
                        }
                        if (isBeforeToday(startDateTime) || isBeforeToday(endDateTime)) {
                            return new ErrorCommand("Event is already over");
                        }
                        return new AddCommand(new Event(description, false, startDateTime, endDateTime));
                    } catch (Exception e) {
                        // Otherwise parse as date only
                        LocalDate startDate = parseDateOnly(dates[0]);
                        LocalDate endDate = parseDateOnly(eventStrings[0]);

                        return new AddCommand(new Event(description, false, startDate, endDate));
                    }
                }
            case DELETE:
                if (words.length == 1) {
                    throw new RebootException("Proper usage: delete {task index}");
                }

                if (Parser.isNotInteger(words[1])) {
                    throw new RebootException("Proper usage: delete {task index}");
                }

                if (Integer.parseInt(words[1]) < 1) {
                    throw new RebootException("Ensure that task index is more than 0");
                }

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
                return new ErrorCommand("Invalid input, check the user guide for proper inputs.");
            }
        } catch (DateTimeParseException e) {
            return new ErrorCommand("Date Time format: dd mm yyyy HHmm");
        } catch (RebootException e) {
            return new ErrorCommand(e.getMessage());
        } catch (IllegalArgumentException e) {
            return new ErrorCommand("I do not understand your language");
        }
    }

    /**
     * Checks if a string is a valid integer.
     * @param str String to be converted to integer.
     *
     * @return true if str is an integer, false otherwise.
     */
    public static boolean isNotInteger(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }

        try {
            Integer.parseInt(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }

    /**
     * Converts the given string to date format.
     *
     * @param input String to be converted to date format.
     */
    public static LocalDate parseDateOnly(String input) {
        DateTimeFormatter[] formats = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd"),
                DateTimeFormatter.ofPattern("dd MM yyyy"),
                DateTimeFormatter.ofPattern("yyyy MM dd")
        };

        for (DateTimeFormatter format : formats) {
            try {
                return LocalDate.parse(input, format);
            } catch (Exception e) {
                // Ignore and try next
            }
        }
        throw new IllegalArgumentException("Invalid date format.");
    }

    /**
     * Converts the given string to date time format.
     * @param input String to be converted to date time format.
     */
    public static LocalDateTime parseDateAndTime(String input) {
        DateTimeFormatter[] formats = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm"),
                DateTimeFormatter.ofPattern("dd/MM/yyyy HHmm"),
                DateTimeFormatter.ofPattern("yyyy/MM/dd HHmm"),
                DateTimeFormatter.ofPattern("dd MM yyyy HHmm"),
                DateTimeFormatter.ofPattern("yyyy MM dd HHmm")
        };

        for (DateTimeFormatter format : formats) {
            try {
                return LocalDateTime.parse(input, format);
            } catch (Exception e) {
                // Ignore and try next
            }
        }
        throw new IllegalArgumentException("Invalid date format.");
    }

    public static boolean isBeforeToday(LocalDate date) {
        return date.isBefore(LocalDate.now());
    }

    public static boolean isBeforeToday(LocalDateTime date) {
        return date.isBefore(LocalDateTime.now());
    }

    public static boolean isNotValidRecurrence(String str) {
        str = str.toLowerCase();
        return str.equals("daily") || str.equals("monthly") || str.equals("yearly");
    }
}
