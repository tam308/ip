package horsebot;

import horsebot.tasks.Deadline;
import horsebot.tasks.Event;
import horsebot.tasks.Task;
import horsebot.tasks.TaskType;
import horsebot.tasks.Todo;

import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;


public class HorseBot {
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

    static ArrayList<Task> list = new ArrayList<>(); //initialise array of Tasks

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
            case "delete":
                deleteItemFromList(userInputArray);
                break;

            //handle task types
            case "todo":
                writeToFile(userInput);
                addItemToList(userInputArray, TaskType.TODO);
                break;
            case "deadline":
                writeToFile(userInput);
                addItemToList(userInputArray, TaskType.DEADLINE);
                break;
            case "event":
                writeToFile(userInput);
                addItemToList(userInputArray, TaskType.EVENT);
                break;

            default: //let the user know of an invalid input
                throw new HorseBotException("Invalid input! If there's nothing to do, I'm gonna go eat grass...");
            }
        } catch (HorseBotException | IOException e) {
            printLine();
            System.out.println(INDENT + e.getMessage());
            printLine();
        }
    }

    private static void deleteItemFromList(String[] userInputArray) throws HorseBotException, IOException {
        if (userInputArray.length < 2) {
            throw new HorseBotException("Neigh? Nothing to delete there???");
        }
        int markingIndex = Integer.parseInt(userInputArray[1]) - 1;
        if (markingIndex > list.size() - 1 || markingIndex < 0) {
            throw new HorseBotException("Neigh? Doesn't exist???");
        }
        System.out.println("Neigh! Task removed!:");
        System.out.println(list.get(markingIndex).toString());
        printLine();
        list.remove(markingIndex);

        deleteFromFile(markingIndex);

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

        printLine();
        System.out.println(INDENT + "Neigh! added: ");
        System.out.println(INDENT + list.get(list.size() - 1).toString());
        System.out.println(INDENT + "Now you have " + list.size() + " task(s) in the list!");
        printLine();
    }

    public static void intro() {    //print the chatbot's introductory message
        System.out.println(LOGO);
        System.out.println(INDENT + "Neigh! I'm a Horse! How can i assist you?");
        printLine();
    }

    private static void markItemAsDone(String[] userInputArray) throws HorseBotException,IOException {
        if (userInputArray.length < 2) {
            throw new HorseBotException("Neigh? Which task should i mark?");
        }
        int markingIndex;
        markingIndex = Integer.parseInt(userInputArray[1]) - 1;
        if (markingIndex > list.size() - 1) { //out of bounds marking
            throw new HorseBotException("Neigh? That doesnt exist!");
        }
        list.get(markingIndex).setDone(true);
        printLine();
        printIndent();
        System.out.println("Neigh! I've marked this task as done:");
        System.out.print(INDENT + "[" + list.get(markingIndex).getStatusIcon() + "] ");
        System.out.println(list.get(markingIndex).getDescription());
        printLine();

        editDoneInFile(markingIndex,true);
    }

    private static void unmarkItem(String[] userInputArray) throws HorseBotException, IOException {
        if (userInputArray.length < 2) {
            throw new HorseBotException("Neigh? Which task should i unmark?");
        }
        int markingIndex;
        markingIndex = Integer.parseInt(userInputArray[1]) - 1;
        if (markingIndex > list.size() - 1) {
            throw new HorseBotException("Neigh? That doesnt exist!");
        }
        list.get(markingIndex).setDone(false);
        printLine();
        printIndent();
        System.out.println("Neigh... This task has been marked undone:");
        System.out.print(INDENT + "[" + list.get(markingIndex).getStatusIcon() + "] ");
        System.out.println(list.get(markingIndex).getDescription());
        printLine();

        editDoneInFile(markingIndex,false);
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
        while (counter < list.size()) {
            printIndent();
            System.out.print((counter + 1) + ".");
            System.out.println(list.get(counter).toString());
            counter++;
        }
        printLine();
    }

    private static void addEvent(String parsedUserInput) throws HorseBotException {
        String[] splitUserInput = parsedUserInput.split("/from", 2); //separate description from timings
        String description = splitUserInput[0].trim();

        checkEventCommandFrom(splitUserInput); //check for /from
        checkEventDescriptionEmpty(description);

        String[] splitDescription = splitUserInput[1].split("/to", 2);//separate from and to timings
        String from = splitDescription[0].trim();

        checkEventCommandTo(splitDescription); //check for /to

        String to = splitDescription[1];
        checkEventFromToEmpty(from, to);
        list.add(new Event(description, from, to));
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
        String[] deadlineUserInput = parsedUserInput.split("/by", 2);//separate description from due date
        checkForDeadlineCommandBy(deadlineUserInput); //check if /by was given
        String description = deadlineUserInput[0].trim();
        String by = deadlineUserInput[1].trim();
        checkDeadlineParametersEmpty(description, by); //check if Parameters are empty
        list.add(new Deadline(description, by));
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
        list.add(new Todo(parsedUserInput, taskType));
    }

    private static void printLine() {
        System.out.println(LINE);
    }

    private static void printIndent() {
        System.out.print(INDENT);
    }

    private static void retrieveFileContents(String filePath) {
        int lineLength = 0; //for flushing the screen after calling commands
        File f = new File("data/tasks.txt");
        Scanner s = null; // create a Scanner using the File as the source
        try {
            s = new Scanner(f);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (s.hasNext()) {
            lineLength++;
            String userInput = s.nextLine();
            String[] separateDone = userInput.split(",");
            boolean markedDone = Boolean.parseBoolean(separateDone[0]);

            String[] userInputArray = separateDone[1].split(" ");
            System.out.println(markedDone + userInputArray[0] + userInputArray[1]);
            String command = userInputArray[0];
            try {
                switch (command) {
                case "todo":
                    addItemToList(userInputArray, TaskType.TODO);
                    break;
                case "deadline":
                    addItemToList(userInputArray, TaskType.DEADLINE);
                    break;
                case "event":
                    addItemToList(userInputArray, TaskType.EVENT);
                    break;
                }

            } catch (HorseBotException e) {
                printLine();
                System.out.println(INDENT + e.getMessage());
                printLine();
            }
            if (markedDone) {
                list.get(lineLength - 1).setDone(true);
            }
        }
        for (int i = 0; i < lineLength * 5; i++) { //flush the screen after calling commands
            System.out.println();
        }
    }

    public static void writeToFile(String userInput) throws IOException {
        FileWriter fw = new FileWriter("data/tasks.txt", true);
        fw.write("false," + userInput + System.lineSeparator());
        fw.close();
    }

    public static void editDoneInFile(int markingIndex, boolean done) throws IOException {
        File f = new File("data/tasks.txt");

        Scanner s = new Scanner(f); //temporarily store current data in new list
        ArrayList<String> lines = new ArrayList<>();
        while (s.hasNext()) {
            lines.add(s.nextLine());
        }
        s.close();
        //replace old line with updated Done status
        String oldLine = lines.get(markingIndex);
        int commaIndex = oldLine.indexOf(",");
        String newLine = (done) + "," + oldLine.substring(commaIndex + 1).trim();
        lines.set(markingIndex, newLine);

        FileWriter fw = new FileWriter(f);
        for (String line : lines) {
            fw.write(line + System.lineSeparator());
        }
        fw.close();
    }

    public static void deleteFromFile(int markingIndex) throws IOException {
        File f = new File("data/tasks.txt");

        Scanner s = new Scanner(f); //temporarily store current data in new list
        ArrayList<String> lines = new ArrayList<>();
        while (s.hasNext()) {
            lines.add(s.nextLine());
        }
        s.close();

        lines.remove(markingIndex);
        FileWriter fw = new FileWriter(f);
        for (String line : lines) {
            fw.write(line + System.lineSeparator());
        }
        fw.close();
    }

    public static void main(String[] args) {
        handleStoredList();
        intro();
        while (true) {
            handleInput();
        }
    }

    private static void handleStoredList() {
        File f = new File("data/tasks.txt");
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdir();
        }
        try {
            f.createNewFile();
            retrieveFileContents("data/tasks.txt");
        } catch (IOException e) {
            System.out.print(INDENT + "Neigh... File creation failed!");
        }

    }
}
