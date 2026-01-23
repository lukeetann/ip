import java.util.Scanner;

public class Cors {
    private UserList userList;

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
        this.userList = new UserList();
    }

    private void bye() {
        System.out.println("       Goodbye. Have a nice day!");
        System.exit(0);
    }

    private void run() {
        Scanner input = new Scanner(System.in);
        while (true) {
            String s = input.nextLine();
            System.out.println("____________________________________");
            switch(s) {
                case ("bye"):
                    bye();
                    break;
                case ("list"):
                    System.out.println("Here are the tasks in your list:");
                    this.userList.print();
                    break;
                default:
                    if (s.length() >= 6 && s.startsWith("mark")) {
                        int index = Integer.parseInt(s.substring(5));
                        if (this.userList.mark(index - 1)) {
                            System.out.println("Nice! I've marked this task as done:");
                            this.userList.print(index - 1);
                        }
                    } else if (s.length() >= 8 && s.startsWith("unmark")) {
                        int index = Integer.parseInt(s.substring(7));
                        if (this.userList.unmark(index - 1)) {
                            System.out.println("OK! I've marked this task as not done yet:");
                            this.userList.print(index - 1);
                        }
                    } else if (s.length() >= 6 && s.startsWith("todo")) {
                        this.userList.add(s.substring(5));
                    } else if (s.length() >= 10 && s.startsWith("deadline")) {
                        int by = s.indexOf(" /by ") + 5;
                        this.userList.add(s.substring(9, by - 5), s.substring(by));
                    } else if (s.length() >= 7 && s.startsWith("event")) {
                        int from = s.indexOf(" /from ") + 7;
                        int to = s.indexOf(" /to ") + 5;
                        this.userList.add(s.substring(6, from - 7), s.substring(from, (to - 4)), s.substring(to));
                    } else {
                        this.userList.add(s);
                    }
            }
            System.out.println("____________________________________");
        }
    }
}
