import java.util.Scanner;

public class Cors {
    public static void main(String[] args) {
        String logo = " ██████   ████████  ████████    ████████\n"
                    + "██       ██      ██ ██      ██ ██\n"
                    + "██       ██      ██ ████████    ██████\n"
                    + "██       ██      ██ ██   ██          ██\n"
                    + " ██████   ████████  ██     ██  ████████\n";

        System.out.println("Hello! I'm your friendly chatbot, Cors!\n" + logo);

        echo();
    }

    private static void bye() {
        System.out.println("       Goodbye. Have a nice day!");
    }

    private static void echo() {
        Scanner input = new Scanner(System.in);
        while (true) {
            String s = input.nextLine();
            if (s.equals("bye")) {
                bye();
                break;
            }
            System.out.println("       " + s);
            System.out.println("----------------------");
        }
    }
}
