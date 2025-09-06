import java.util.Scanner;
import java.util.Arrays;

public class HorseBot {
    public static final int MAX_RECORDS = 100;
    static String INDENT = "    ";
    static String LINE = "______________________________________________________________________";
    static String LOGO = """
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

    static Task[] list = new Task[MAX_RECORDS]; //initialise array of Tasks
    static int listLength = 0;

    //handle user inputs
    public static void handleInput() {
        String userInput;
        Scanner in = new Scanner(System.in);
        userInput = in.nextLine();
        String[] userInputArray = userInput.split(" ");
        String command = userInputArray[0]; //read the first word in user input as command
        try {
            switch (command) {
            case "mark": //mark a task as done
                markItemAsDone(userInputArray);
                break;
            case "unmark": //mark a task as undone
                unmarkItem(userInputArray);
                break;
            case "bye": //terminate program
                exitProgram();
                break;
            case "list": //list out all items
                listOutItems();
                break;

            //handle task types
            case "todo":
                addItemToList(userInputArray, TaskType.TODO);
                break;
            case "deadline":
                addItemToList(userInputArray, TaskType.DEADLINE);
                break;
            case "event":
                addItemToList(userInputArray, TaskType.EVENT);
                break;

            default: //let the user know of an invalid input
                throw new HorseBotException("Invalid input! If there's nothing to do, I'm gonna go eat grass...");
            }
        } catch (HorseBotException e) {
            printLine();
            System.out.println(INDENT + e.getMessage());
            printLine();
        }
    }

    private static void addItemToList(String[] userInputArray, TaskType taskType) throws HorseBotException {
        String[] parsedUserInputArray = Arrays.copyOfRange(userInputArray, 1, userInputArray.length);//truncate the command from userInput
        String parsedUserInput = String.join(" ", parsedUserInputArray);
        if (parsedUserInput.isBlank()) {
            throw new HorseBotException("Neigh??? Description can't be empty!");
        }
        switch (taskType) {
        case TODO:
            addTodo(taskType, parsedUserInput);
            break;
        case DEADLINE:
            addDeadline(parsedUserInput);
            break;
        case EVENT:
            addEvent(parsedUserInput);
            break;
        }

        listLength++;
        printLine();
        System.out.println(INDENT + "Neigh! added: ");
        System.out.println(INDENT + list[listLength - 1].toString());
        System.out.println(INDENT + "Now you have " + listLength + " task(s) in the list!");
        printLine();
    }

    public static void intro() {    //print the chatbot's introductory message
        System.out.println(LOGO);
        System.out.println(INDENT + "Neigh! I'm a Horse! How can i assist you?");
        printLine();
    }

    private static void markItemAsDone(String[] userInputArray) {
        int markingIndex;
        markingIndex = Integer.parseInt(userInputArray[1]) - 1;
        list[markingIndex].setDone(true);
        printLine();
        printIndent();
        System.out.println("Neigh! I've marked this task as done:");
        System.out.print(INDENT + "[" + list[markingIndex].getStatusIcon() + "] ");
        System.out.println(list[markingIndex].getDescription());
        printLine();
    }

    private static void unmarkItem(String[] userInputArray) {
        int markingIndex;
        markingIndex = Integer.parseInt(userInputArray[1]) - 1;
        list[markingIndex].setDone(false);
        printLine();
        printIndent();
        System.out.println("Neigh... This task has been marked undone:");
        System.out.print(INDENT + "[" + list[markingIndex].getStatusIcon() + "] ");
        System.out.println(list[markingIndex].getDescription());
        printLine();
    }

    private static void exitProgram() {
        printLine();
        printIndent();
        System.out.println("Bye bye! It was Neigh-ce to meet you!");
        printLine();
        System.exit(0);
    }

    private static void listOutItems() {
        int counter = 0;
        printLine();
        System.out.println(INDENT + "Neigh! Here are your Tasks!");
        while (list[counter] != null) {
            printIndent();
            System.out.print((counter + 1) + ".");
            System.out.println(list[counter].toString());
            counter++;
        }
        printLine();
    }

    private static void addEvent(String parsedUserInput) throws HorseBotException {
        String[] splitUserInput = parsedUserInput.split("/from",2); //separate description from timings
        String description = splitUserInput[0].trim();

        checkEventCommandFrom(splitUserInput); //check for /from
        checkEventDescriptionEmpty(description);

        String[] splitDescription = splitUserInput[1].split("/to",2);//separate from and to timings
        String from = splitDescription[0].trim();

        checkEventCommandTo(splitDescription); //check for /to

        String to = splitDescription[1];
        checkEventFromToEmpty(from, to);
        list[listLength] = new Event(description, from, to);
    }

    private static void checkEventDescriptionEmpty(String description) throws HorseBotException {
        if (description.isEmpty()) {
            throw new HorseBotException("Neigh! Description is empty!");
        }
    }

    private static void checkEventCommandTo(String[] splitDescription) throws HorseBotException {
        if (splitDescription.length < 2) { // /to missing in input
            throw new HorseBotException("Neigh! End time command missing! set one by using /to [time]");
        }
    }

    private static void checkEventCommandFrom(String[] splitUserInput) throws HorseBotException {
        if (splitUserInput.length < 2) { // /from missing in input
            throw new HorseBotException("Neigh! Start time command missing! set one by using /from [time]");
        }
    }

    private static void checkEventFromToEmpty(String from, String to) throws HorseBotException {
        if (from.isEmpty()) {
            throw new HorseBotException("Neigh! Start time is empty!");
        }
        if (to.isEmpty()) {
            throw new HorseBotException("Neigh! End time is empty!");
        }
    }

    private static void addDeadline(String parsedUserInput) throws HorseBotException {
        String[] deadlineUserInput = parsedUserInput.split("/by",2);//separate description from due date
        checkForDeadlineCommandBy(deadlineUserInput); //check if /by was given
        String description = deadlineUserInput[0].trim();
        String by = deadlineUserInput[1].trim();
        checkDeadlineParametersEmpty(description, by); //check if Parameters are empty
        list[listLength] = new Deadline(description, by);
    }

    private static void checkForDeadlineCommandBy(String[] deadlineUserInput) throws HorseBotException {
        if (deadlineUserInput.length < 2) {
            throw new HorseBotException("Neigh! By command missing! set one by using /by [deadline]");
        }
    }

    private static void checkDeadlineParametersEmpty(String description, String by) throws HorseBotException {
        if (description.isEmpty()) {
            throw new HorseBotException("Neigh! Description is empty!");
        }
        if (by.isEmpty()) {
            throw new HorseBotException("Neigh! Deadline is empty!");
        }
    }

    private static void addTodo(TaskType taskType, String parsedUserInput) {
        list[listLength] = new Todo(parsedUserInput, taskType);
    }

    private static void printLine() {
        System.out.println(LINE);
    }

    private static void printIndent() {
        System.out.print(INDENT);
    }


    public static void main(String[] args) {
        intro();
        while (true) {
            handleInput();
        }
    }
}
