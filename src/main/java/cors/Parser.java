package cors;
import java.util.ArrayList;

public class Parser {
    ArrayList<String> res = new ArrayList<>();

    /**
     * Parses input from file
     * @param input string
     * @return ArrayList<String>. Arg[0] is the
     * type, arg[1] is the task, arg[2] is the
     * state of the task, arg[3+] are for deadline
     * and event.
     */
    public ArrayList<String> parseFromFile(String input) {
        if (input.isEmpty()) {
            res.add("empty");
        } else {
            switch (input.substring(0, 3)) {
            case ("[T]"):
                res.add("todo");
                res.add(input.substring(8)); // add the task
                if (input.charAt(5) == 'X') { // check if marked
                    res.add("marked");
                } else {
                    res.add("unmarked");
                }
                break;
            case ("[D]"):
                res.add("deadline");
                int indexBy = input.indexOf(" (by: ");
                res.add(input.substring(8, indexBy)); // add the task
                if (input.charAt(5) == 'X') {
                    res.add("marked");
                } else {
                    res.add("unmarked");
                }
                res.add(input.substring(indexBy + 6, input.length() - 1)); //add the /by
                break;
            case ("[E]"):
                res.add("event");
                int indexFrom = input.indexOf(" (from: ");
                int indexTo = input.indexOf(" to: ");
                res.add(input.substring(8, indexFrom)); //add the task
                if (input.charAt(5) == 'X') {
                    res.add("marked");
                } else {
                    res.add("unmarked");
                }
                res.add(input.substring(indexFrom + 8, indexTo)); //add the /from
                res.add(input.substring(indexTo + 5, input.length() - 1)); //add the /to
                break;
            default:
                res.add("fail");
            }
        }
        return res;
    }

    /**
     * Parses user input
     * @param input string
     * @return ArrayList<String>. Arg[0] is the
     * type, arg[1] is the task, arg[2] is the
     * state of the task, arg[3+] are for deadline
     * and event.
     */
    public ArrayList<String> parse(String input) {
        switch (input) {
        case ("bye"):
            // Fallthrough
        case ("list"):
            res.add(input);
            break;
        default:
            if (input.length() >= 6 && input.startsWith("mark")) {
                int index = Integer.parseInt(input.substring(5));
                res.add(input.substring(0, 4));
                res.add(Integer.toString(index));
            } else if (input.length() >= 8 && input.startsWith("unmark")) {
                int index = Integer.parseInt(input.substring(7));
                res.add(input.substring(0, 6));
                res.add(Integer.toString(index));
            } else if (input.startsWith("todo")) {
                res.add(input.substring(0, 4));
                if (input.length() >= 6) {
                    res.add(input.substring(5));
                }
                res.add("unmarked");
            } else if (input.startsWith("deadline")) {
                res.add(input.substring(0, 8));
                int by = input.indexOf(" /by ") + 5;
                if (input.length() >= 10 && by > 4) {
                    res.add(input.substring(9, by - 5));
                    res.add("unmarked");
                    res.add(input.substring(by));
                }
            } else if (input.startsWith("event")) {
                res.add(input.substring(0, 5));
                if (input.indexOf(" /from ") > 6 && input.contains(" /to ")) {
                    int from = input.indexOf(" /from ") + 7;
                    int to = input.indexOf(" /to ") + 5;
                    res.add(input.substring(6, from - 7));
                    res.add("unmarked");
                    res.add(input.substring(from, (to - 4)));
                    res.add(input.substring(to));
                }
            } else if (input.startsWith("delete")) {
                res.add(input.substring(0, 6));
                if (input.length() > 7) { // delete must be followed by some number
                    int index = Integer.parseInt(input.substring(7));
                    res.add(Integer.toString(index));
                }
            } else {
                res.add("fail");
            }
        }
        return res;
    }
}