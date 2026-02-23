package zhbot;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import zhbot.tasks.Deadline;
import zhbot.tasks.Event;
import zhbot.tasks.Remind;
import zhbot.tasks.Task;
import zhbot.tasks.TaskList;
import zhbot.tasks.ToDo;

/**
 * Main application class for the ZH9072 task bot.
 */
public class Zhbot {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    /**
     * Creates a bot instance that reads/writes from the given file path.
     *
     * @param filePath Storage file path.
     */
    public Zhbot(String filePath) {
        assert filePath != null : "Storage file path should not be null.";
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();

        TaskList loaded;
        try {
            ArrayList<Task> loadedTasks = storage.load();
            loaded = new TaskList(loadedTasks);
        } catch (IOException e) {
            ui.showLoadingError();
            loaded = new TaskList();
        }
        tasks = loaded;
    }

    /**
     * Runs the main command loop until the user exits.
     */
    public void run() {
        ui.showWelcome();

        while (true) {
            String input = ui.readCommand();

            try {
                Parser.Command cmd = parser.parse(input);
                assert cmd != null : "Parser should return a command for valid input.";

                switch (cmd.type) {
                case BYE:
                    ui.showBye();
                    return;

                case LIST:
                    ui.showList(tasks);
                    break;

                case MARK:
                    ui.showMarked(updateTaskStatus(cmd.index, true), true);
                    break;

                case UNMARK:
                    ui.showMarked(updateTaskStatus(cmd.index, false), false);
                    break;

                case TODO:
                    addTask(new ToDo(cmd.description));
                    ui.showAdded(tasks);
                    break;

                case DEADLINE:
                    addTask(new Deadline(cmd.description, cmd.by));
                    ui.showAdded(tasks);
                    break;

                case EVENT:
                    addTask(new Event(cmd.description, cmd.from, cmd.to));
                    ui.showAdded(tasks);
                    break;

                case DELETE:
                    Task removed = deleteTask(cmd.index);
                    ui.showDeleted(removed, tasks);
                    break;

                case FIND:
                    ui.showFindResults(tasks.findByKeyword(cmd.description));
                    break;

                case REMIND:
                    ui.showReminders(tasks.findUpcomingReminders(LocalDate.now(), cmd.daysAhead), cmd.daysAhead);
                    break;

                default:
                    throw new BotException("Sorry, I don't understand your command. Please try again.");
                }

            } catch (BotException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showSaveError();
            } catch (IndexOutOfBoundsException e) {
                ui.showError("Index is out of range.");
            } catch (RuntimeException e) {
                ui.showError("Something went wrong while handling your command. Please try again.");
            }
        }
    }

    /**
     * Generates a response for the user's chat message.
     *
     * @param input User input string.
     * @return Bot response string.
     */
    public String getResponse(String input) {
        try {
            Parser.Command cmd = parser.parse(input);
            assert cmd != null : "Parser should return a command for valid input.";

            switch (cmd.type) {
            case BYE:
                return "Bye. Hope to see you again soon!";

            case LIST:
                StringBuilder listResponse = new StringBuilder("Here are the tasks in your list:");
                if (tasks.size() == 0) {
                    listResponse.append("\n(No tasks yet.)");
                } else {
                    for (int i = 0; i < tasks.size(); i++) {
                        listResponse.append("\n").append(i + 1).append(".").append(tasks.get(i));
                    }
                }
                return listResponse.toString();

            case MARK:
                return "Nice! I've marked this task as done:\n  "
                        + updateTaskStatus(cmd.index, true);

            case UNMARK:
                return "OK, I've marked this task as not done yet:\n  "
                        + updateTaskStatus(cmd.index, false);

            case TODO:
                addTask(new ToDo(cmd.description));
                return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
                        + "\nNow you have " + tasks.size() + " tasks in the list.";

            case DEADLINE:
                addTask(new Deadline(cmd.description, cmd.by));
                return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
                        + "\nNow you have " + tasks.size() + " tasks in the list.";

            case EVENT:
                addTask(new Event(cmd.description, cmd.from, cmd.to));
                return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
                        + "\nNow you have " + tasks.size() + " tasks in the list.";

            case DELETE:
                Task removed = deleteTask(cmd.index);
                return "Noted. I've removed this task:\n  " + removed
                        + "\nNow you have " + tasks.size() + " tasks in the list.";

            case FIND:
                List<Task> matches = tasks.findByKeyword(cmd.description);
                if (matches.isEmpty()) {
                    return "No matching tasks found.";
                }
                StringBuilder findResponse = new StringBuilder("Here are the matching tasks in your list:");
                for (int i = 0; i < matches.size(); i++) {
                    findResponse.append("\n").append(i + 1).append(".").append(matches.get(i));
                }
                return findResponse.toString();

            case REMIND:
                return buildReminderResponse(cmd.daysAhead);

            default:
                return "Sorry, I don't understand your command. Please try again.";
            }
        } catch (BotException e) {
            return e.getMessage();
        } catch (IOException e) {
            return "Oops! I could not save your tasks to disk.";
        } catch (IndexOutOfBoundsException e) {
            return "Index is out of range.";
        } catch (RuntimeException e) {
            return "Something went wrong while handling your command. Please try again.";
        }
    }

    private Task updateTaskStatus(int index, boolean isDone) throws IOException {
        Task task = tasks.get(index);
        if (isDone) {
            task.markDone();
        } else {
            task.markUndone();
        }
        storage.save(tasks.asList());
        return task;
    }

    private void addTask(Task task) throws IOException {
        tasks.add(task);
        storage.save(tasks.asList());
    }

    private Task deleteTask(int index) throws IOException {
        Task removed = tasks.remove(index);
        storage.save(tasks.asList());
        return removed;
    }

    /**
     * Returns the startup greeting and command guide.
     *
     * @return Greeting text shown when the app starts.
     */
    public String getWelcomeMessage() {
        return "Hello! I'm ZH9072Bot\n"
                + "I can help you manage your tasks.\n"
                + "Available commands:\n"
                + "1. list\n"
                + "2. todo <description>\n"
                + "3. deadline <description> /by <yyyy-MM-dd>\n"
                + "4. event <description> /from <start> /to <end>\n"
                + "5. mark <task number>\n"
                + "6. unmark <task number>\n"
                + "7. delete <task number>\n"
                + "8. find <keyword>\n"
                + "9. remind [days]\n"
                + "10. bye";
    }

    private String buildReminderResponse(int daysAhead) {
        List<Remind> reminders = tasks.findUpcomingReminders(LocalDate.now(), daysAhead);
        StringBuilder response = new StringBuilder(
                "Here are your upcoming deadlines in the next " + daysAhead + " day(s):");
        if (reminders.isEmpty()) {
            response.append("\nNo upcoming deadlines. Nice work.");
        } else {
            for (int i = 0; i < reminders.size(); i++) {
                response.append("\n").append(i + 1).append(".").append(reminders.get(i));
            }
        }
        return response.toString();
    }

    /**
     * Entry point for the application.
     *
     * @param args Command-line arguments (unused).
     */
    public static void main(String[] args) {
        new Zhbot("data/zh.txt").run();
    }
}
