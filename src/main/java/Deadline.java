public class Deadline extends Task {
    public Deadline(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return "[D] " + super.toString();
    }
}