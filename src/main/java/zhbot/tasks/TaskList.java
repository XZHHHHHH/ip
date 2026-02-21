package zhbot.tasks;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the list of tasks and task-related operations.
 */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Wraps an existing list of tasks.
     *
     * @param tasks Backing list to use.
     */
    public TaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Backing task list should not be null.";
        this.tasks = tasks;
    }

    /**
     * Adds a task to the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        assert task != null : "Task to add should not be null.";
        tasks.add(task);
    }

    /**
     * Removes a task at the given index.
     *
     * @param index Zero-based index.
     * @return Removed task.
     */
    public Task remove(int index) {
        assert index >= 0 && index < tasks.size() : "Task index should be within range.";
        return tasks.remove(index);
    }

    /**
     * Returns the task at the given index.
     *
     * @param index Zero-based index.
     * @return Task at the index.
     */
    public Task get(int index) {
        assert index >= 0 && index < tasks.size() : "Task index should be within range.";
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks.
     *
     * @return Task count.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns the backing list of tasks.
     *
     * @return Backing task list.
     */
    public List<Task> asList() {
        return new ArrayList<>(tasks);
    }

    /**
     * Finds tasks that contain the given keyword.
     *
     * @param keyword Keyword to search for.
     * @return List of matching tasks.
     */
    public List<Task> findByKeyword(String keyword) {
        assert keyword != null : "Search keyword should not be null.";
        List<Task> matches = new ArrayList<>();
        String loweredKeyword = keyword.toLowerCase();
        for (Task task : tasks) {
            if (task.getContent().toLowerCase().contains(loweredKeyword)) {
                matches.add(task);
            }
        }
        return matches;
    }

    /**
     * Finds reminders for unfinished deadlines due within the next {@code daysAhead} days.
     *
     * @param today     Baseline date for reminder calculations.
     * @param daysAhead Number of days ahead, inclusive.
     * @return List of reminders.
     */
    public List<Remind> findUpcomingReminders(LocalDate today, int daysAhead) {
        assert today != null : "Reminder base date should not be null.";
        assert daysAhead >= 0 : "Days-ahead value should be non-negative.";

        LocalDate latestDate = today.plusDays(daysAhead);
        List<Remind> reminders = new ArrayList<>();

        for (Task task : tasks) {
            if (!(task instanceof Deadline) || task.isDone()) {
                continue;
            }

            Deadline deadline = (Deadline) task;
            LocalDate by = deadline.getBy();
            boolean isWithinRange = (by.isEqual(today) || by.isAfter(today))
                    && (by.isEqual(latestDate) || by.isBefore(latestDate));
            if (isWithinRange) {
                long daysLeft = ChronoUnit.DAYS.between(today, by);
                reminders.add(new Remind(deadline, daysLeft));
            }
        }
        return reminders;
    }
}


