package cors;

public class Todo extends Task {
    public Todo(String s, boolean isCompleted) {
        super(s, isCompleted);
    }

    @Override
    public String toString() {
        return "[T] " + super.toString();
    }
}