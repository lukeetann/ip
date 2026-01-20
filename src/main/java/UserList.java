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
        System.out.print("[");
        if (this.myList[index].isCompleted()) {
            System.out.print("X");
        } else {
            System.out.print(" ");
        }
        System.out.print("] ");
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

    public void add(String s) {
        Task t = new Task(s);
        myList[length] = t;
        length++;
        System.out.println("Added: " + s);
    }
}