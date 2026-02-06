package cors.ui;
import java.util.Scanner;

import cors.Parser;
import cors.Storage;
import cors.command.Command;
import cors.exception.CorsException;
import cors.task.TaskList;

public class Cors {
    private TaskList taskList;
    private Storage storage;
    private Ui ui;

    public static void main(String[] args) {
        new Cors("./src/main/java/cors/cors.csv").run();
    }

    public Cors(String filePath) {
        storage = new Storage(filePath);
        ui = new Ui();
        try {
            taskList = new TaskList(storage.load());
        } catch (CorsException e) {
            ui.makeVisible();
            ui.showLoadingError();
            taskList = new TaskList();
        }
        ui.makeVisible();
    }

    private void run() {
        ui.showLogo();
        ui.greet();
        Scanner input = new Scanner(System.in);
        boolean isExit = false;
        while (!isExit) {
            String s = input.nextLine();
            System.out.println("____________________________________");
            Parser parser = new Parser();
            Command c = parser.parse(s, ui);
            c.runCommand(taskList, ui, storage);
            isExit = c.isExit();
            System.out.println("____________________________________");
        }
    }
}
