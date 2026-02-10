package zhbot;

/**
 * Represents a user-facing exception for invalid commands.
 */
public class BotException extends Exception {
    /**
     * Creates a new bot exception with the given message.
     *
     * @param message Error message to show the user.
     */
    public BotException(String message) {
        super(message);
    }
}


