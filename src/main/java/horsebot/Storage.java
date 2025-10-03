package horsebot;

import horsebot.tasks.TaskType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Handles saving and loading of list data to a .txt file
 */
public class Storage {
    /**
     * Retrieves previously stored list data from .txt file.
     */
    private static void retrieveFileContents() {
        int lineLength = 0;
        File f = new File("data/tasks.txt");
        Scanner s;
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
                    TaskList.addItemToList(userInputArray, TaskType.TODO);
                    break;
                case "deadline":
                    TaskList.addItemToList(userInputArray, TaskType.DEADLINE);
                    break;
                case "event":
                    TaskList.addItemToList(userInputArray, TaskType.EVENT);
                    break;
                }

            } catch (HorseBotException e) {
                Ui.printLine();
                System.out.println(Ui.INDENT + e.getMessage());
                Ui.printLine();
            }
            if (markedDone) {
                TaskList.list.get(lineLength - 1).setDone(true);
            }
        }
        for (int i = 0; i < 100; i++) { //flush the screen after calling commands
            System.out.println();
        }
    }

    /**
     * Search for existing .txt file under folder data/tasks.txt.
     * Creates tasks.txt and its parent folder if it does not exist.
     */
    static void handleStoredList() {
        File f = new File("data/tasks.txt");
        if (!f.getParentFile().exists()) {
            f.getParentFile().mkdir();
        }
        try {
            f.createNewFile();
            retrieveFileContents();
        } catch (IOException e) {
            System.out.print(Ui.INDENT + "Neigh... File creation failed!");
        }

    }

    /**
     * Deletes an item from the list stored in tasks.txt.
     *
     * @param markingIndex Index of the item to be deleted.
     * @throws IOException if error occurs while trying to write to storage.
     */
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

    /**
     * Appends new list entry contents to tasks.txt.
     *
     * @param userInput String containing information to be written to tasks.txt.
     * @throws IOException if error occurs while trying to write to storage.
     */
    public static void writeToFile(String userInput) throws IOException {
        FileWriter fw = new FileWriter("data/tasks.txt", true);
        fw.write("false," + userInput + System.lineSeparator());
        fw.close();
    }

    /**
     * Mark or unmark an item as done in list as done in tasks.txt.
     *
     * @param markingIndex Index of the item to be marked.
     * @param done         determines if item is to be marked as done or undone.
     * @throws IOException if error occurs while trying to write to storage.
     */
    public static void editDoneInFile(int markingIndex, boolean done) throws IOException {
        File f = new File("data/tasks.txt");

        Scanner s = new Scanner(f); //temporarily store current data in new list
        ArrayList<String> lines = new ArrayList<>();
        while (s.hasNext()) {
            lines.add(s.nextLine());
        }
        s.close();

        updateLineToBeMarked(markingIndex, done, lines);         //replace old line with updated Done status

        FileWriter fw = new FileWriter(f);
        for (String line : lines) {
            fw.write(line + System.lineSeparator());
        }
        fw.close();
    }

    /**
     * Handles formatting of item to be marked done or undone.
     *
     * @param markingIndex Index of the item to be edited.
     * @param done         determines if item is to be marked as done or undone.
     * @param lines        line to be edited as read from tasks.txt.
     */
    private static void updateLineToBeMarked(int markingIndex, boolean done, ArrayList<String> lines) {
        String oldLine = lines.get(markingIndex);
        int commaIndex = oldLine.indexOf(",");
        String newLine = done + "," + oldLine.substring(commaIndex + 1).trim();
        lines.set(markingIndex, newLine);
    }
}
