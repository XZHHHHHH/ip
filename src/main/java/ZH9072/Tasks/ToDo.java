package ZH9072.Tasks;

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
        String doneFlag;
        if (isDone()) {
            doneFlag = "1";
        } else {
            doneFlag = "0";
        }
        return "T | " + doneFlag + " | " + getContent();
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
