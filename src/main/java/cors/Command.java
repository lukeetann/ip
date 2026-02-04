package cors;

import java.time.DateTimeException;
import java.time.LocalDateTime;

public class Command {
    private CommandType type;
    private String task;
    private boolean isMarked;
    private LocalDateTime by;
    private LocalDateTime from;
    private LocalDateTime to;
    private int index;
    private boolean isExit;

    public Command() {
        type = CommandType.EMPTY;
        index = -1;
    }

    public void setType(CommandType type) {
        this.type = type;
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

    public boolean isExit() {
        return isExit;
    }

    public void bye(TaskList taskList, Ui ui, Storage storage) {
        isExit = true;
        storage.saveToFile(taskList.getAllTasks());
        ui.showGoodbyeMessage();
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
            if (taskList.mark(index - 1)) {
                ui.showTaskAsDone(taskList.get(index - 1));
            }
            break;
        case UNMARK:
            if (taskList.unmark(index - 1)) {
                ui.showTaskAsNotDone(taskList.get(index - 1));
            }
            break;
        case TODO:
            if (task == null) {
                ui.showTodoError();
            } else {
                ui.show(taskList.add(new Todo(task, isMarked)));
            }
            break;
        case DEADLINE:
            if (task == null || by == null) {
                ui.showDeadlineError();
            } else {
                try {
                    ui.show(taskList.add(new Deadline(task, isMarked, by)));
                } catch (DateTimeException e) {
                    ui.showDateTimeError();
                }
            }
            break;
        case EVENT:
            if (task == null || from == null || to == null) {
                ui.showEventError();
            } else {
                try {
                    ui.show(taskList.add(new Event(task, isMarked, from, to)));
                } catch (DateTimeException e) {
                    ui.showDateTimeError();
                }
            }
            break;
        case DELETE:
            if (index < 0) {
                System.out.println("Usage: delete <number>\nE.g. delete 3");
            } else {
                taskList.delete(index - 1);
            }
            break;
        case EMPTY:
            break;
        case FAIL:
            ui.showUserCommandError();
            ui.showFileCorruptedError();
            break;
        default:
            System.out.println("You shouldn't be here...");
        }

        return taskList;
    }
}