package ZH9072.Tasks;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ToDoTest {
    @Test
    public void toStorageString_usesTodoFormat() {
        ToDo todo = new ToDo("read notes");
        todo.markDone();

        assertEquals("T | 1 | read notes", todo.toStorageString());
    }

    @Test
    public void toString_usesTodoPrefix() {
        ToDo todo = new ToDo("buy milk");

        assertEquals("[T][ ] buy milk", todo.toString());
    }
}
