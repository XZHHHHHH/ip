package ZH9072;

import java.time.LocalDate;

/**
 * Parses user input into command data.
 */
public class Parser {

    public enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE
    }

    public static class Command {
        public final CommandType type;
        public final String description;
        public final int index;
        public final LocalDate by;
        public final String from;
        public final String to;

        private Command(CommandType type, String description, int index, LocalDate by, String from, String to) {
            this.type = type;
            this.description = description;
            this.index = index;
            this.by = by;
            this.from = from;
            this.to = to;
        }

        public static Command bye() {
            return new Command(CommandType.BYE, null, -1, null, null, null);
        }

        public static Command list() {
            return new Command(CommandType.LIST, null, -1, null, null, null);
        }

        public static Command mark(int index) {
            return new Command(CommandType.MARK, null, index, null, null, null);
        }

        public static Command unmark(int index) {
            return new Command(CommandType.UNMARK, null, index, null, null, null);
        }

        public static Command todo(String description) {
            return new Command(CommandType.TODO, description, -1, null, null, null);
        }

        public static Command deadline(String description, LocalDate by) {
            return new Command(CommandType.DEADLINE, description, -1, by, null, null);
        }

        public static Command event(String description, String from, String to) {
            return new Command(CommandType.EVENT, description, -1, null, from, to);
        }

        public static Command delete(int index) {
            return new Command(CommandType.DELETE, null, index, null, null, null);
        }
    }

    public Command parse(String input) throws BotException {
        input = input.trim();

        if (input.equals("bye")) {
            return Command.bye();
        }
        if (input.equals("list")) {
            return Command.list();
        }
        if (input.startsWith("mark ")) {
            int idx = parseIndex(input.split(" ")[1]);
            return Command.mark(idx);
        }
        if (input.startsWith("unmark ")) {
            int idx = parseIndex(input.split(" ")[1]);
            return Command.unmark(idx);
        }
        if (input.equals("todo") || input.startsWith("todo")) {
            if (input.length() <= 4) {
                throw new BotException("Oops — please add some content after 'todo'.");
            }
            String desc = input.split("todo ", 2)[1].trim();
            if (desc.isEmpty()) {
                throw new BotException("Oops — please add some content after 'todo'.");
            }
            return Command.todo(desc);
        }
        if (input.startsWith("deadline")) {
            String rest = input.split("deadline", 2)[1];
            int byIndex = rest.indexOf(" /by ");
            if (byIndex == -1) {
                throw new BotException("Oops — deadline must have /by yyyy-MM-dd.");
            }

            String desc = rest.substring(0, byIndex).trim();
            String byStr = rest.substring(byIndex + 5).trim();

            if (desc.isEmpty()) {
                throw new BotException("Oops — please add a description for deadline.");
            }

            try {
                LocalDate by = LocalDate.parse(byStr);
                return Command.deadline(desc, by);
            } catch (Exception e) {
                throw new BotException("Oops — date must be yyyy-MM-dd (e.g., 2019-10-15).");
            }
        }
        if (input.startsWith("event")) {
            String rest = input.split("event", 2)[1];
            int fromIndex = rest.indexOf(" /from ");
            int toIndex = rest.indexOf(" /to ");
            if (fromIndex == -1 || toIndex == -1 || toIndex <= fromIndex) {
                throw new BotException("Oops — event must have /from ... /to ...");
            }

            String desc = rest.substring(0, fromIndex).trim();
            String from = rest.substring(fromIndex + 7, toIndex).trim();
            String to = rest.substring(toIndex + 5).trim();

            if (desc.isEmpty()) {
                throw new BotException("Oops — please add a description for event.");
            }
            if (from.isEmpty() || to.isEmpty()) {
                throw new BotException("Oops — event must have both /from and /to values.");
            }

            return Command.event(desc, from, to);
        }
        if (input.startsWith("delete ")) {
            int idx = parseIndex(input.split(" ")[1]);
            return Command.delete(idx);
        }

        throw new BotException("Sorry, I don't understand your command. Please try again.");
    }

    private int parseIndex(String oneBasedNumber) throws BotException {
        try {
            int oneBased = Integer.parseInt(oneBasedNumber);
            if (oneBased <= 0) {
                throw new BotException("Index must be a positive number.");
            }
            return oneBased - 1;
        } catch (NumberFormatException e) {
            throw new BotException("Index must be a number.");
        }
    }
}
