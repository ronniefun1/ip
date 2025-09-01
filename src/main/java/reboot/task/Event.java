package reboot.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.LocalDate;

public class Event extends Task {

    // If date is the only thing provided
    private LocalDate startDate;
    private LocalDate endDate;

    // If time is provided
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public Event(String description, boolean isDone, String start, String end) {
        super(description, isDone);
        parseInput(start, end);
    }

    @Override
    public String toFileString() {
        if (startDate != null) {
            DateTimeFormatter start = DateTimeFormatter.ofPattern("dd MM yyyy");
            DateTimeFormatter end = DateTimeFormatter.ofPattern("dd MM yyyy");
            return "D | " + (isDone ? "1" : "0") + " | " + description
                    + " | " + startDate.format(start) + " | " + endDate.format(end);
        } else {
            DateTimeFormatter start = DateTimeFormatter.ofPattern("dd MM yyyy HHmm");
            DateTimeFormatter end = DateTimeFormatter.ofPattern("dd MM yyyy HHmm");
            return "D | " + (isDone ? "1" : "0") + " | " + description
                    + " | " + startDateTime.format(start) + " | " + endDateTime.format(end);
        }
    }

    @Override
    public String toString() {
        if (startDate != null) {
            DateTimeFormatter start = DateTimeFormatter.ofPattern("dd MM yyyy");
            DateTimeFormatter end = DateTimeFormatter.ofPattern("dd MM yyyy");
            return "[E]" + super.toString() + " (from: "
                    + startDate.format(start) + " to: " + endDate.format(end) + ")";
        } else {
            DateTimeFormatter start = DateTimeFormatter.ofPattern("dd MM yyyy HHmm");
            DateTimeFormatter end = DateTimeFormatter.ofPattern("dd MM yyyy HHmm");
            return "[E]" + super.toString() + " (from: "
                    + startDateTime.format(start) + " to: " + endDateTime.format(end) + ")";
        }
    }

    private void parseInput(String start, String end) {
        try {
            // Try to parse with datetime
            this.startDateTime = parseDateTime(start);
            this.endDateTime = parseDateTime(end);
        } catch (Exception e) {
            // Otherwise parse as date only
            this.startDate = parseDate(start);
            this.endDate = parseDate(end);
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
