package reboot.task.reccuring;

import reboot.task.Deadline;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RecurringDeadline extends Deadline {

    // Only "daily", "weekly", "monthly" is accepted for now
    private final String frequency;

    public RecurringDeadline(String description, boolean isDone, LocalDate dueDate,
                             String frequency) {
        super(description, isDone, dueDate);
        this.frequency = frequency.toLowerCase();
    }

    public RecurringDeadline(String description, boolean isDone, LocalDateTime dueDateTime,
                             String frequency) {
        super(description, isDone, dueDateTime);
        this.frequency = frequency.toLowerCase();
    }

    /**
     * Check if the deadline day has passed and auto-update to the next occurrence.
     */
    public void updateDateIfOverdue() {
        LocalDate today = LocalDate.now();
        while (getDueDate().isBefore(today)) {
            this.unmark();
            switch (frequency) {
            case "daily":
                setDueDate(getDueDate().plusDays(1));
                break;
            case "weekly":
                setDueDate(getDueDate().plusWeeks(1));
                break;
            case "monthly":
                setDueDate(getDueDate().plusMonths(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown recurrence: " + frequency);
            }
        }
    }

    /**
     * Check if the deadline day and time has passed and auto-update to the next occurrence.
     */
    public void updateDateTimeIfOverdue() {
        LocalDateTime today = LocalDateTime.now();
        while (getDueDateTime().isBefore(today)) {
            switch (frequency) {
            case "daily":
                setDueDateTime(getDueDateTime().plusDays(1));
                break;
            case "weekly":
                setDueDateTime(getDueDateTime().plusWeeks(1));
                break;
            case "monthly":
                setDueDateTime(getDueDateTime().plusMonths(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown recurrence: " + frequency);
            }
        }
    }

    @Override
    public String toFileString() {
        return "R" + super.toFileString() + " | " + frequency;
    }

    @Override
    public String toString() {
        return super.toString() + " (repeats " + frequency + ")";
    }
}
