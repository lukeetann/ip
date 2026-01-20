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
                    if (s.substring(0, 4).equals("mark")) {
                        int index = Integer.parseInt(s.substring(5));
                        if (this.userList.mark(index - 1)) {
                            System.out.println("Nice! I've marked this task as done:");
                            this.userList.print(index - 1);
                        }
                    } else if (s.substring(0, 6).equals("unmark")) {
                        int index = Integer.parseInt(s.substring(7));
                        if (this.userList.unmark(index - 1)) {
                            System.out.println("OK! I've marked this task as not done yet:");
                            this.userList.print(index - 1);
                        }
                    } else {
                        this.userList.add(s);
                    }
            }
            System.out.println("____________________________________");
        }
    }
}
