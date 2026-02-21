package zhbot.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

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

    @Test
    public void findByKeyword_matchesByDescriptionCaseInsensitive() {
        TaskList list = new TaskList();
        list.add(new Task("read Book"));
        list.add(new Task("return book"));
        list.add(new Task("clean room"));

        List<Task> matches = list.findByKeyword("book");

        assertEquals(2, matches.size());
        assertEquals("[ ] read Book", matches.get(0).toString());
        assertEquals("[ ] return book", matches.get(1).toString());
    }

    @Test
    public void findByKeyword_noMatches_returnsEmptyList() {
        TaskList list = new TaskList();
        list.add(new Task("exercise"));

        List<Task> matches = list.findByKeyword("book");

        assertEquals(0, matches.size());
    }

    @Test
    public void findUpcomingReminders_returnsOnlyUndoneDeadlinesWithinWindow() {
        TaskList list = new TaskList();
        Deadline dueToday = new Deadline("today", LocalDate.of(2026, 2, 21));
        Deadline dueSoon = new Deadline("soon", LocalDate.of(2026, 2, 24));
        Deadline dueLater = new Deadline("later", LocalDate.of(2026, 3, 10));
        Deadline doneSoon = new Deadline("done soon", LocalDate.of(2026, 2, 23));
        doneSoon.markDone();

        list.add(dueToday);
        list.add(dueSoon);
        list.add(dueLater);
        list.add(doneSoon);
        list.add(new Task("todo"));

        List<Remind> reminders = list.findUpcomingReminders(LocalDate.of(2026, 2, 21), 3);

        assertEquals(2, reminders.size());
        assertSame(dueToday, reminders.get(0).getDeadline());
        assertEquals(0, reminders.get(0).getDaysLeft());
        assertSame(dueSoon, reminders.get(1).getDeadline());
        assertEquals(3, reminders.get(1).getDaysLeft());
    }
}


