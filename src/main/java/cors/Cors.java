package cors;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;
import java.util.Scanner;

public class Cors {
    private TaskList taskList;
    private static final File USER_FILE = new File("./src/main/java/cors/cors.csv");

    public static void main(String[] args) {
        String logo = " ██████   ████████  ████████    ████████\n"
                    + "██       ██      ██ ██      ██ ██\n"
                    + "██       ██      ██ ████████    ██████\n"
                    + "██       ██      ██ ██   ██          ██\n"
                    + " ██████   ████████  ██     ██  ████████\n";

        System.out.println("Hello! I'm your friendly chatbot, Cors!\n" + logo);

        Cors cors = new Cors();
        cors.run();
        System.exit(0);
    }

    public Cors() {
        taskList = new TaskList();
        if (USER_FILE.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(USER_FILE))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Parser parse = new Parser();
                    ArrayList<String> arg = parse.parseFromFile(line);
                    runCommand(arg, false);
                }
            } catch (IOException e) {
                System.out.println("Unable to read user list");
            }
        }
    }

    private void bye() {
        saveToFile(USER_FILE);
        System.out.println("       Goodbye. Have a nice day!");
        System.exit(0);
    }

    private void saveToFile(File file) {
        try (PrintWriter out = new PrintWriter(file)) {
            for (int i = 0; i < 100; i++) {
                out.println(taskList.get(i));
            }
        } catch (IOException e) {
            System.out.println("Unable to save to file");
        }
    }

    private void run() {
        Scanner input = new Scanner(System.in);
        while (true) {
            String s = input.nextLine();
            System.out.println("____________________________________");
            Parser parse = new Parser();
            ArrayList<String> arg = parse.parse(s);
            runCommand(arg, true);
            System.out.println("____________________________________");
        }
    }

    /**
     * Runs keyword command from user or file
     * @param arg ArrayList<String> stores parsed args
     * @param fromUser boolean value
     */
    private void runCommand(ArrayList<String> arg, boolean fromUser) {
        switch (arg.get(0)) {
        case ("bye"):
            bye();
            break;
        case ("list"):
            System.out.println("Here are the tasks in your list:");
            taskList.print();
            break;
        case ("mark"):
            if (taskList.mark(Integer.valueOf(arg.get(1)) - 1)) {
                if (fromUser) {
                    System.out.println("Nice! I've marked this task as done:");
                }
                taskList.print(Integer.valueOf(arg.get(1)) - 1);
            }
            break;
        case ("unmark"):
            if (taskList.unmark(Integer.valueOf(arg.get(1)) - 1)) {
                if (fromUser) {
                    System.out.println("OK! I've marked this task as not done yet:");
                }
                taskList.print(Integer.valueOf(arg.get(1)) - 1);
            }
            break;
        case ("todo"):
            if (arg.size() < 3) {
                System.out.println("Usage: todo <todo item>\nE.g. todo buy book");
            } else {
                String result = taskList.add(new Todo(arg.get(1),
                        arg.get(2).equals("marked")));
                if (fromUser) {
                    System.out.println(result);
                }
            }
            break;
        case ("deadline"):
            if (arg.size() < 4) {
                System.out.println("Usage: deadline <deadline item> /by <deadline>\n" +
                        "E.g. deadline finish writing essay /by Tuesday 9pm");
            } else {
                String result = taskList.add(new Deadline(arg.get(1),
                        arg.get(2).equals("marked"), arg.get(3)));
                if (fromUser) {
                    System.out.println(result);
                }
            }
            break;
        case ("event"):
            if (arg.size() < 5) {
                System.out.println("Usage: event <event item>" +
                        "/from <start time> /to <end time>\n" +
                        "E.g. attend lecture /from Tuesday 2pm /to 9pm");
            } else {
                String result = taskList.add(new Event(arg.get(1), arg.get(2).equals("marked"),
                        arg.get(3), arg.get(4)));
                if (fromUser) {
                    System.out.println(result);
                }
            }
            break;
        case ("delete"):
            if (arg.size() < 2) {
                System.out.println("Usage: delete <number>\nE.g. delete 3");
            } else {
                taskList.delete(Integer.valueOf(arg.get(1)) - 1);
            }
            break;
        case ("empty"):
            break;
        case ("fail"):
            if (fromUser) {
                System.out.println("Incorrect input!\n" +
                        "To add an item to the list, type todo, deadline, or event.\n" +
                        "To remove an item, type delete\n" +
                        "To mark an item, type mark.\n" +
                        "To unmark an item, type unmark.");
            } else {
                System.out.println("File corrupted. Unable to read task");
            }
            break;
        default:
            System.out.println("You shouldn't be here...");
        }
    }
}
