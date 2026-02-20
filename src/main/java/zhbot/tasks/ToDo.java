package zhbot.tasks;

/**
 * Represents a todo task.
 */
public class ToDo extends Task {

    /**
     * Creates a todo task.
     *
     * @param content Task description.
     */
    public ToDo(String content) {
        super(content);
    }

    /**
     * Formats the task for persistent storage.
     *
     * @return Serialized storage line for this todo.
     */
    @Override
    public String toStorageString() {
        return "T | " + getDoneFlag() + " | " + getContent();
    }

    /**
     * Returns a user-friendly string for display.
     *
     * @return Display string for the todo.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}


