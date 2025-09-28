package horsebot;

import horsebot.tasks.Deadline;
import horsebot.tasks.Event;
import horsebot.tasks.Task;
import horsebot.tasks.TaskType;
import horsebot.tasks.Todo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Represents all tasks handled by the chatbot.
 */
public class TaskList {
    static ArrayList<Task> list = new ArrayList<>(); //initialise array of Tasks

    /**
     * Removes an entry from the task list.
     *
     * @param userInputArray user input stored as an array of strings.
     * @throws IOException       if error occurs while trying to write to storage.
     * @throws HorseBotException if index is not provided or invalid.
     */
    static void deleteItemFromList(String[] userInputArray) throws HorseBotException, IOException {
        if (userInputArray.length < 2) {
            throw new HorseBotException("Neigh? Nothing to delete there???");
        }
        int markingIndex = Integer.parseInt(userInputArray[1]) - 1;
        if (markingIndex > list.size() - 1 || markingIndex < 0) {
            throw new HorseBotException("Neigh? Doesn't exist???");
        }
        System.out.println("Neigh! Task removed!:");
        System.out.println(list.get(markingIndex).toString());
        Ui.printLine();
        list.remove(markingIndex);

        Storage.deleteFromFile(markingIndex);

    }

    /**
     * Appends an entry to the task list.
     *
     * @param userInputArray user input stored as an array of strings.
     * @param taskType       the type of task to add (TODO,DEADLINE,EVENT)
     * @throws HorseBotException if task does not contain a description.
     */
    static void addItemToList(String[] userInputArray, TaskType taskType) throws HorseBotException {
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

        Ui.printLine();
        System.out.println(Ui.INDENT + "Neigh! added: ");
        System.out.println(Ui.INDENT + list.get(list.size() - 1).toString());
        System.out.println(Ui.INDENT + "Now you have " + list.size() + " task(s) in the list!");
        Ui.printLine();
    }

    /**
     * Mark an existing item in the task list as done.
     *
     * @param userInputArray user input stored as an array of strings.
     * @throws IOException       if error occurs while trying to write to storage.
     * @throws HorseBotException if index is not provided or invalid.
     */
    static void markItemAsDone(String[] userInputArray) throws HorseBotException, IOException {
        if (userInputArray.length < 2) {
            throw new HorseBotException("Neigh? Which task should i mark?");
        }
        int markingIndex;
        markingIndex = Integer.parseInt(userInputArray[1]) - 1;
        if (markingIndex > list.size() - 1) { //out of bounds marking
            throw new HorseBotException("Neigh? That doesnt exist!");
        }
        list.get(markingIndex).setDone(true);
        Ui.printLine();
        Ui.printIndent();
        System.out.println("Neigh! I've marked this task as done:");
        System.out.print(Ui.INDENT + "[" + list.get(markingIndex).getStatusIcon() + "] ");
        System.out.println(list.get(markingIndex).getDescription());
        Ui.printLine();

        Storage.editDoneInFile(markingIndex, true);
    }

    /**
     * Mark an existing item in the task list as not done.
     *
     * @param userInputArray user input stored as an array of strings.
     * @throws IOException       if error occurs while trying to write to storage.
     * @throws HorseBotException if index is not provided or invalid.
     */
    static void unmarkItem(String[] userInputArray) throws HorseBotException, IOException {
        if (userInputArray.length < 2) {
            throw new HorseBotException("Neigh? Which task should i unmark?");
        }
        int markingIndex;
        markingIndex = Integer.parseInt(userInputArray[1]) - 1;
        if (markingIndex > list.size() - 1) {
            throw new HorseBotException("Neigh? That doesnt exist!");
        }
        list.get(markingIndex).setDone(false);
        Ui.printLine();
        Ui.printIndent();
        System.out.println("Neigh... This task has been marked undone:");
        System.out.print(Ui.INDENT + "[" + list.get(markingIndex).getStatusIcon() + "] ");
        System.out.println(list.get(markingIndex).getDescription());
        Ui.printLine();

        Storage.editDoneInFile(markingIndex, false);
    }

    /**
     * Print out all items in the current task list.
     *
     */
    static void listOutItems() {
        int counter = 0;
        Ui.printLine();
        System.out.println(Ui.INDENT + "Neigh! Here are your Tasks!");
        while (counter < list.size()) {
            Ui.printIndent();
            System.out.print((counter + 1) + ".");
            System.out.println(list.get(counter).toString());
            counter++;
        }
        Ui.printLine();
    }

    /**
     * Add an Event type task to the tasklist.
     *
     * @param parsedUserInput user input as a String with its command trimmed.
     * @throws HorseBotException if syntax is invalid or information is missing.
     */
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

    /**
     * Validate inputs of addEvent method.
     */
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

    /**
     * Add a Deadline type task to the tasklist.
     *
     * @param parsedUserInput user input as a String with its command trimmed.
     * @throws HorseBotException if syntax is invalid or information is missing.
     */
    private static void addDeadline(String parsedUserInput) throws HorseBotException {
        String[] deadlineUserInput = parsedUserInput.split("/by", 2);//separate description from due date
        checkForDeadlineCommandBy(deadlineUserInput); //check if /by was given
        String description = deadlineUserInput[0].trim();
        String by = deadlineUserInput[1].trim();
        checkDeadlineParametersEmpty(description, by); //check if Parameters are empty
        list.add(new Deadline(description, by));
    }

    /**
     * Validate inputs of addDeadline method.
     */
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

    /**
     * Add a Todo type task to the tasklist.
     *
     * @param taskType        the task type to be added.
     * @param parsedUserInput user input as a String with its command trimmed.
     */
    private static void addTodo(TaskType taskType, String parsedUserInput) {
        list.add(new Todo(parsedUserInput, taskType));
    }

    /**
     * search for items containing keyword in the current task list.
     *
     * @param userInputArray user input as an array.
     * @throws HorseBotException if no keyword is provided.
     */
    static void findInList(String[] userInputArray) throws HorseBotException {
        String parsedUserInput = Arrays.stream(userInputArray, 1, userInputArray.length).collect(Collectors.joining(" "));
        if (parsedUserInput.isBlank()) {
            throw new HorseBotException("Neigh??? What are you searching for?");
        }
        System.out.println(parsedUserInput);
        int counter = 0;
        Ui.printLine();
        System.out.println(Ui.INDENT + "Neigh! Here are the matching tasks in your list:");
        while (counter < list.size()) {
            checkItemInList(counter, parsedUserInput);
            counter++;
        }
        Ui.printLine();
    }

    /**
     * Prints a task if it's description contains the keyword.
     *
     * @param counter         index of the current task in the list.
     * @param parsedUserInput the search keyword.
     */
    private static void checkItemInList(int counter, String parsedUserInput) {
        if (list.get(counter).getDescription().contains(parsedUserInput)) {
            Ui.printIndent();
            System.out.print((counter + 1) + ".");
            System.out.println(list.get(counter).toString());
        }
    }
}
