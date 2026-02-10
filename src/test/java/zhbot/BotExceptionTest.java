package zhbot;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class BotExceptionTest {
    @Test
    public void constructor_setsMessage() {
        BotException ex = new BotException("boom");

        assertEquals("boom", ex.getMessage());
    }
}


