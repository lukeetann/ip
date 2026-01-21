public class Event extends Task {
    public Event(String s) {
        super(s);
    }

    @Override
    public String toString() {
        return "[E] " + super.toString();
    }
}