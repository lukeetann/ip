package cors.exception;

public class IncorrectDateException extends CorsException {
    public IncorrectDateException() {
        super("Date or time is incorrect. Please follow given format.");
    }
}