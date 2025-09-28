package horsebot;

/**
 * Entry point of the HorseBot application.
 */
public class HorseBot {
    /**
     * Retrieve stored information.
     * Print the chatbot introductory message.
     * Continuously wait and handle user input.
     */
    public static void main(String[] args) {
        Storage.handleStoredList();
        Ui.intro();
        while (true) {
            Parser.handleInput();
        }
    }
}
