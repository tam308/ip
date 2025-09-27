package horsebot;

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

    public static void printLine() {
        System.out.println(LINE);
    }

    public static void printIndent() {
        System.out.print(INDENT);
    }

    public static void intro() {    //print the chatbot's introductory message
        System.out.println(LOGO);
        System.out.println(INDENT + "Neigh! I'm a Horse! How can i assist you?");
        printLine();
    }

    static void exitProgram() {
        printLine();
        printIndent();
        System.out.println("Bye bye! It was Neigh-ce to meet you!");
        printLine();
        System.exit(0);
    }
}
