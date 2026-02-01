package cors;

public class Deadline extends Task {
    private String by;

    public Deadline(String name, boolean isCompleted, String by) {
        super(name, isCompleted);
        this.by = by; //Strings are immutable, so no need to copy it.
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + by + ")";
    }
}