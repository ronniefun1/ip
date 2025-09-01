package reboot.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Deadline extends Task {

    private LocalDate dueDate; // If date is the only thing provided
    private LocalDateTime dueDateTime; // If time is provided

    public Deadline(String description, boolean isDone, String dueDate) {
        super(description, isDone);
        parseInput(dueDate);
    }

    @Override
    public String toFileString() {
        if (dueDate != null) {
            DateTimeFormatter output = DateTimeFormatter.ofPattern("dd MM yyyy");
            return "D | " + (isDone ? "1" : "0") + " | "
                    + description + " | " + dueDate.format(output);
        } else {
            DateTimeFormatter output = DateTimeFormatter.ofPattern("dd MM yyyy HHmm");
            return "D | " + (isDone ? "1" : "0") + " | "
                    + description + " | " + dueDateTime.format(output);
        }
    }

    @Override
    public String toString() {
        if (dueDate != null) {
            DateTimeFormatter output = DateTimeFormatter.ofPattern("dd MM yyyy");
            return "[D]" + super.toString() + " (by: " + dueDate.format(output) + ")";
        } else {
            DateTimeFormatter output = DateTimeFormatter.ofPattern("dd MM yyyy HHmm");
            return "[D]" + super.toString() + " (by: " + dueDateTime.format(output) + ")";
        }
    }

    private void parseInput(String input) {
        try {
            // Try to parse with datetime
            this.dueDateTime = parseDateTime(input);
        } catch (Exception e) {
            // Otherwise parse as date only
            this.dueDate = parseDate(input);
        }
    }

    public static LocalDateTime parseDateTime(String input) {
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

    public static LocalDate parseDate(String input) {
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
}
