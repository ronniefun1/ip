public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    // Mark task done
    public void mark() {
        this.isDone = true;
    }

    // Mark task not done
    public void unmark() {
        this.isDone = false;
    }

    public String toFileString() {
        return null;
    }


    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
