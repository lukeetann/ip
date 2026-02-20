package cors.command;

/**
 * Enum for different types of commands
 */
public enum CommandType {
    EMPTY, BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, FAIL;

    public int length() {
        return this.toString().length();
    }
}
