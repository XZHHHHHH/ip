package zhbot;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import zhbot.tasks.Deadline;
import zhbot.tasks.Event;
import zhbot.tasks.Task;
import zhbot.tasks.ToDo;


/**
 * Handles loading and saving tasks to disk.
 */
public class Storage {
    private static final Path DEFAULT_PATH = Paths.get("data", "zh.txt");

    private final Path filePath;

    /**
     * Creates a storage instance using the default path.
     */
    public Storage() {
        this(DEFAULT_PATH);
    }

    /**
     * Creates a storage instance using the given file path.
     *
     * @param filePath Path to the storage file.
     */
    public Storage(String filePath) {
        this(Paths.get(filePath));
    }

    /**
     * Creates a storage instance using the given file path.
     *
     * @param filePath Path to the storage file.
     */
    public Storage(Path filePath) {
        assert filePath != null : "Storage file path should not be null.";
        this.filePath = filePath;
    }


    /**
     * Loads tasks from disk.
     *
     * @return List of loaded tasks (empty if file missing).
     * @throws IOException If the file cannot be read.
     */
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
                // Ignore malformed lines.
            }
        }
        return tasks;
    }

    /**
     * Saves tasks to disk, creating directories if needed.
     *
     * @param tasks Tasks to persist.
     * @throws IOException If the file cannot be written.
     */
    public void save(List<Task> tasks) throws IOException {
        assert tasks != null : "Task list to save should not be null.";
        Path parent = filePath.getParent();
        if (parent != null && Files.notExists(parent)) {
            Files.createDirectories(parent);
        }

        List<String> output = new ArrayList<>();
        for (Task task : tasks) {
            assert task != null : "Stored task entries should not be null.";
            output.add(task.toStorageString());
        }

        Files.write(filePath, output, StandardCharsets.UTF_8);
    }

    /**
     * Parses a single storage line into a task.
     *
     * @param line Storage line content.
     * @return Parsed task.
     * @throws IllegalArgumentException If the line is malformed.
     */
    private Task parseLine(String line) {
        assert line != null : "Storage line should not be null.";
        assert !line.trim().isEmpty() : "Storage line should not be empty.";
        String[] parts = line.split("\\s*\\|\\s*");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid line");
        }

        String type = parts[0];
        boolean isDone = "1".equals(parts[1]);

        String content = parts[2];
        Task task;

        if ("T".equals(type)) {
            task = new ToDo(content);
        } else if ("D".equals(type)) {
            if (parts.length < 4) {
                throw new IllegalArgumentException("Deadline corrupted");
            }
            LocalDate by = LocalDate.parse(parts[3]);
            task = new Deadline(parts[2], by);
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
