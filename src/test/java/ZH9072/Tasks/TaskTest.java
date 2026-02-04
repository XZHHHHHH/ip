package ZH9072.Tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TaskTest {
    @Test
    public void getStatusIcon_newTask_showsUndone() {
        Task task = new Task("read book");

        assertEquals("[ ]", task.getStatusIcon());
        assertFalse(task.isDone());
    }

    @Test
    public void markDone_thenMarkUndone_updatesStatus() {
        Task task = new Task("wash car");

        task.markDone();
        assertTrue(task.isDone());
        assertEquals("[X]", task.getStatusIcon());

        task.markUndone();
        assertFalse(task.isDone());
        assertEquals("[ ]", task.getStatusIcon());
    }

    @Test
    public void toStorageString_reflectsDoneFlagAndContent() {
        Task task = new Task("file taxes");
        task.markDone();

        assertEquals("? | 1 | file taxes", task.toStorageString());
    }

    @Test
    public void toString_includesStatusAndContent() {
        Task task = new Task("practice");

        assertEquals("[ ] practice", task.toString());
    }
}
