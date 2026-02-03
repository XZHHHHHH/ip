package ZH9072;

import ZH9072.Tasks.Task;
import ZH9072.Tasks.TaskList;
import ZH9072.Tasks.ToDo;
import ZH9072.Tasks.Deadline;
import ZH9072.Tasks.Event;

import java.io.IOException;
import java.util.ArrayList;

public class ZH9072Bot {

    private final Storage storage;
    private final TaskList tasks;
    private final Ui ui;
    private final Parser parser;

    public ZH9072Bot(String filePath) {
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

    public static void main(String[] args) {
        new ZH9072Bot("data/duke.txt").run();
    }
}
