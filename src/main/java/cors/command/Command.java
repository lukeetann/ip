package cors.command;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import cors.exception.CorsException;
import cors.exception.WrongIndexException;
import cors.storage.Storage;
import cors.task.Deadline;
import cors.task.Event;
import cors.task.TaskList;
import cors.task.Todo;
import cors.ui.Ui;
import javafx.application.Platform;

/**
 * Command class to handle command logic and attributes
 */
public class Command {
    private CommandType type;
    private String task;
    private boolean isMarked;
    private LocalDateTime by;
    private LocalDateTime from;
    private LocalDateTime to;
    private int index;

    /**
     * Instantiates an empty command
     */
    public Command() {
        type = CommandType.EMPTY;
        index = -1;
    }

    public Command setType(CommandType type) {
        this.type = type;
        return this;
    }

    public CommandType getType() {
        return type;
    }

    public Command setTask(String task) {
        this.task = task;
        return this;
    }

    /**
     * Sets the isMarked value to true and
     * return the command
     * @return this
     */
    public Command mark() {
        this.isMarked = true;
        return this;
    }

    /**
     * Sets the isMarked value to false and
     * returns the command
     * @return this
     */
    public Command unmark() {
        this.isMarked = false;
        return this;
    }

    public void setBy(LocalDateTime by) {
        this.by = by;
    }

    public void setFrom(LocalDateTime from) {
        this.from = from;
    }

    public void setTo(LocalDateTime to) {
        this.to = to;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Performs shutdown operations for the application.
     * This method saves the current state of the provided {@code TaskList} to persistent
     * storage, displays a goodbye message to the user through the {@code Ui},
     * and then terminates the JavaFX application by calling {@code Platform.exit()}.
     * @param taskList
     * @param ui
     * @param storage
     */
    public void bye(TaskList taskList, Ui ui, Storage storage) {
        storage.saveToFile(taskList.getAllTasks());
        ui.showGoodbyeMessage();
        Platform.exit();
    }

    /**
     * Runs keyword command from user or file
     * @param taskList, ui, storage, isFromUser
     */
    public TaskList runCommand(TaskList taskList, Ui ui, Storage storage) {
        switch (type) {
        case BYE:
            bye(taskList, ui, storage);
            break;
        case LIST:
            ui.showAllTasks(taskList.getAllTasks());
            break;
        case MARK:
            handleMark(taskList, ui, true);
            break;
        case UNMARK:
            handleMark(taskList, ui, false);
            break;
        case TODO:
            handleTodo(taskList, ui);
            break;
        case DEADLINE:
            handleDeadline(taskList, ui);
            break;
        case EVENT:
            handleEvent(taskList, ui);
            break;
        case DELETE:
            handleDelete(taskList, ui);
            break;
        case FIND:
            ui.showMatchingTasks(taskList.getMatchingTasks(task));
            break;
        case FAIL:
            ui.showUserCommandError();
            break;
        case EMPTY:
            break;
        default:
            throw new CorsException("CommandType not valid");
        }
        return taskList;
    }

    private void handleMark(TaskList taskList, Ui ui, boolean mark) {
        int i = index - 1;
        boolean success = mark ? taskList.mark(i) : taskList.unmark(i);

        if (success) {
            if (mark) {
                ui.showTaskAsDone(taskList.get(i));
            } else {
                ui.showTaskAsNotDone(taskList.get(i));
            }
        } else {
            ui.showIndexError();
        }
    }

    private void handleTodo(TaskList taskList, Ui ui) {
        if (task == null) {
            ui.showTodoError();
            return;
        }
        ui.showAddedTask(taskList.add(new Todo(task, isMarked)), taskList.getLength());
    }

    private void handleDeadline(TaskList taskList, Ui ui) {
        if (task == null || by == null) {
            ui.showDeadlineError();
            return;
        }
        try {
            ui.showAddedTask(taskList.add(new Deadline(task, isMarked, by)), taskList.getLength());
        } catch (DateTimeException e) {
            ui.showDateTimeError();
        }
    }

    private void handleEvent(TaskList taskList, Ui ui) {
        if (task == null || from == null || to == null) {
            ui.showEventError();
            return;
        }
        try {
            ui.showAddedTask(taskList.add(new Event(task, isMarked, from, to)), taskList.getLength());
        } catch (DateTimeException e) {
            ui.showDateTimeError();
        }
    }

    private void handleDelete(TaskList taskList, Ui ui) {
        try {
            ui.showDeletedTask(taskList.delete(index - 1), taskList.getLength());
        } catch (WrongIndexException e) {
            ui.showIndexError();
        }
    }
}
