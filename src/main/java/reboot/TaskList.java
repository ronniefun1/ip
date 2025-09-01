package reboot;

import reboot.task.Task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public void clear() {
        tasks.clear();
    }

    public void remove(Task t) {
        tasks.remove(t);
    }

    public int size() {
        return tasks.size();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public ArrayList<Task> getAll() {
        return tasks;
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    public boolean isOutOfRange(int n) {
        return n > tasks.size();
    }
}
