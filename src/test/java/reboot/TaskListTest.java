package reboot;

import reboot.task.Deadline;
import reboot.task.Event;
import reboot.task.Task;
import reboot.task.Todo;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskListTest {

    @Test
    public void size_taskListWith3Tasks_success() {

        TaskList tasks = new TaskList();
        Task todo = new Todo("Read book", false);
        Task deadline = new Deadline("Do iP", false, "02 09 2025 2100");
        Task event = new Event("Yoga Class", false
                , "02 09 2025 1700", "02 09 2025 1900");

        tasks.add(todo);
        tasks.add(deadline);
        tasks.add(event);

        assertEquals(3, tasks.size());
    }

    @Test
    public void addTask_todoTask_success() {

        TaskList tasks = new TaskList();
        Task todo = new Todo("Read book", false);

        tasks.add(todo);
        assertEquals(1, tasks.size());
        assertEquals("[T][ ] Read book", tasks.get(0).toString());
    }

    @Test
    public void addTask_deadlineTask_success() {

        TaskList tasks = new TaskList();
        Task deadline = new Deadline("Do iP", false, "02 09 2025 2100");

        tasks.add(deadline);
        assertEquals(1, tasks.size());
        assertEquals("[D][ ] Do iP (by: 02 09 2025 2100)", tasks.get(0).toString());
    }

    @Test
    public void addTask_eventTask_success() {

        TaskList tasks = new TaskList();
        Task event = new Event("Yoga Class", false
                , "02 09 2025 1700", "02 09 2025 1900");

        tasks.add(event);
        assertEquals(1, tasks.size());
        assertEquals("[E][ ] Yoga Class (from: 02 09 2025 1700 to: 02 09 2025 1900)"
                , tasks.get(0).toString());
    }

    @Test
    public void removeTask_todoTaskRemoval_success() {

        TaskList tasks = new TaskList();
        Task todo = new Todo("Read book", false);
        tasks.add(todo);

        tasks.remove(todo);

        assertEquals(0, tasks.size());
    }

    @Test
    public void clear_filledTaskList_success() {

        TaskList tasks = new TaskList();
        Task todo = new Todo("Read book", false);
        Task deadline = new Deadline("Do iP", false, "02 09 2025 2100");
        Task event = new Event("Yoga Class", false
                , "02 09 2025 1700", "02 09 2025 1900");

        tasks.add(todo);
        tasks.add(deadline);
        tasks.add(event);

        tasks.clear();
        assertEquals(0, tasks.size());
    }

    @Test
    public void isEmpty_emptyTaskList_success() {

        TaskList tasks = new TaskList();
        assertTrue(tasks.isEmpty());
    }

    @Test
    public void isOutOfRange_outOfRange_success() {

        TaskList tasks = new TaskList();
        Task todo = new Todo("Read book", false);
        Task deadline = new Deadline("Do iP", false, "02 09 2025 2100");
        Task event = new Event("Yoga Class", false
                , "02 09 2025 1700", "02 09 2025 1900");

        tasks.add(todo);
        tasks.add(deadline);
        tasks.add(event);

        assertTrue(tasks.isOutOfRange(4));
    }
}
