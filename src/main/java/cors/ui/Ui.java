package cors.ui;

/**
 * Basic Ui class that manages the returned test
 */
public class Ui {
    private Response response;

    public Ui() {
        response = new Response();
    }


    public void showIndexError() {
        addToResponse("Index is not valid");
    }

    public void showTodoError() {
        addToResponse("Usage: todo <todo item>\nE.g. todo buy book");
    }

    /**
     * Shows the proper usage of deadline command
     */
    public void showDeadlineError() {
        addToResponse("Usage: deadline <deadline item> /by <deadline>\n"
                + "E.g. deadline finish writing essay /by 09-05-2026 1800");
    }

    /**
     * Shows the proper usage of event command
     */
    public void showEventError() {
        addToResponse("Usage: event <event item>"
                + "/from <start time> /to <end time>\n"
                + "E.g. event attend lecture /from 05-12-2025 1800 /to 31-12-2025 2100");
    }

    /**
     * Shows the proper format for dates
     */
    public void showDateTimeError() {
        addToResponse("Error getting date\n"
                + "Date must be formatted as such: dd-mm-yyyy 24h");
    }

    public void showFindError() {
        addToResponse("Error finding. Please enter a search term.");
    }

    /**
     * Shows proper commands
     */
    public void showUserCommandError() {
        addToResponse("Incorrect input!\n"
                + "To add an item to the list, type todo, deadline, or event.\n"
                + "To remove an item, type delete\n"
                + "To mark an item, type mark <index>.\n"
                + "To unmark an item, type unmark <index>.");
    }

    public void showGoodbyeMessage() {
        addToResponse("       Goodbye. Have a nice day!");
    }

    public void addToResponse(String s) {
        response.add(s);
    }

    public String getWelcomeMessage() {
        return "Welcome to Cors!\nHow can I help you?";
    }

    public String getResponse() {
        String s = response.get();
        response.clear();
        return s;
    }

    /**
     * Shows all tasks
     * @param tasks
     */
    public void showAllTasks(String[] tasks) {
        addToResponse("Here are the tasks in your list:");
        for (String task : tasks) {
            addToResponse(task);
        }
    }

    /**
     * Shows all matching tasks
     * @param tasks
     */
    public void showMatchingTasks(String[] tasks) {
        addToResponse("Here are the matching tasks in your list:");
        for (String task : tasks) {
            if (task != null) {
                addToResponse(task);
            }
        }
    }

    /**
     * Shows the task that is marked as done
     * @param task
     */
    public void showTaskAsDone(String task) {
        addToResponse("Nice! I've marked this task as done:");
        addToResponse(task);
    }

    /**
     * Shows the task that is marked as not done
     * @param task
     */
    public void showTaskAsNotDone(String task) {
        addToResponse("OK! I've marked this task as not done yet:");
        addToResponse(task);
    }

    /**
     * Shows the deleted tasks
     * @param task
     * @param length
     */
    public void showDeletedTask(String task, int length) {
        addToResponse("Nice, I've deleted this task:");
        addToResponse(task);
        addToResponse("You now have " + length + " tasks in your list.");
    }

    /**
     * Shows the added tasks
     * @param task
     * @param length
     */
    public void showAddedTask(String task, int length) {
        addToResponse("Got it, I've added this task:");
        addToResponse(task);
        addToResponse("Now you have " + length + " tasks in your list.");
    }
}
