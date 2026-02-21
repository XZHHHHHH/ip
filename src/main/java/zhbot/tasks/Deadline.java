package zhbot.tasks;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task with a due date.
 */
public class Deadline extends Task {
    private static final DateTimeFormatter OUTPUT_FORMAT =
            DateTimeFormatter.ofPattern("MMM dd yyyy");
    protected LocalDate by;

    /**
     * Creates a deadline task.
     *
     * @param content Task description.
     * @param by      Due date.
     */
    public Deadline(String content, LocalDate by) {
        super(content);
        assert by != null : "Deadline date should not be null.";
        this.by = by;
    }

    /**
     * Returns the deadline due date.
     *
     * @return Due date.
     */
    public LocalDate getBy() {
        return by;
    }

    /**
     * Returns a user-friendly string for display.
     *
     * @return Display string for the deadline.
     */
    @Override
    public String toString() {
        return "[D]" + super.toString()
                + " (by: " + by.format(OUTPUT_FORMAT) + ")";
    }

    /**
     * Formats the task for persistent storage.
     *
     * @return Serialized storage line for this deadline.
     */
    @Override
    public String toStorageString() {
        return "D | " + (isDone ? "1" : "0")
                + " | " + getContent()
                + " | " + by.toString(); // yyyy-MM-dd
    }
}
