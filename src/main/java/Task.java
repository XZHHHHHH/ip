/**
 * Represents a task with content and a completion status.
 */
public class Task {
    private final String content;
    private boolean isDone;

    public Task(String content) {
        this.content = content;
        this.isDone = false;
    }

    public String getContent() {
        return content;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markUndone() {
        this.isDone = false;
    }

    public String getStatusIcon() {
        if (isDone) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    public String toStorageString() {
        String doneFlag;
        if (isDone) {
            doneFlag = "1";
        } else {
            doneFlag = "0";
        }
        return "? | " + doneFlag + " | " + content;
    }

    @Override
    public String toString() {
        return getStatusIcon() + " " + content;
    }
}
