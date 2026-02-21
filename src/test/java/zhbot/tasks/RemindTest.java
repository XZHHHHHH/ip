package zhbot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class RemindTest {
    @Test
    public void constructorAndGetters_storeReminderData() {
        Deadline deadline = new Deadline("submit report", LocalDate.of(2026, 2, 24));

        Remind remind = new Remind(deadline, 3);

        assertSame(deadline, remind.getDeadline());
        assertEquals(3, remind.getDaysLeft());
        assertEquals("submit report", remind.getContent());
    }

    @Test
    public void toString_formatsReminderWithDaysLeft() {
        Deadline deadline = new Deadline("submit report", LocalDate.of(2026, 2, 24));

        Remind remind = new Remind(deadline, 3);

        assertEquals("[R][D][ ] submit report (by: Feb 24 2026) (in 3 day(s))", remind.toString());
    }
}
