/**
 * Represents a deadline task.
 */
public class Deadline extends Task {
    private final String by;

    public Deadline(String content, String by) {
        super(content);
        this.by = by;
    }

    public String getBy() {
        return by;
    }

    @Override
    public String toStorageString() {
        String doneFlag;
        if (isDone()) {
            doneFlag = "1";
        } else {
            doneFlag = "0";
        }
        return "D | " + doneFlag + " | " + getContent() + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}
