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

    static Task[] list = new Task[100]; //initialise list of size 100
    static int listLength = 0;

    //record whatever user inputs in a list
    public static void record() {
        String userInput;
        Scanner in = new Scanner(System.in);
        userInput = in.nextLine();
        String[] userInputArray = userInput.split(" ");
        String command = userInputArray[0]; //read the first word in user input as possible command
        int markingIndex = 0; //to keep track of which index to mark/unmark after reading command
        switch (command) {
        case "mark": //mark a task as done
            markingIndex = Integer.parseInt(userInputArray[1]) - 1;
            list[markingIndex].setDone(true);
            System.out.println(line);
            System.out.println("Neigh! I've marked this task as done:");
            System.out.print("[" + list[markingIndex].getStatusIcon() + "] ");
            System.out.println(list[markingIndex].getDescription());
            System.out.println(line);
            break;
        case "unmark": //mark a task as undone
            markingIndex = Integer.parseInt(userInputArray[1]) - 1;
            list[markingIndex].setDone(false);
            System.out.println(line);
            System.out.println("Neigh... This task has been marked undone:");
            System.out.print("[" + list[markingIndex].getStatusIcon() + "] ");
            System.out.println(list[markingIndex].getDescription());
            System.out.println(line);
            break;

        case "bye": //terminate program
            System.out.println(line);
            System.out.println("Bye bye! It was Neigh-ce to meet you!");
            System.out.println(line);
            System.exit(0);
            break;
        case "list": //list out all items
            int counter = 0;
            System.out.println(line);
            while (list[counter] != null) {
                System.out.print((counter + 1) + ".");
                System.out.print("[" + list[counter].getStatusIcon() + "] ");
                System.out.println(list[counter].getDescription());
                counter++;
            }
            System.out.println(line);
            break;

        default: //default case add userInput to the list
            list[listLength] = new Task(userInput);
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
