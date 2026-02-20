package cors.ui;

import cors.command.Command;
import cors.command.CommandType;
import cors.command.Parser;
import cors.exception.CorsException;
import cors.storage.Storage;
import cors.task.TaskList;

/**
 * Basic Cors class to run the cors chatbot
 */
public class Cors {
    private TaskList taskList;
    private Storage storage;
    private Ui ui;
    private CommandType commandType;

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

    /**
     * Takes in the commands from user input, parses
     * and runs the command, then returns the output
     * @param input
     * @return response
     */
    public String getResponse(String input) {
        try {
            Parser parser = new Parser();
            Command c = parser.parse(input, ui);
            c.runCommand(taskList, ui, storage);
            commandType = c.getType();
            return ui.getResponse();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public CommandType getCommandType() {
        return commandType;
    }
}
