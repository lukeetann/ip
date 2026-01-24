public class IncorrectTextException extends CorsException {
    public IncorrectTextException() {
        super("Error: Incorrect input!\nTo add an item to the list, type todo, deadline, or event.");
    }
}