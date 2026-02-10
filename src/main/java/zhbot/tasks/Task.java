package zhbot.tasks;

/**
 * Represents a task with content and a completion status.
 */
public class Task {
    protected boolean isDone;
    private final String content;

    /**
     * Creates a task with the given content.
     *
     * @param content Task description.
     */
    public Task(String content) {
        this.content = content;
        this.isDone = false;
    }

    /**
     * Returns the task description.
     *
     * @return Task description.
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns whether the task is marked done.
     *
     * @return {@code true} if done.
     */
    public boolean isDone() {
        return isDone;
    }

    /**
     * Sets the done status.
     *
     * @param isDone Whether the task is done.
     */
    public void setDone(boolean isDone) {
        this.isDone = isDone;
    }

    /**
     * Marks the task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done.
     */
    public void markUndone() {
        this.isDone = false;
    }

    /**
     * Returns the display icon for the done status.
     *
     * @return Status icon string.
     */
    public String getStatusIcon() {
        if (isDone) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    /**
     * Formats the task for persistent storage.
     *
     * @return Serialized storage line for this task.
     */
    public String toStorageString() {
        String doneFlag;
        if (isDone) {
            doneFlag = "1";
        } else {
            doneFlag = "0";
        }
        return "? | " + doneFlag + " | " + content;
    }

    /**
     * Returns a user-friendly string for display.
     *
     * @return Display string for the task.
     */
    @Override
    public String toString() {
        return getStatusIcon() + " " + content;
    }
}


