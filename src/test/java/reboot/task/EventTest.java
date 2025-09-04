package reboot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void toString_withDateOnly_success() {
        Event e = new Event("project meeting", false,
                "2025-12-02", "2025-12-03");
        String expected = "[E][ ] project meeting (from: 02 12 2025 to: 03 12 2025)";
        assertEquals(expected, e.toString());
    }

    @Test
    public void toString_withDateTime_success() {
        Event e = new Event("project meeting", false,
                "2025-12-02 0800", "2025-12-02 1000");
        String expected = "[E][ ] project meeting (from: 02 12 2025 0800 to: 02 12 2025 1000)";
        assertEquals(expected, e.toString());
    }

    @Test
    public void toFileString_withDateOnly_success() {
        Event e = new Event("project meeting", false,
                "2025-12-02", "2025-12-03");
        String expected = "E | 0 | project meeting | 02 12 2025 | 03 12 2025";
        assertEquals(expected, e.toFileString());
    }

    @Test
    public void toFileString_withDateTime_success() {
        Event e = new Event("project meeting", false,
                "2025-12-02 0800", "2025-12-02 1000");
        String expected = "E | 0 | project meeting | 02 12 2025 0800 | 02 12 2025 1000";
        assertEquals(expected, e.toFileString());
    }
}
