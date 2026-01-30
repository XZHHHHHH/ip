/**
 * Represents a todo task.
 */
public class ToDo extends Task {

    public ToDo(String content) {
        super(content);
    }

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

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
