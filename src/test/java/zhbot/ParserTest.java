package zhbot;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class ParserTest {
    @Test
    public void parse_byeCommand_returnsByeType() throws Exception {
        Parser parser = new Parser();

        Parser.Command cmd = parser.parse("bye");
        assertEquals(Parser.CommandType.BYE, cmd.type);
    }

    @Test
    public void parse_listCommand_returnsListType() throws Exception {
        Parser parser = new Parser();

        Parser.Command cmd = parser.parse("list");

        assertEquals(Parser.CommandType.LIST, cmd.type);
    }

    @Test
    public void parse_markCommand_convertsToZeroBasedIndex() throws Exception {
        Parser parser = new Parser();

        Parser.Command cmd = parser.parse("mark 2");

        assertEquals(Parser.CommandType.MARK, cmd.type);
        assertEquals(1, cmd.index);
    }

    @Test
    public void parse_unmarkCommand_convertsToZeroBasedIndex() throws Exception {
        Parser parser = new Parser();

        Parser.Command cmd = parser.parse("unmark 1");

        assertEquals(Parser.CommandType.UNMARK, cmd.type);
        assertEquals(0, cmd.index);
    }

    @Test
    public void parse_deleteCommand_convertsToZeroBasedIndex() throws Exception {
        Parser parser = new Parser();

        Parser.Command cmd = parser.parse("delete 3");

        assertEquals(Parser.CommandType.DELETE, cmd.type);
        assertEquals(2, cmd.index);
    }

    @Test
    public void parse_findCommand_readsKeyword() throws Exception {
        Parser parser = new Parser();

        Parser.Command cmd = parser.parse("find book");

        assertEquals(Parser.CommandType.FIND, cmd.type);
        assertEquals("book", cmd.description);
    }

    @Test
    public void parse_todoCommand_readsDescription() throws Exception {
        Parser parser = new Parser();

        Parser.Command cmd = parser.parse("todo read book");

        assertEquals(Parser.CommandType.TODO, cmd.type);
        assertEquals("read book", cmd.description);
    }

    @Test
    public void parse_deadlineCommand_parsesDate() throws Exception {
        Parser parser = new Parser();

        Parser.Command cmd = parser.parse("deadline submit report /by 2026-02-04");

        assertEquals(Parser.CommandType.DEADLINE, cmd.type);
        assertEquals("submit report", cmd.description);
        assertEquals(LocalDate.of(2026, 2, 4), cmd.by);
    }

    @Test
    public void parse_eventCommand_parsesFromTo() throws Exception {
        Parser parser = new Parser();

        Parser.Command cmd = parser.parse("event party /from 6pm /to 8pm");

        assertEquals(Parser.CommandType.EVENT, cmd.type);
        assertEquals("party", cmd.description);
        assertEquals("6pm", cmd.from);
        assertEquals("8pm", cmd.to);
    }

    @Test
    public void parse_invalidDeadlineDate_throwsBotException() {
        Parser parser = new Parser();

        BotException ex = assertThrows(BotException.class, () -> parser.parse("deadline homework /by 2026-02-31"));
        assertTrue(ex.getMessage().toLowerCase().contains("date"));
    }

    @Test
    public void parse_invalidIndex_throwsBotException() {
        Parser parser = new Parser();

        BotException ex = assertThrows(BotException.class, () -> parser.parse("mark 0"));
        assertTrue(ex.getMessage().toLowerCase().contains("index"));
    }

    @Test
    public void parse_findWithoutKeyword_throwsBotException() {
        Parser parser = new Parser();

        BotException ex = assertThrows(BotException.class, () -> parser.parse("find"));
        assertTrue(ex.getMessage().toLowerCase().contains("keyword"));
    }

    @Test
    public void parse_findWithSpacesOnly_throwsBotException() {
        Parser parser = new Parser();

        BotException ex = assertThrows(BotException.class, () -> parser.parse("find   "));
        assertTrue(ex.getMessage().toLowerCase().contains("keyword"));
    }

    @Test
    public void parse_unknownCommand_throwsBotException() {
        Parser parser = new Parser();

        BotException ex = assertThrows(BotException.class, () -> parser.parse("nonsense"));
        assertTrue(ex.getMessage().toLowerCase().contains("don't understand"));
    }
}
