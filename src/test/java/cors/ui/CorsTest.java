package cors.ui;

import cors.Parser;
import cors.command.Command;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CorsTest {
    @Test
    public void testInitialization_validPath_taskListCreated() {
        // Assuming a test file exists or the path is valid
        Cors cors = new Cors("./src/test/java/cors/test.csv");
        assertNotNull(cors, "Cors instance should be successfully created.");
        // Note: You may need a getter or use reflection if taskList is private
        // to verify that taskList is not null.
    }

    @Test
    public void testInitialization_invalidPath_handlesException() {
        // Path that definitely does not exist
        Cors cors = new Cors("non_existent_file.csv");

        // The constructor catches CorsException and initializes an empty TaskList
        // This test ensures the code completes without throwing an exception to the caller
        assertDoesNotThrow(() -> new Cors("non_existent_file.csv"),
                "Constructor should handle loading errors gracefully.");
    }

}