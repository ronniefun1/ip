package reboot.task.recurring;

import reboot.task.Todo;

import java.time.LocalDate;

public class RecurringTodo extends Todo {

    // Only "daily", "weekly", "monthly" is accepted for now
    private final String frequency;
    private LocalDate startDate;

    public RecurringTodo(String description, boolean isDone, String frequency) {
        super(description, isDone);
        this.frequency = frequency;
        this.startDate = LocalDate.now();
    }

    public void setStartDate(LocalDate date) {
        this.startDate = date;
    }

    public void updateDateIfOverdue() {
        LocalDate today = LocalDate.now();
        while (startDate.isBefore(today)) {
            this.unmark();
            switch (frequency) {
            case "daily":
                setStartDate(startDate.plusDays(1));
                break;
            case "weekly":
                setStartDate(startDate.plusWeeks(1));
                break;
            case "monthly":
                setStartDate(startDate.plusMonths(1));
                break;
            default:
                throw new IllegalArgumentException("Unknown recurrence: " + frequency);
            }
        }
    }

    public LocalDate getStartDate() {
        return this.startDate;
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
