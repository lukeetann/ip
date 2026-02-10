package cors;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

import cors.command.Command;
import cors.command.CommandType;
import cors.exception.FileErrorException;
import cors.ui.Ui;

/**
 * Deals with making sense of the user command or data loaded from a file.
 */
public class Parser {

    /**
     * Parses a single line of text from the save file into a Command object.
     * Expected format: "[T] [X] task description" or "[D] [ ] task (by: date)".
     * @param input The raw string line from the storage file.
     * @return A Command object configured with the task details.
     * @throws FileErrorException If the file format does not match expected patterns.
     */
    public Command parseFromFile(String input) {
        Command c = new Command();
        if (input == null) {
            c.setType(CommandType.EMPTY);
        } else {
            String pattern;
            // Check the task type indicator at the start of the string
            switch (input.substring(0, 3)) {
            case ("[T]"):
                c.setType(CommandType.TODO);
                c.setTask(input.substring(8));
                // Index 5 corresponds to the status icon: [T] [X] or [T] [ ]
                if (input.charAt(5) == 'X') {
                    c.mark();
                } else {
                    c.unmark();
                }
                break;
            case ("[D]"):
                c.setType(CommandType.DEADLINE);
                int indexBy = input.indexOf(" (by: ");
                c.setTask(input.substring(8, indexBy));
                if (input.charAt(5) == 'X') {
                    c.mark();
                } else {
                    c.unmark();
                }
                // Determine date pattern based on string length (handles d vs dd for day)
                if (input.length() - indexBy - 7 > 15) {
                    pattern = "dd MMM yyyy', 'ha";
                } else {
                    pattern = "d MMM yyyy', 'ha";
                }
                DateTimeFormatter format = new DateTimeFormatterBuilder()
                        .parseCaseInsensitive().appendPattern(pattern).toFormatter();
                c.setBy(LocalDateTime.parse(input.substring(indexBy + 6, input.length() - 1),
                        format));
                break;
            case ("[E]"):
                try {
                    c.setType(CommandType.EVENT);
                    int indexFrom = input.indexOf(" (from: ");
                    int indexTo = input.indexOf(" to: ");
                    c.setTask(input.substring(8, indexFrom));
                    if (input.charAt(5) == 'X') {
                        c.mark();
                    } else {
                        c.unmark();
                    }
                    // Handle dynamic date padding for 'from' field
                    if (indexTo - indexFrom - 8 > 15) {
                        pattern = "dd MMM yyyy', 'ha";
                    } else {
                        pattern = "d MMM yyyy', 'ha";
                    }
                    c.setFrom(LocalDateTime.parse(input.substring(indexFrom + 8, indexTo),
                            DateTimeFormatter.ofPattern(pattern)));
                    // Handle dynamic date padding for 'to' field
                    if (input.length() - indexTo - 6 > 15) {
                        pattern = "dd MMM yyyy', 'ha";
                    } else {
                        pattern = "d MMM yyyy', 'ha";
                    }
                    c.setTo(LocalDateTime.parse(input.substring(indexTo + 5, input.length() - 1),
                            DateTimeFormatter.ofPattern(pattern)));
                    break;
                } catch (DateTimeException e) {
                    throw new FileErrorException();
                }
            default:
                throw new FileErrorException(); // Possibly misleading since it takes in a String[]
            }
        }
        return c;
    }

    /**
     * Parses the raw user input from the CLI into a Command object.
     * @param input The full command string entered by the user.
     * @param ui The UI object used to display immediate feedback/errors.
     * @return A Command object representing the user's intent.
     */
    public Command parse(String input, Ui ui) {
        Command c = new Command();

        // Handle single-word commands first
        switch (input) {
        case ("bye"):
            c.setType(CommandType.BYE);
            break;
        case ("list"):
            c.setType(CommandType.LIST);
            break;
        default:
            // Handle multi-word commands (mark, unmark, todo, deadline, event, delete)
            if (input.length() >= 6 && input.startsWith("mark")) {
                int index = Integer.parseInt(input.substring(5));
                c.setType(CommandType.MARK);
                c.setIndex(index);
            } else if (input.length() >= 8 && input.startsWith("unmark")) {
                int index = Integer.parseInt(input.substring(7));
                c.setType(CommandType.UNMARK);
                c.setIndex(index);
            } else if (input.startsWith("todo")) {
                c.setType(CommandType.TODO);
                if (input.length() >= 6) {
                    c.setTask(input.substring(5));
                }
            } else if (input.startsWith("deadline")) {
                c.setType(CommandType.DEADLINE);
                int by = input.indexOf(" /by ") + 5;
                if (input.length() >= 10 && by > 4) {
                    c.setTask(input.substring(9, by - 5));
                    try {
                        // Expected format: dd-MM-yyyy HHmm
                        c.setBy(LocalDateTime.parse(input.substring(by),
                                DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm")));
                    } catch (DateTimeException e) {
                        c.setType(CommandType.EMPTY);
                        ui.showDateTimeError();
                    }
                }
            } else if (input.startsWith("event")) {
                c.setType(CommandType.EVENT);
                if (input.indexOf(" /from ") > 6 && input.contains(" /to ")) {
                    int from = input.indexOf(" /from ") + 7;
                    int to = input.indexOf(" /to ") + 5;
                    c.setTask(input.substring(6, from - 7));
                    try {
                        c.setFrom(LocalDateTime.parse(input.substring(from, (to - 5)),
                                DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm")));
                        c.setTo(LocalDateTime.parse(input.substring(to),
                                DateTimeFormatter.ofPattern("dd-MM-yyyy HHmm")));
                    } catch (DateTimeException e) {
                        c.setType(CommandType.EMPTY);
                        ui.showDateTimeError();
                    }
                }
            } else if (input.startsWith("delete")) {
                c.setType(CommandType.DELETE);
                if (input.length() > 7) {
                    int index = Integer.parseInt(input.substring(7));
                    c.setIndex(index);
                }
            } else if (input.startsWith("find")) {
                c.setType(CommandType.FIND);
                if (input.length() > 5) {
                    c.setTask(input.substring(5));
                }
            } else {
                c.setType(CommandType.FAIL);
            }
        }
        return c;
    }
}
