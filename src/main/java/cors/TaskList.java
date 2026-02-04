package cors;

import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> myList;
    private int length;

    public TaskList() {
        this.myList = new ArrayList<>(100);
        this.length = 0;
    }

    public TaskList(String[] tasks) {
        TaskList taskList = new TaskList();
        Parser parser = new Parser();
        for (int i = 0; i < 100; i ++) {
            Command c = parser.parseFromFile(tasks[i]);
            taskList = c.runCommand(taskList, new Ui(), null);
        }
        this.myList = taskList.myList;
        this.length = taskList.length;
    }

    public String get(int index) {
        if (index < length) {
            return this.myList.get(index).toString();
        }
        return "";
    }

    public String[] getAllTasks() {
        String[] allTasks = new String[length];
        for (int i = 0; i < length; i++) {
            allTasks[i] = get(i);
        }
        return allTasks;
    }

    public boolean mark(int index) {
        if (index >= length || index < 0) {
            System.out.println("Sorry, there is no task at index " + (index + 1));
            return false;
        }
        myList.get(index).markAsComplete();
        return true;
    }

    public boolean unmark(int index) {
        if (index >= length || index < 0) {
            System.out.println("Sorry, there is no task at index " + (index + 1));
            return false;
        }
        myList.get(index).markAsIncomplete();
        return true;
    }

    /**
     * Adds a new Todo task
     */
    public String add(Task task) {
        myList.add(task);
        length++;
        return "Got it, I've added this task:\n" + task
                + "\nNow you have " + (length) + " tasks in your list.";
    }

    public void delete(int num) {
        if (num < 0 || num > myList.size()) {
            throw new IncorrectTextException();
        } else {
            System.out.println("Ok, I have removed this task:\n" + myList.get(num) +
                    "\nNow you have " + (myList.size() - 1) + " tasks left.");
            myList.remove(num);
            length--;
        }
    }
}