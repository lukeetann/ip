package cors;

import java.util.ArrayList;

public class Response {
    private ArrayList<String> response;

    public Response() {
        response = new ArrayList<>();
    }
    /**
     * Adds a string to the response
     * @param s
     */
    public void add(String s) {
        response.add(s);
    }

    /**
     * Returns all strings in the response
     * @return String response
     */
    public String get() {
        StringBuilder stringResult = new StringBuilder();
        for (int i = 0; i < response.size(); i++) {
            stringResult.append(response.get(i) + "\n");
        }
        return stringResult.toString();
    }

    public void clear() {
        response.clear();
    }
}
