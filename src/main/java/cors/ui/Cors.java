package cors.ui;

import cors.Parser;
import cors.Storage;
import cors.command.Command;
import cors.exception.CorsException;
import cors.task.TaskList;

/**
 * Basic Cors class to run the cors chatbot
 */
public class Cors {
    private TaskList taskList;
    private Storage storage;
    private Ui ui;

    /**
     * Initiates a new Cors instance with the given filepath
     * @param filePath String filepath from root folder
     */
    public Cors(String filePath) {
        storage = new Storage(filePath);
        ui = new Ui();
        try {
            taskList = new TaskList(storage.load());
        } catch (CorsException e) {
            taskList = new TaskList();
        }
    }

    public String getResponse(String input) {
        try {
            ui.addToResponse("Cor KAWWWW!\n");
            Parser parser = new Parser();
            Command c = parser.parse(input, ui);
            c.runCommand(taskList, ui, storage);
            return ui.getResponse();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
