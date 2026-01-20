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
                    this.userList.print();
                    break;
                default:
                    this.userList.add(s);
            }
            System.out.println("____________________________________");
        }
    }
}
