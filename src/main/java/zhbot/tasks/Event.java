package zhbot.tasks;

/**
 * Represents an event task.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    /**
     * Creates an event task with a time range.
     *
     * @param content Task description.
     * @param from    Start time or description.
     * @param to      End time or description.
     */
    public Event(String content, String from, String to) {
        super(content);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the event start description.
     *
     * @return Start time or description.
     */
    public String getFrom() {
        return from;
    }

    /**
     * Returns the event end description.
     *
     * @return End time or description.
     */
    public String getTo() {
        return to;
    }

    /**
     * Formats the task for persistent storage.
     *
     * @return Serialized storage line for this event.
     */
    @Override
    public String toStorageString() {
        return "E | " + getDoneFlag() + " | " + getContent()
                + " | " + from + " | " + to;
    }

    /**
     * Returns a user-friendly string for display.
     *
     * @return Display string for the event.
     */
    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}


