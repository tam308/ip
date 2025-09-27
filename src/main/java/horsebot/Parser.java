package horsebot;

import horsebot.tasks.TaskType;

import java.io.IOException;
import java.util.Scanner;

public class Parser {

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
                TaskList.markItemAsDone(userInputArray);
                break;
            case "unmark": //mark a task as undone
                TaskList.unmarkItem(userInputArray);
                break;
            case "bye": //terminate program
                Ui.exitProgram();
                break;
            case "list": //list out all items
                TaskList.listOutItems();
                break;
            case "delete":
                TaskList.deleteItemFromList(userInputArray);
                break;

            //handle task types
            case "todo":
                Storage.writeToFile(userInput);
                TaskList.addItemToList(userInputArray, TaskType.TODO);
                break;
            case "deadline":
                Storage.writeToFile(userInput);
                TaskList.addItemToList(userInputArray, TaskType.DEADLINE);
                break;
            case "event":
                Storage.writeToFile(userInput);
                TaskList.addItemToList(userInputArray, TaskType.EVENT);
                break;

            default: //let the user know of an invalid input
                throw new HorseBotException("Invalid input! If there's nothing to do, I'm gonna go eat grass...");
            }
        } catch (HorseBotException | IOException e) {
            Ui.printLine();
            System.out.println(Ui.INDENT + e.getMessage());
            Ui.printLine();
        }
    }
}
