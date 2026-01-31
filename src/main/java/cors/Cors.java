package cors;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Scanner;

public class Cors {

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

    private void bye() {
        System.out.println("       Goodbye. Have a nice day!");
        System.exit(0);
    }

    private void run() {
        Scanner input = new Scanner(System.in);
        UserList userList = new UserList();
        while (true) {
            String s = input.nextLine();
            System.out.println("____________________________________");
            Parser parse = new Parser();
            ArrayList<String> arg = parse.parse(s);
            switch (arg.get(0)) {
            case ("bye"):
                bye();
                break;
            case ("list"):
                System.out.println("Here are the tasks in your list:");
                userList.print();
                break;
            case ("mark"):
                if (userList.mark(Integer.valueOf(arg.get(1)) - 1)) {
                    System.out.println("Nice! I've marked this task as done:");
                    userList.print(Integer.valueOf(arg.get(1)) - 1);
                }
                break;
            case ("unmark"):
                if (userList.mark(Integer.valueOf(arg.get(1)) - 1)) {
                    System.out.println("OK! I've marked this task as not done yet:");
                    userList.print(Integer.valueOf(arg.get(1)) - 1);
                }
                break;
            case ("todo"):
                if (arg.size() < 2) {
                    System.out.println("Usage: todo <todo item>\nE.g. todo buy book");
                } else {
                    userList.add(arg.get(1));
                }
                break;
            case ("deadline"):
                if (arg.size() < 3) {
                    System.out.println("Usage: deadline <deadline item> /by <deadline>\n" +
                            "E.g. deadline finish writing essay /by Tuesday 9pm");
                } else {
                    userList.add(arg.get(1), arg.get(2));
                }
                break;
            case ("event"):
                if (arg.size() < 4) {
                    System.out.println("Usage: event <event item>" +
                            "/from <start time> /to <end time>\n" +
                            "E.g. attend lecture /from Tuesday 2pm /to 9pm");
                } else {
                    userList.add(arg.get(1), arg.get(2), arg.get(3));
                }
                break;
            case ("delete"):
                if (arg.size() < 2) {
                    System.out.println("Usage: delete <number>\nE.g. delete 3");
                } else {
                    userList.delete(Integer.valueOf(arg.get(1)) - 1);
                }
                break;
            case ("fail"):
                System.out.println("Incorrect input!\n" +
                        "To add an item to the list, type todo, deadline, or event.\n" +
                        "To remove an item, type delete\n" +
                        "To mark an item, type mark.\n" +
                        "To unmark an item, type unmark.");
                break;
            }
            System.out.println("____________________________________");
        }
    }
}
