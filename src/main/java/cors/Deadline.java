package cors;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(String name, boolean isCompleted, LocalDateTime by) {
        super(name, isCompleted);
        this.by = by; //Strings are immutable, so no need to copy it.
    }

    @Override
    public String toString() {
        return "[D] " + super.toString() + " (by: " + by.format(DateTimeFormatter.ofPattern("d MMM yyyy', 'ha")) + ")";
    }
}