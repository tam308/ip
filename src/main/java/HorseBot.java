import java.util.Scanner;

public class HorseBot {
    static String line = "______________________________________________________________________";

    //print the chatbot's introductory message
    public static void intro() {
        String logo = """
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
        System.out.println(logo + "\nNeigh! I'm a Horse! How can i assist you?");
        System.out.println(line);
    }

    static String[] list = new String[100]; //initialise list of size 100
    static int listLength = 0;

    //record whatever user inputs in a list
    public static void record() {
        String userInput;
        Scanner in = new Scanner(System.in);
        userInput = in.nextLine();
        switch (userInput) {
            case "bye": //terminate program
                System.out.println(line);
                System.out.println("Bye bye! It was Neigh-ce to meet you!");
                System.out.println(line);
                System.exit(0);
                break;
            case "list": //list out all items
                int tmp = 0;
                System.out.println(line);

                while (list[tmp] != null) {
                    System.out.print((tmp + 1) + ". ");
                    System.out.println(list[tmp]);
                    tmp++;
                }
                System.out.println(line);
                break;
            default: //default case add userInput to the list
                list[listLength] = userInput;
                listLength++;
                System.out.println(line);
                System.out.println("    " + "Neigh! added: " + userInput);
                System.out.println(line);
                break;

        }
    }


    public static void main(String[] args) {
        intro();
        while (true) {
            record();
        }
    }
}
