import java.util.Scanner;

/**
 * Handles all user interaction (input/output).
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(LINE);
        System.out.println("Hello! I'm ZH9072Bot");
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    public void showBye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showError(String message) {
        System.out.println(LINE);
        System.out.println(message);
        System.out.println(LINE);
    }

    public void showLoadingError() {
        System.out.println(LINE);
        System.out.println("Warning: failed to load saved tasks. Starting with an empty list.");
        System.out.println(LINE);
    }

    public void showSaveError() {
        System.out.println(LINE);
        System.out.println("Oops! I could not save your tasks to disk.");
        System.out.println(LINE);
    }

    public void showList(TaskList tasks) {
        System.out.println(LINE);
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        System.out.println(LINE);
    }

    public void showAdded(TaskList tasks) {
        System.out.println(LINE);
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + tasks.get(tasks.size() - 1));
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showDeleted(Task removed, TaskList tasks) {
        System.out.println(LINE);
        System.out.println("Noted. I've removed this task:");
        System.out.println("  " + removed);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    public void showMarked(Task task, boolean done) {
        System.out.println(LINE);
        if (done) {
            System.out.println("Nice! I've marked this task as done:");
        } else {
            System.out.println("OK, I've marked this task as not done yet:");
        }
        System.out.println("  " + task);
        System.out.println(LINE);
    }
}
