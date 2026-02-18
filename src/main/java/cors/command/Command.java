package cors.command;

import java.time.DateTimeException;
import java.time.LocalDateTime;

import javafx.application.Platform;

import cors.storage.Storage;
import cors.exception.WrongIndexException;
import cors.task.Deadline;
import cors.task.Event;
import cors.task.TaskList;
import cors.task.Todo;
import cors.ui.Ui;

public class Command {
    private CommandType type;
    private String task;
    private boolean isMarked;
    private LocalDateTime by;
    private LocalDateTime from;
    private LocalDateTime to;
    private int index;

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

    public void setTask(String task) {
        this.task = task;
    }

    public void mark() {
        this.isMarked = true;
    }

    public void unmark() {
        this.isMarked = false;
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
        default:
            System.out.println("You shouldn't be here...");
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
        }
    }

    private void handleTodo(TaskList taskList, Ui ui) {
        if (task == null) {
            ui.showTodoError();
            return;
        }
        ui.addToResponse(taskList.add(new Todo(task, isMarked)));
    }

    private void handleDeadline(TaskList taskList, Ui ui) {
        if (task == null || by == null) {
            ui.showDeadlineError();
            return;
        }
        try {
            ui.addToResponse(taskList.add(new Deadline(task, isMarked, by)));
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
            ui.addToResponse(taskList.add(new Event(task, isMarked, from, to)));
        } catch (DateTimeException e) {
            ui.showDateTimeError();
        }
    }

    private void handleDelete(TaskList taskList, Ui ui) {
        try {
            ui.addToResponse(taskList.delete(index - 1));
        } catch (WrongIndexException e) {
            ui.addToResponse("Usage: delete <number>\nE.g. delete 3");
        }
    }

}
