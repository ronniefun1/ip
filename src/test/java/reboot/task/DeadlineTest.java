package reboot.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DeadlineTest {

    @Test
    public void toString_withDateOnly_success() {
        Deadline d = new Deadline("return book", false
                , "2025-12-02");
        String expected = "[D][ ] return book (by: 02 12 2025)";
        assertEquals(expected, d.toString());
    }

    @Test
    public void toString_withDateTime_success() {
        Deadline d = new Deadline("return book", false
                , "2025-12-02 1800");
        String expected = "[D][ ] return book (by: 02 12 2025 1800)";
        assertEquals(expected, d.toString());
    }

    @Test
    public void toFileString_withDateOnly_success() {
        Deadline d = new Deadline("return book", false
                , "2025-12-02");
        String expected = "D | 0 | return book | 02 12 2025";
        assertEquals(expected, d.toFileString());
    }

    @Test
    public void toFileString_withDateTime_success() {
        Deadline d = new Deadline("return book", false
                , "2025-12-02 1800");
        String expected = "D | 0 | return book | 02 12 2025 1800";
        assertEquals(expected, d.toFileString());
    }
}
