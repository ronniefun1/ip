package reboot;

import java.util.ArrayList;
import java.util.List;

import reboot.task.Task;

/**
 * Represents a tasklist that will store all the user tasks.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Constructs a new tasklist that can be used to store tasks.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Constructs a tasklist from a list of tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a specified task to the tasklist.
     * @param t task to be added to tasklist.
     */
    public void add(Task t) {
        tasks.add(t);
    }

    /**
     * Clears the tasklist.
     */
    public void clear() {
        tasks.clear();
    }

    /**
     * Removes a specified task to the tasklist.
     * @param t task to be removed from tasklist.
     */
    public void remove(Task t) {
        tasks.remove(t);
    }

    /**
     * Returns the size of the tasklist.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the task in the specified index.
     * @param index Index of the specified task in tasklist
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the whole tasklist.
     */
    public ArrayList<Task> getAll() {
        return tasks;
    }

    /**
     * Returns true if tasklist is empty and false if otherwise.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Returns true if specified index is out of range of tasklist
     * and false if otherwise.
     * @param n Index of input task.
     */
    public boolean isOutOfRange(int n) {
        return n > tasks.size();
    }

    /**
     * Finds tasks in tasklist which contains the specified keyword.
     * @param keyword Keyword to search for in the tasklist.
     */
    public TaskList findTasks(String keyword) {
        TaskList results = new TaskList();

        for (Task t : tasks) {
            if (t.getDescription().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(t);
            }
        }

        return results;
    }
}
