package cors;

public class Task {
    private String name;
    private boolean isCompleted;

    public Task(String name, boolean isCompleted) {
        this.name = name;
        this.isCompleted = isCompleted;
    }

    @Override
    public String toString() {
        String res = isCompleted ? "[X] " : "[ ] ";
        res += name;
        return res;
    }

    public void markAsComplete() {
        isCompleted = true;
    }

    public void markAsIncomplete() {
        isCompleted = false;
    }
}