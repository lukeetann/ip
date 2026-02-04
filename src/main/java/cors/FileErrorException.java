package cors;

public class FileErrorException extends CorsException {
    public FileErrorException() {
        super("Error loading file");
    }
}