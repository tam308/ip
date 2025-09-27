package horsebot;


public class HorseBot {
    public static void main(String[] args) {
        Storage.handleStoredList();
        Ui.intro();
        while (true) {
            Parser.handleInput();
        }
    }
}
