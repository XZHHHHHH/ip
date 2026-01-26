public class Task {
    private String content;
    private boolean isDone;

    public Task(String content) {
        this.content = content;
        this.isDone = false;
    }

    public void markDone() {
        this.isDone = true;
    }

    public void unMark() {
        this.isDone = false;
    }

    public String iconStatus() {
        if(isDone == true) {
            return "[X]";
        } else {
            return "[ ]";
        }
    }

    @Override
    public String toString() {
        return iconStatus() + " " + content;
    }
    
}