package zhbot.tasks;

/**
 * Represents a reminder for an upcoming deadline task.
 */
public class Remind extends Task {
    private final Deadline deadline;
    private final long daysLeft;

    /**
     * Creates a reminder for a deadline.
     *
     * @param deadline Deadline to remind about.
     * @param daysLeft Days left until due date.
     */
    public Remind(Deadline deadline, long daysLeft) {
        super(requireDeadlineContent(deadline));
        assert daysLeft >= 0 : "Days left should be non-negative.";
        this.deadline = deadline;
        this.daysLeft = daysLeft;
    }

    private static String requireDeadlineContent(Deadline deadline) {
        assert deadline != null : "Reminder deadline should not be null.";
        return deadline.getContent();
    }

    /**
     * Returns the underlying deadline task.
     *
     * @return Deadline this reminder refers to.
     */
    public Deadline getDeadline() {
        return deadline;
    }

    /**
     * Returns days left until the deadline.
     *
     * @return Days left.
     */
    public long getDaysLeft() {
        return daysLeft;
    }

    /**
     * Returns a user-friendly string for display.
     *
     * @return Display string for this reminder.
     */
    @Override
    public String toString() {
        return "[R]" + deadline + " (in " + daysLeft + " day(s))";
    }
}
