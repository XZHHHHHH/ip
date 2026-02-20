package zhbot;

import java.time.LocalDate;

/**
 * Parses user input into command data.
 */
public class Parser {

    /**
     * Supported command types.
     */
    public enum CommandType {
        BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND
    }

    /**
     * Represents a parsed command with associated arguments.
     */
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

        /**
         * Creates a {@code bye} command.
         *
         * @return Command representing a goodbye request.
         */
        public static Command bye() {
            return new Command(CommandType.BYE, null, -1, null, null, null);
        }

        /**
         * Creates a {@code list} command.
         *
         * @return Command representing a list request.
         */
        public static Command list() {
            return new Command(CommandType.LIST, null, -1, null, null, null);
        }

        /**
         * Creates a {@code mark} command.
         *
         * @param index Zero-based task index.
         * @return Command to mark a task as done.
         */
        public static Command mark(int index) {
            return new Command(CommandType.MARK, null, index, null, null, null);
        }

        /**
         * Creates an {@code unmark} command.
         *
         * @param index Zero-based task index.
         * @return Command to mark a task as not done.
         */
        public static Command unmark(int index) {
            return new Command(CommandType.UNMARK, null, index, null, null, null);
        }

        /**
         * Creates a {@code todo} command.
         *
         * @param description Task description.
         * @return Command to add a todo task.
         */
        public static Command todo(String description) {
            return new Command(CommandType.TODO, description, -1, null, null, null);
        }

        /**
         * Creates a {@code deadline} command.
         *
         * @param description Task description.
         * @param by          Due date.
         * @return Command to add a deadline task.
         */
        public static Command deadline(String description, LocalDate by) {
            return new Command(CommandType.DEADLINE, description, -1, by, null, null);
        }

        /**
         * Creates an {@code event} command.
         *
         * @param description Task description.
         * @param from        Start time or description.
         * @param to          End time or description.
         * @return Command to add an event task.
         */
        public static Command event(String description, String from, String to) {
            return new Command(CommandType.EVENT, description, -1, null, from, to);
        }

        /**
         * Creates a {@code delete} command.
         *
         * @param index Zero-based task index.
         * @return Command to delete a task.
         */
        public static Command delete(int index) {
            return new Command(CommandType.DELETE, null, index, null, null, null);
        }

        public static Command find(String keyword) {
            return new Command(CommandType.FIND, keyword, -1, null, null, null);
        }
    }

    /**
     * Parses a raw user input line into a command.
     *
     * @param input Raw user input.
     * @return Parsed command.
     * @throws BotException If the input is invalid or incomplete.
     */
    public Command parse(String input) throws BotException {
        assert input != null : "Parser input should not be null.";
        input = input.trim();

        if (input.equals("bye")) {
            return Command.bye();
        }
        if (input.equals("list")) {
            return Command.list();
        }
        if (input.startsWith("mark ")) {
            return Command.mark(parseIndexArgument(input, "mark"));
        }
        if (input.startsWith("unmark ")) {
            return Command.unmark(parseIndexArgument(input, "unmark"));
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
            return Command.delete(parseIndexArgument(input, "delete"));
        }
        if (input.equals("find") || input.startsWith("find")) {
            if (input.length() <= 4) {
                throw new BotException("Oops - please add a keyword after 'find'.");
            }
            String keyword = input.split("find ", 2)[1].trim();
            if (keyword.isEmpty()) {
                throw new BotException("Oops - please add a keyword after 'find'.");
            }
            return Command.find(keyword);
        }

        throw new BotException("Sorry, I don't understand your command. Please try again.");
    }

    /**
     * Parses a one-based index and converts it to zero-based form.
     *
     * @param oneBasedNumber User-provided one-based index string.
     * @return Zero-based index.
     * @throws BotException If the value is not a positive number.
     */
    private int parseIndex(String oneBasedNumber) throws BotException {
        assert oneBasedNumber != null : "Index token should not be null.";
        try {
            int oneBased = Integer.parseInt(oneBasedNumber);
            if (oneBased <= 0) {
                throw new BotException("Index must be a positive number.");
            }
            int zeroBased = oneBased - 1;
            assert zeroBased >= 0 : "Parsed index should be zero-based and non-negative.";
            return zeroBased;
        } catch (NumberFormatException e) {
            throw new BotException("Index must be a number.");
        }
    }

    private int parseIndexArgument(String input, String commandWord) throws BotException {
        String argument = input.substring(commandWord.length()).trim();
        if (argument.isEmpty()) {
            throw new BotException("Index must be a number.");
        }
        return parseIndex(argument.split("\\s+")[0]);
    }
}


