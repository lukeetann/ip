public class UserList {
    private Task[] myList;
    private int length;

    public UserList() {
        this.myList = new Task[100];
        this.length = 0;
    }

    public void print() {
        for (int i = 0; i < this.length; i++) {
            System.out.print((i + 1) + ".");
            print(i);
        }
    }

    public void print(int index) {
        System.out.println(this.myList[index].toString());
    }

    public boolean mark(int index) {
        if (index >= length || index < 0) {
            System.out.println("Sorry, there is no task at index " + (index + 1));
            return false;
        }
        myList[index].markAsComplete();
        return true;
    }

    public boolean unmark(int index) {
        if (index >= length || index < 0) {
            System.out.println("Sorry, there is no task at index " + (index + 1));
            return false;
        }
        myList[index].markAsIncomplete();
        return true;
    }

    /**
     * Adds a new Todo task
     */
    public void add(String name) {
        Task t = new Todo(name);
        myList[length] = t;
        length++;
        System.out.println("Got it, I've added this task:\n" + t
                + "\nNow you have " + (length) + " tasks in your list.");;
    }

    /**
     * Adds a new Deadline task
     */
    public void add(String name, String by) {
        Task t = new Deadline(name, by);
        myList[length] = t;
        length++;
        System.out.println("Got it, I've added this task:\n" + t
                + "\nNow you have " + (length) + " tasks in your list.");
    }

    /**
     * Adds a new Event task
     */
    public void add(String name, String from, String to) {
        Task t = new Event(name, from, to);
        myList[length] = t;
        length++;
        System.out.println("Got it, I've added this task:\n" + t
                + "\nNow you have " + (length) + " tasks in your list.");    }
}