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
        return isDone ? "[X]" : "[ ]";
    }

    /**
     * Returns the serialized done flag used by storage.
     *
     * @return "1" if done, otherwise "0".
     */
    protected String getDoneFlag() {
        return isDone ? "1" : "0";
    }

    /**
     * Formats the task for persistent storage.
     *
     * @return Serialized storage line for this task.
     */
    public String toStorageString() {
        return "? | " + getDoneFlag() + " | " + content;
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


