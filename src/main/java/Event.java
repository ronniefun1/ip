public class Event extends Task {
    private String startTime;
    private String endTime;

    public Event(String description, boolean isDone, String start, String end) {
        super(description, isDone);
        this.startTime = start;
        this.endTime = end;
    }

    @Override
    public String toFileString() {
        return "E | " + (isDone ? "1" : "0") + " | " + description
                + " | " + startTime + " | " + endTime ;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString()
                + " (from: " + startTime + " to: " + endTime + ")";
    }
}
