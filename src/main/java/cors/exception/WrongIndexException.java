package cors.exception;

public class WrongIndexException extends CorsException {
    public WrongIndexException() {
        super("Usage: delete <number>\nE.g. delete 3");
    }
}