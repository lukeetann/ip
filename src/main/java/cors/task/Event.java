package cors.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String name, boolean isCompleted, LocalDateTime from, LocalDateTime to) {
        super(name, isCompleted);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E] " + super.toString() + " (from: " + from.format(DateTimeFormatter.ofPattern("d MMM yyyy', 'ha")) + " to: "
                + to.format(DateTimeFormatter.ofPattern("d MMM yyyy', 'ha"))+ ")";
    }
}