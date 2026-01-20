public class Task {
    private String name;
    private boolean completionStatus;

    public Task(String s) {
        this.name = s;
        this.completionStatus = false;
    }

    @Override
    public String toString() {
        return name;
    }

    public void markAsComplete() {
        completionStatus = true;
    }

    public void markAsIncomplete() {
        completionStatus = false;
    }

    public boolean isCompleted() {
        return completionStatus;
    }
}