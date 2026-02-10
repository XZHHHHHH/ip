package zhbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import zhbot.tasks.Task;
import zhbot.tasks.TaskList;

public class UiTest {
    @Test
    public void readCommand_readsFromInput() {
        ByteArrayInputStream in = new ByteArrayInputStream("list\n".getBytes(StandardCharsets.UTF_8));
        java.io.InputStream originalIn = System.in;
        System.setIn(in);

        try {
            Ui ui = new Ui();
            assertEquals("list", ui.readCommand());
        } finally {
            System.setIn(originalIn);
        }
    }

    @Test
    public void showMethods_printExpectedMessages() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));

        try {
            Ui ui = new Ui();
            TaskList list = new TaskList();
            Task task = new Task("read");
            list.add(task);

            ui.showWelcome();
            ui.showAdded(list);
            ui.showList(list);
            ui.showMarked(task, true);
            ui.showMarked(task, false);
            ui.showDeleted(task, list);
            ui.showError("error!");
            ui.showLoadingError();
            ui.showSaveError();
            ui.showBye();

            String output = out.toString(StandardCharsets.UTF_8);
            assertTrue(output.contains("Hello! I'm ZH9072Bot"));
            assertTrue(output.contains("I've added this task"));
            assertTrue(output.contains("Here are the tasks in your list"));
            assertTrue(output.contains("I've marked this task as done"));
            assertTrue(output.contains("not done yet"));
            assertTrue(output.contains("I've removed this task"));
            assertTrue(output.contains("error!"));
            assertTrue(output.contains("failed to load saved tasks"));
            assertTrue(output.contains("could not save your tasks"));
            assertTrue(output.contains("Hope to see you again soon"));
        } finally {
            System.setOut(originalOut);
        }
    }
}


