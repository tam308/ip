package horsebot;

/**
 * Represents custom exceptions specific to HorseBot.
 */
public class HorseBotException extends Exception {
    /**
     * Constructor for a new HorseBotException.
     * @param message String containing cause of exception.
     */
    public HorseBotException(String message) {
        super(message);
    }
}
