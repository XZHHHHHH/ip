package ZH9072.Tasks;

import org.junit.jupiter.api.Test;
import java.util.List;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TaskListTest {
    @Test
    public void addGetRemove_updatesSizeAndItems() {
        TaskList list = new TaskList();
        Task task = new Task("exercise");

        list.add(task);
        assertEquals(1, list.size());
        assertSame(task, list.get(0));

        Task removed = list.remove(0);
        assertSame(task, removed);
        assertEquals(0, list.size());
    }

    @Test
    public void asList_returnsBackingList() {
        TaskList list = new TaskList();
        list.add(new Task("study"));

        List<Task> backing = list.asList();
        assertEquals(1, backing.size());
        assertEquals("[ ] study", backing.get(0).toString());
    }

    @Test
    public void constructor_withExistingList_usesProvidedItems() {
        ArrayList<Task> seed = new ArrayList<>();
        seed.add(new Task("seeded"));

        TaskList list = new TaskList(seed);

        assertEquals(1, list.size());
        assertEquals("[ ] seeded", list.get(0).toString());
    }
}
