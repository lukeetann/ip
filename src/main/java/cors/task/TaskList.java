package cors.task;

import java.util.ArrayList;

import cors.command.Command;
import cors.command.Parser;
import cors.exception.WrongIndexException;
import cors.ui.Ui;

/**
 * Represents a list of tasks for the chatbot.
 * Handles operations such as adding, deleting, marking, and retrieving tasks.
 */
public class TaskList {
    private ArrayList<Task> myList;
    private int length;

    /**
     * Constructs an empty TaskList with an initial capacity of 100.
     */
    public TaskList() {
        this.myList = new ArrayList<>(100);
        this.length = 0;
    }

    /**
     * Constructs a TaskList by parsing an array of task strings, typically from storage.
     * @param tasks An array of strings representing serialized tasks from a file.
     */
    public TaskList(String[] tasks) {
        TaskList taskList = new TaskList();
        Parser parser = new Parser();
        for (int i = 0; i < 100; i++) {
            Command c = parser.parseFromFile(tasks[i]);
            taskList = c.runCommand(taskList, new Ui(), null);
        }
        this.myList = taskList.myList;
        this.length = taskList.length;
    }

    /**
     * Retrieves the string representation of a task at a specific index.
     * @param index The zero-based index of the task.
     * @return The string representation of the task, or an empty string if index is invalid.
     */
    public String get(int index) {
        if (index < length) {
            return this.myList.get(index).toString();
        }
        return "";
    }

    /**
     * Returns all tasks in the list as an array of strings.
     * @return A String array containing the string representation of every task.
     */
    public String[] getAllTasks() {
        String[] allTasks = new String[length];
        for (int i = 0; i < length; i++) {
            allTasks[i] = get(i);
        }
        return allTasks;
    }

    public String[] getMatchingTasks(String taskString) {
        String[] matchingTasks = new String[length];
        int i = 0;
        for (Task task : myList) {
            if (task.toString().contains(taskString)) {
                matchingTasks[i] = task.toString();
                i++;
            }
        }
        return matchingTasks;
    }

    /**
     * Marks a task as completed.
     * @param index The zero-based index of the task to mark.
     * @return true if the operation was successful, false if the index was out of bounds.
     */
    public boolean mark(int index) {
        if (index >= length || index < 0) {
            return false;
        }
        myList.get(index).markAsComplete();
        return true;
    }

    /**
     * Marks a task as incomplete.
     * @param index The zero-based index of the task to unmark.
     * @return true if the operation was successful, false if the index was out of bounds.
     */
    public boolean unmark(int index) {
        if (index >= length || index < 0) {
            return false;
        }
        myList.get(index).markAsIncomplete();
        return true;
    }

    /**
     * Adds a new task to the list and returns a confirmation message.
     * @param task The task object to be added.
     * @return A String message confirming the addition and current list size.
     */
    public String add(Task task) {
        myList.add(task);
        length++;
        return task.toString();
    }

    /**
     * Deletes a task from the list based on its index.
     * @param num The zero-based index of the task to be removed.
     * @throws WrongIndexException If the index provided is negative or out of bounds.
     */
    public String delete(int num) {
        if (num < 0 || num >= myList.size()) {
            throw new WrongIndexException();
        } else {
            String result = get(num);
            myList.remove(num);
            length--;
            return result;
        }
    }

    public int getLength() {
        return length;
    }
}
