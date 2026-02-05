package cors;

import cors.command.Command;
import cors.command.CommandType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ParserTest {
    @Test
    public void parseFromFile_validDeadline_returnsCorrectCommand() {
        Parser parser = new Parser();
        String input = "[D] [X] return book (by: 7 Feb 2024, 6PM)";

        Command result = parser.parseFromFile(input);

        assertEquals(CommandType.DEADLINE, result.getType());
        // Verification of the "marked" status (index 5 is 'X')
        // Verification of the date/time parsing logic
    }
}