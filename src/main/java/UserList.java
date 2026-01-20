import java.util.ArrayList;

public class UserList {
    private ArrayList<String> myList;
    private int length;

    public UserList() {
        this.myList = new ArrayList<>(100);
        this.length = 0;
    }

    public void print() {
        for (int i = 0; i < this.length; i++) {
            System.out.println(this.myList.get(i));
        }
    }

    public void add(String s) {
        myList.add(length, s);
        length++;
        System.out.println("Added: " + s);
    }
}