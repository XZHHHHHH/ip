package zhbot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTest {
    @Test
    public void toString_formatsWithTypeStatusContentAndDate() {
        Deadline deadline = new Deadline("submit report", LocalDate.of(2026, 2, 4));

        assertEquals("[D][ ] submit report (by: Feb 04 2026)", deadline.toString());
    }

    @Test
    public void toStorageString_includesDoneFlagAndIsoDate() {
        Deadline deadline = new Deadline("pay rent", LocalDate.of(2026, 2, 1));
        deadline.markDone();

        assertEquals("D | 1 | pay rent | 2026-02-01", deadline.toStorageString());
    }
}


