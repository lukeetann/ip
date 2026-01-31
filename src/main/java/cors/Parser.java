package cors;
import java.util.ArrayList;

public class Parser {
    ArrayList<String> res = new ArrayList<>();
    public ArrayList<String> parse(String s) {
        switch (s) {
        case ("bye"):
            // Fallthrough
        case ("list"):
            res.add(s);
            break;
        default:
            if (s.length() >= 6 && s.startsWith("mark")) {
                int index = Integer.parseInt(s.substring(5));
                res.add(s.substring(0, 4));
                res.add(Integer.toString(index));
            } else if (s.length() >= 8 && s.startsWith("unmark")) {
                int index = Integer.parseInt(s.substring(7));
                res.add(s.substring(0, 6));
                res.add(Integer.toString(index));
            } else if (s.startsWith("todo")) {
                res.add(s.substring(0, 4));
                if (s.length() >= 6) {
                    res.add(s.substring(5));
                }
            } else if (s.startsWith("deadline")) {
                res.add(s.substring(0, 8));
                int by = s.indexOf(" /by ") + 5;
                if (s.length() >= 10 && by > -4) {
                    res.add(s.substring(9, by));
                    res.add(s.substring(by));
                }
            } else if (s.startsWith("event")) {
                res.add(s.substring(0, 5));
                if (s.indexOf(" /from ") > 6 && s.contains(" /to ")) {
                    int from = s.indexOf(" /from ") + 7;
                    int to = s.indexOf(" /to ") + 5;
                    res.add(s.substring(6, from - 7));
                    res.add(s.substring(from, (to - 4)));
                    res.add(s.substring(to));
                }
            } else if (s.startsWith("delete")) {
                res.add(s.substring(0, 6));
                if (s.length() > 7) { // delete must be followed by some number
                    int index = Integer.parseInt(s.substring(7));
                    res.add(Integer.toString(index));
                }
            } else {
                res.add("fail");
            }
        }
        return res;
    }
}