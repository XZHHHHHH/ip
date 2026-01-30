/**
 * Represents an event task.
 */
public class Event extends Task {
    private final String from;
    private final String to;

    public Event(String content, String from, String to) {
        super(content);
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toStorageString() {
        String doneFlag;
        if (isDone()) {
            doneFlag = "1";
        } else {
            doneFlag = "0";
        }
        return "E | " + doneFlag + " | " + getContent()
                + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}
