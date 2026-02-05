package cors.command;

import cors.Storage;
import cors.task.TaskList;

import cors.ui.Ui;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


public class CommandTest {
    @Test
    public void runCommand_byeType_setsExitTrue() {
        Command command = new Command().setType(CommandType.BYE);
        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./test.csv"); // Ensure this doesn't overwrite real data

        assertFalse(command.isExit(), "Command should not be exit initially");

        command.runCommand(taskList, ui, storage);

        assertTrue(command.isExit(), "runCommand should set isExit to true for BYE type");
    }

    @Test
    public void runCommand_todoType_addsTaskToList() {
        Command command = new Command().setType(CommandType.TODO);
        command.setTask("Read a book");

        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./test.csv");

        int initialSize = taskList.getAllTasks().length;
        command.runCommand(taskList, ui, storage);

        assertEquals(initialSize + 1, taskList.getAllTasks().length, "TaskList size should increase by 1");
        assertEquals("[T] [ ] Read a book", taskList.get(0), "The added task description should match");
    }

    @Test
    public void runCommand_deleteInvalidIndex_doesNotCrash() {
        Command command = new Command().setType(CommandType.DELETE);
        command.setIndex(-5); // Invalid index

        TaskList taskList = new TaskList();
        Ui ui = new Ui();
        Storage storage = new Storage("./test.csv");

        // We expect no exception to be thrown even with an invalid index
        assertDoesNotThrow(() -> command.runCommand(taskList, ui, storage),
                "Command should handle negative indices gracefully without throwing exceptions.");
    }
}