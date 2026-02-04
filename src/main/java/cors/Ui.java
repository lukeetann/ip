package cors;

public class Ui {
    private String logo = " ██████   ████████  ████████    ████████\n"
            + "██       ██      ██ ██      ██ ██\n"
            + "██       ██      ██ ████████    ██████\n"
            + "██       ██      ██ ██   ██          ██\n"
            + " ██████   ████████  ██     ██  ████████\n";
    private boolean isVisible;

    public Ui() {
        isVisible = false;
    }

    public void makeVisible() {
        isVisible = true;
    }

    public void showLogo() {
        show(logo);
    }

    public void greet() {
        show("How can I help you?");
    }

    public void showLoadingError() {
        show("Loading error");
    }

    public void showTodoError() {
        show("Usage: todo <todo item>\nE.g. todo buy book");
    }

    public void showDeadlineError() {
        show("Usage: deadline <deadline item> /by <deadline>\n" +
                "E.g. deadline finish writing essay /by 09-05-2026 1800");
    }

    public void showEventError() {
        show("Usage: event <event item>" +
                "/from <start time> /to <end time>\n" +
                "E.g. event attend lecture /from 05-12-2025 1800 /to 31-12-2025 2100");
    }

    public void showDateTimeError() {
        System.out.println("Error getting date\n" +
                "Date must be formatted as such: dd-mm-yyyy 24h");
    }

    public void showUserCommandError() {
        show("Incorrect input!\n" +
                "To add an item to the list, type todo, deadline, or event.\n" +
                "To remove an item, type delete\n" +
                "To mark an item, type mark <index>.\n" +
                "To unmark an item, type unmark <index>.");
    }

    public void showFileCorruptedError() {
        show("File corrupted. Unable to read task");
    }

    public void showGoodbyeMessage() {
        System.out.println("       Goodbye. Have a nice day!");
    }

    public void show(String s) {
        if (isVisible) {
            System.out.println(s);
        }
    }

    public void showAllTasks(String[] tasks) {
        show("Here are the tasks in your list:");
        for (String task : tasks) {
            show(task);
        }
    }

    public void showTaskAsDone(String task) {
        show("Nice! I've marked this task as done:");
        show(task);
    }

    public void showTaskAsNotDone(String task) {
        show("OK! I've marked this task as not done yet:");
        show(task);
    }
}