public class Task {
    private String name;
    private boolean completionStatus;

    public Task(String s) {
        this.name = s;
        this.completionStatus = false;
    }

    @Override
    public String toString() {
        String res = completionStatus ? "[X] " : "[ ] ";
        res += name;
        return res;
    }

    public void markAsComplete() {
        completionStatus = true;
    }

    public void markAsIncomplete() {
        completionStatus = false;
    }
}