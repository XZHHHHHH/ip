package seedu.zh9072.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EventTest {
    @Test
    public void getters_returnFromAndTo() {
        Event event = new Event("team meeting", "2pm", "3pm");

        assertEquals("2pm", event.getFrom());
        assertEquals("3pm", event.getTo());
    }

    @Test
    public void toStorageString_usesEventFormat() {
        Event event = new Event("demo", "2026-02-10", "2026-02-11");
        event.markDone();

        assertEquals("E | 1 | demo | 2026-02-10 | 2026-02-11", event.toStorageString());
    }

    @Test
    public void toString_usesEventPrefixAndWindow() {
        Event event = new Event("conference", "Mon", "Tue");

        assertEquals("[E][ ] conference (from: Mon to: Tue)", event.toString());
    }
}


