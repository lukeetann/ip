package cors.command;

/**
 * Enum for different types of commands
 */
public enum CommandType {
    EMPTY, BYE, LIST, MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, FAIL;

    private int length = this.toString().length();

    public int getLength() {
        return length;
    }

    public void setLengthShort() {
        length = 1;
    }

    public void setTodoLengthLong() {
        length = "todo".length();
    }

    public void setDeadlineLengthLong() {
        length = "deadline".length();
    }

    public void setEventLengthLong() {
        length = "event".length();
    }
}
