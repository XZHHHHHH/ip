package zhbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import zhbot.tasks.Deadline;
import zhbot.tasks.Event;
import zhbot.tasks.Task;
import zhbot.tasks.ToDo;

public class StorageTest {
    @TempDir
    Path tempDir;

    @Test
    public void load_missingFile_returnsEmptyList() throws Exception {
        Path file = tempDir.resolve("missing.txt");
        Storage storage = new Storage(file);

        ArrayList<Task> tasks = storage.load();

        assertTrue(tasks.isEmpty());
    }

    @Test
    public void save_thenLoad_roundTripsTasks() throws Exception {
        Path file = tempDir.resolve("zh.txt");
        Storage storage = new Storage(file.toString());

        List<Task> tasks = new ArrayList<>();
        tasks.add(new ToDo("read"));
        tasks.add(new Deadline("submit", LocalDate.of(2026, 2, 4)));
        tasks.add(new Event("conference", "Mon", "Tue"));
        tasks.get(2).markDone();

        storage.save(tasks);
        ArrayList<Task> loaded = storage.load();

        assertEquals(3, loaded.size());
        assertEquals("[T][ ] read", loaded.get(0).toString());
        assertEquals("[D][ ] submit (by: Feb 04 2026)", loaded.get(1).toString());
        assertEquals("[E][X] conference (from: Mon to: Tue)", loaded.get(2).toString());
    }

    @Test
    public void load_skipsInvalidLines() throws Exception {
        Path file = tempDir.resolve("zh.txt");
        List<String> lines = List.of(
                "T | 0 | ok",
                "BAD LINE",
                "D | 1 | missing date",
                "E | 0 | demo | 1pm | 2pm"
        );
        Files.write(file, lines, StandardCharsets.UTF_8);

        Storage storage = new Storage(file);
        ArrayList<Task> tasks = storage.load();

        assertEquals(2, tasks.size());
        assertEquals("[T][ ] ok", tasks.get(0).toString());
        assertEquals("[E][ ] demo (from: 1pm to: 2pm)", tasks.get(1).toString());
    }

    @Test
    public void save_createsParentDirectories() throws IOException {
        Path nested = tempDir.resolve("nested/zh.txt");
        Storage storage = new Storage(nested);

        List<Task> tasks = List.of(new ToDo("plan"));
        storage.save(tasks);

        assertTrue(Files.exists(nested));
    }
}


