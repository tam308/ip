package horsebot;

/**
 * Handles interactions with user interface.
 * Contains formatting lines and ASCII art for chatbot logo.
 */
public class Ui {
    public static String INDENT = "    ";
    public static String LINE = "______________________________________________________________________";
    public static String LOGO = """
            .--------------------------------------------------------------------.
            | __  __                                     ____            __      |
            |/\\ \\/\\ \\                                   /\\  _`\\         /\\ \\__   |
            |\\ \\ \\_\\ \\    ___   _ __   ____     __      \\ \\ \\L\\ \\    ___\\ \\ ,_\\  |
            | \\ \\  _  \\  / __`\\/\\`'__\\/',__\\  /'__`\\     \\ \\  _ <'  / __`\\ \\ \\/  |
            |  \\ \\ \\ \\ \\/\\ \\L\\ \\ \\ \\//\\__, `\\/\\  __/      \\ \\ \\L\\ \\/\\ \\L\\ \\ \\ \\_ |
            |   \\ \\_\\ \\_\\ \\____/\\ \\_\\\\/\\____/\\ \\____\\      \\ \\____/\\ \\____/\\ \\__\\|
            |    \\/_/\\/_/\\/___/  \\/_/ \\/___/  \\/____/       \\/___/  \\/___/  \\/__/|
            '--------------------------------------------------------------------'""";
    //ascii art generated with https://www.asciiart.eu/text-to-ascii-art

    /**
     * Prints a Line divider to the console output.
     */
    public static void printLine() {
        System.out.println(LINE);
    }

    /**
     * Prints an Indentation to the console output without a new line.
     */
    public static void printIndent() {
        System.out.print(INDENT);
    }

    /**
     * Prints the chatbot's introductory message.
     */
    public static void intro() {    //print the chatbot's introductory message
        System.out.println(LOGO);
        System.out.println(INDENT + "Neigh! I'm a Horse! How can i assist you?");
        printLine();
    }

    /**
     * Prints the chatbot's exit message and terminates the program.
     */
    static void exitProgram() {
        printLine();
        printIndent();
        System.out.println("Bye bye! It was Neigh-ce to meet you!");
        printLine();
        System.exit(0);
    }
}
