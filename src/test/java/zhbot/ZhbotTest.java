package zhbot;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

public class ZhbotTest {
    @TempDir
    Path tempDir;

    @Test
    public void run_byeCommand_printsWelcomeAndBye() {
        ByteArrayInputStream in = new ByteArrayInputStream("bye\n".getBytes(StandardCharsets.UTF_8));
        java.io.InputStream originalIn = System.in;
        System.setIn(in);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(out, true, StandardCharsets.UTF_8));

        try {
            Path file = tempDir.resolve("duke.txt");
            Zhbot bot = new Zhbot(file.toString());
            bot.run();

            String output = out.toString(StandardCharsets.UTF_8);
            assertTrue(output.contains("Hello! I'm ZH9072Bot"));
            assertTrue(output.contains("Hope to see you again soon"));
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}


