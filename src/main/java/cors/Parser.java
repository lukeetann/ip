package cors;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Parser {

    /**
     * Parses input from file
     * @param input string
     * @return Command c
     */
    public Command parseFromFile(String input) {
        Command c = new Command();
        if (input == null) {
            c.setType(CommandType.EMPTY);
        } else {
            String pattern;
            switch (input.substring(0, 3)) {
            case ("[T]"):
                c.setType(CommandType.TODO);
                c.setTask(input.substring(8)); // add the task
                if (input.charAt(5) == 'X') { // check if marked
                    c.mark();
                } else {
                    c.unmark();
                }
                break;
            case ("[D]"):
                c.setType(CommandType.DEADLINE);
                int indexBy = input.indexOf(" (by: ");
                c.setTask(input.substring(8, indexBy)); // add the task
                if (input.charAt(5) == 'X') {
                    c.mark();
                } else {
                    c.unmark();
                }
                if (input.length() - indexBy - 7 > 15) {
                    pattern = "dd MMM yyyy', 'ha";
                } else {
                    pattern = "d MMM yyyy', 'ha";
                }
                c.setBy(LocalDateTime.parse(input.substring(indexBy + 6, input.length() - 1),
                        DateTimeFormatter.ofPattern(pattern)));
                break;
            case ("[E]"):
                c.setType(CommandType.EVENT);
                int indexFrom = input.indexOf(" (from: ");
                int indexTo = input.indexOf(" to: ");
                c.setTask(input.substring(8, indexFrom)); //add the task
                if (input.charAt(5) == 'X') {
                    c.mark();
                } else {
                    c.unmark();
                }
                if (indexTo - indexFrom - 8 > 15) {
                    pattern = "dd MMM yyyy', 'ha";
                } else {
                    pattern = "d MMM yyyy', 'ha";
                }
                c.setFrom(LocalDateTime.parse(input.substring(indexFrom + 8, indexTo),
                        DateTimeFormatter.ofPattern(pattern))); //add the /from
                if (input.length() - indexTo - 6 > 15) {
                    pattern = "dd MMM yyyy', 'ha";
                } else {
                    pattern = "d MMM yyyy', 'ha";
                }
                c.setTo(LocalDateTime.parse(input.substring(indexTo + 5, input.length() - 1),
                        DateTimeFormatter.ofPattern(pattern))); //add the /to
                break;
            default:
                throw new FileErrorException();
            }
        }
        return c;
    }

    /**
     * Parses user input
     * @param input string
     * @return Command c
     */
    public Command parse(String input, Ui ui) {
        Command c = new Command();
        switch (input) {
        case ("bye"):
            c.setType(CommandType.BYE);
            break;
        case ("list"):
            c.setType(CommandType.LIST);
            break;
        default:
            if (input.length() >= 6 && input.startsWith("mark")) {
                int index = Integer.parseInt(input.substring(5));
                c.mark();
                c.setIndex(index);
            } else if (input.length() >= 8 && input.startsWith("unmark")) {
                int index = Integer.parseInt(input.substring(7));
                c.unmark();
                c.setIndex(index);
            } else if (input.startsWith("todo")) {
                c.setType(CommandType.TODO);
                if (input.length() >= 6) {
                    c.setTask(input.substring(5));
                }
            } else if (input.startsWith("deadline")) {
                c.setType(CommandType.DEADLINE); // type is deadline
                int by = input.indexOf(" /by ") + 5;
                if (input.length() >= 10 && by > 4) {
                    c.setTask(input.substring(9, by - 5)); // add task
                    try {
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
                if (input.length() > 7) { // delete must be followed by some number
                    int index = Integer.parseInt(input.substring(7));
                    c.setIndex(index);
                }
            } else {
                c.setType(CommandType.FAIL);
            }
        }
        return c;
    }
}