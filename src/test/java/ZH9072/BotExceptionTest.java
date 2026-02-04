package ZH9072;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BotExceptionTest {
    @Test
    public void constructor_setsMessage() {
        BotException ex = new BotException("boom");

        assertEquals("boom", ex.getMessage());
    }
}
