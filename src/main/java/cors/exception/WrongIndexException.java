package cors.exception;

public class WrongIndexException extends CorsException {
    public WrongIndexException() {
        super("Index out of bounds");
    }
}