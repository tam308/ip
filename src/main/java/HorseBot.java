import java.util.Scanner;
public class HorseBot {
    static String line = "______________________________________________________________________";
    //print the chatbot's introductory message
    public static void intro(){
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
        System.out.println(line);
        System.out.println(logo + "\nNeigh! I'm a Horse! How can i assist you?");
        System.out.println(line);
}

    //echo user input with indentation
    public static void echo(){
        String userInput = "";
        while (!userInput.equals("bye")) {
            Scanner in = new Scanner(System.in);
            userInput = in.nextLine();
            if (!userInput.equals("bye")) {
                System.out.println(line);
                System.out.println("    " + userInput);
                System.out.println(line);
            }
        }
    }

    public static void main(String[] args) {
        intro();
        echo();
        System.out.println(line);
        System.out.println("Bye bye! It was Neigh-ce to meet you!");
        System.out.println(line);
    }
}
