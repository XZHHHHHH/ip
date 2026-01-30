import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles loading and saving tasks to disk.
 */
public class Storage {
    private static final Path DEFAULT_PATH = Paths.get("data", "duke.txt");

    private final Path filePath;

    public Storage() {
        this(DEFAULT_PATH);
    }

    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();

        if (Files.notExists(filePath)) {
            return tasks;
        }

        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);
        for (String line : lines) {
            if (line == null || line.trim().isEmpty()) {
                continue;
            }

            try {
                Task task = parseLine(line.trim());
                tasks.add(task);
            } catch (IllegalArgumentException ignored) {

            }
        }
        return tasks;
    }

    public void save(List<Task> tasks) throws IOException {
        Path parent = filePath.getParent();
        if (parent != null && Files.notExists(parent)) {
            Files.createDirectories(parent);
        }

        List<String> output = new ArrayList<>();
        for (Task task : tasks) {
            output.add(task.toStorageString());
        }

        Files.write(filePath, output, StandardCharsets.UTF_8);
    }

    private Task parseLine(String line) {
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid line");
        }

        String type = parts[0];
        boolean isDone;
        if ("1".equals(parts[1])) {
            isDone = true;
        } else {
            isDone = false;
        }

        String content = parts[2];
        Task task;

        if ("T".equals(type)) {
            task = new ToDo(content);
        } else if ("D".equals(type)) {
            if (parts.length < 4) {
                throw new IllegalArgumentException("Deadline corrupted");
            }
            task = new Deadline(content, parts[3]);
        } else if ("E".equals(type)) {
            if (parts.length < 5) {
                throw new IllegalArgumentException("Event corrupted");
            }
            task = new Event(content, parts[3], parts[4]);
        } else {
            throw new IllegalArgumentException("Unknown task type");
        }

        task.setDone(isDone);
        return task;
    }
}
