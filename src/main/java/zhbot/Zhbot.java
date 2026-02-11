package zhbot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import zhbot.tasks.Deadline;
import zhbot.tasks.Event;
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

                switch (cmd.type) {
                case BYE:
                    ui.showBye();
                    return;

                case LIST:
                    ui.showList(tasks);
                    break;

                case MARK:
                    tasks.get(cmd.index).markDone();
                    storage.save(tasks.asList());
                    ui.showMarked(tasks.get(cmd.index), true);
                    break;

                case UNMARK:
                    tasks.get(cmd.index).markUndone();
                    storage.save(tasks.asList());
                    ui.showMarked(tasks.get(cmd.index), false);
                    break;

                case TODO:
                    tasks.add(new ToDo(cmd.description));
                    storage.save(tasks.asList());
                    ui.showAdded(tasks);
                    break;

                case DEADLINE:
                    tasks.add(new Deadline(cmd.description, cmd.by)); // LocalDate
                    storage.save(tasks.asList());
                    ui.showAdded(tasks);
                    break;

                case EVENT:
                    tasks.add(new Event(cmd.description, cmd.from, cmd.to));
                    storage.save(tasks.asList());
                    ui.showAdded(tasks);
                    break;

                case DELETE:
                    Task removed = tasks.remove(cmd.index);
                    storage.save(tasks.asList());
                    ui.showDeleted(removed, tasks);
                    break;

                case FIND:
                    ui.showFindResults(tasks.findByKeyword(cmd.description));
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
                tasks.get(cmd.index).markDone();
                storage.save(tasks.asList());
                return "Nice! I've marked this task as done:\n  " + tasks.get(cmd.index);

            case UNMARK:
                tasks.get(cmd.index).markUndone();
                storage.save(tasks.asList());
                return "OK, I've marked this task as not done yet:\n  " + tasks.get(cmd.index);

            case TODO:
                tasks.add(new ToDo(cmd.description));
                storage.save(tasks.asList());
                return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
                        + "\nNow you have " + tasks.size() + " tasks in the list.";

            case DEADLINE:
                tasks.add(new Deadline(cmd.description, cmd.by));
                storage.save(tasks.asList());
                return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
                        + "\nNow you have " + tasks.size() + " tasks in the list.";

            case EVENT:
                tasks.add(new Event(cmd.description, cmd.from, cmd.to));
                storage.save(tasks.asList());
                return "Got it. I've added this task:\n  " + tasks.get(tasks.size() - 1)
                        + "\nNow you have " + tasks.size() + " tasks in the list.";

            case DELETE:
                Task removed = tasks.remove(cmd.index);
                storage.save(tasks.asList());
                return "Noted. I've removed this task:\n  " + removed
                        + "\nNow you have " + tasks.size() + " tasks in the list.";

            case FIND:
                StringBuilder findResponse = new StringBuilder("Here are the matching tasks in your list:");
                List<Task> matches = tasks.findByKeyword(cmd.description);
                if (matches.isEmpty()) {
                    findResponse.append("\nNo matching tasks found.");
                } else {
                    for (int i = 0; i < matches.size(); i++) {
                        findResponse.append("\n").append(i + 1).append(".").append(matches.get(i));
                    }
                }
                return findResponse.toString();

            default:
                return "Sorry, I don't understand your command. Please try again.";
            }
        } catch (BotException e) {
            return e.getMessage();
        } catch (IOException e) {
            return "Oops! I could not save your tasks to disk.";
        } catch (IndexOutOfBoundsException e) {
            return "Index is out of range.";
        }
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


