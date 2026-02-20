package cors.command;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.function.Consumer;

import cors.exception.FileErrorException;
import cors.ui.Ui;

/**
 * Deals with making sense of the user command or data loaded from a file.
 */
public class Parser {

    // === File parsing indexes ===
    private static final int COMMAND_TYPE_INDEX = 1;
    private static final int MARK_INDEX = 5;
    private static final int TASK_START_INDEX = 8;

    // === File format tokens ===
    private static final String BY_TOKEN = " (by: ";
    private static final String FROM_TOKEN = " (from: ";
    private static final String TO_TOKEN = " to: ";

    // === CLI tokens ===
    private static final String CLI_BY = " /by ";
    private static final String CLI_FROM = " /from ";
    private static final String CLI_TO = " /to ";

    // === Date patterns ===
    private static final String DATE_PATTERN_SHORT = "d MMM yyyy', 'ha";
    private static final String DATE_PATTERN_LONG = "dd MMM yyyy', 'ha";
    private static final String CLI_DATE_PATTERN = "dd-MM-yyyy HHmm";

    private static final int DATE_LENGTH_THRESHOLD = 15;

    /**
     * Parses a single line of text from the save file into a Command object.
     */
    public Command parseFromFile(String input) {
        if (input == null) {
            return new Command().setType(CommandType.EMPTY);
        }

        switch (input.charAt(COMMAND_TYPE_INDEX)) {
        case 'T':
            return parseTodoFromFile(input);
        case 'D':
            return parseDeadlineFromFile(input);
        case 'E':
            return parseEventFromFile(input);
        default:
            throw new FileErrorException();
        }
    }

    private Command parseTodoFromFile(String input) {
        Command command = new Command()
                .setType(CommandType.TODO)
                .setTask(input.substring(TASK_START_INDEX));

        return applyMarkStatus(command, input);
    }

    private Command parseDeadlineFromFile(String input) {
        Command command = new Command().setType(CommandType.DEADLINE);
        applyMarkStatus(command, input);

        int byIndex = input.indexOf(BY_TOKEN);
        command.setTask(input.substring(TASK_START_INDEX, byIndex));

        LocalDateTime byDate = parseFileDate(
                input,
                byIndex + BY_TOKEN.length(),
                input.length() - 1,
                input.length() - byIndex - BY_TOKEN.length() - 1
        );

        command.setBy(byDate);
        return command;
    }

    private Command parseEventFromFile(String input) {
        try {
            Command command = new Command().setType(CommandType.EVENT);
            applyMarkStatus(command, input);

            int fromIndex = input.indexOf(FROM_TOKEN);
            int toIndex = input.indexOf(TO_TOKEN);

            command.setTask(input.substring(TASK_START_INDEX, fromIndex));

            LocalDateTime from = parseFileDate(
                    input,
                    fromIndex + FROM_TOKEN.length(),
                    toIndex,
                    toIndex - fromIndex - FROM_TOKEN.length()
            );

            LocalDateTime to = parseFileDate(
                    input,
                    toIndex + TO_TOKEN.length(),
                    input.length() - 1,
                    input.length() - toIndex - TO_TOKEN.length() - 1
            );

            command.setFrom(from);
            command.setTo(to);

            return command;

        } catch (DateTimeException e) {
            throw new FileErrorException();
        }
    }

    private Command applyMarkStatus(Command command, String input) {
        if (input.charAt(MARK_INDEX) == 'X') {
            return command.mark();
        }
        return command.unmark();
    }

    private LocalDateTime parseFileDate(String input,
                                        int start,
                                        int end,
                                        int lengthCheck) {

        String pattern = (lengthCheck > DATE_LENGTH_THRESHOLD)
                ? DATE_PATTERN_LONG
                : DATE_PATTERN_SHORT;

        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .parseCaseInsensitive()
                .appendPattern(pattern)
                .toFormatter();

        return LocalDateTime.parse(input.substring(start, end), formatter);
    }

    /**
     * Parses the raw user input from CLI into a Command object.
     */
    public Command parse(String input, Ui ui) {

        switch (input) {
        case "bye":
            return new Command().setType(CommandType.BYE);
        case "list":
            return new Command().setType(CommandType.LIST);
        default:
            return parseComplexCommand(input, ui);
        }
    }

    private Command parseComplexCommand(String input, Ui ui) {

        if (input.startsWith("mark")) {
            return parseIndexCommand(input, CommandType.MARK, CommandType.MARK.length() + 1);

        } else if (input.startsWith("unmark")) {
            return parseIndexCommand(input, CommandType.UNMARK, CommandType.UNMARK.length() + 1);

        } else if (input.startsWith("todo")) {
            return parseTodoCommand(input, ui);

        } else if (input.startsWith("deadline")) {
            return parseDeadlineCommand(input, ui);

        } else if (input.startsWith("event")) {
            return parseEventCommand(input, ui);

        } else if (input.startsWith("delete")) {
            return parseIndexCommand(input, CommandType.DELETE, CommandType.DELETE.length() + 1);

        } else if (input.startsWith("find")) {
            return parseFindCommand(input);
        }

        return new Command().setType(CommandType.FAIL);
    }

    private Command parseIndexCommand(String input, CommandType type, int indexStart) {

        Command command = new Command().setType(type);

        if (input.length() > indexStart) {
            int index = Integer.parseInt(input.substring(indexStart));
            command.setIndex(index);
        }

        return command;
    }

    private Command parseTodoCommand(String input, Ui ui) {
        Command command = new Command().setType(CommandType.TODO);

        if (input.length() > CommandType.TODO.length() + 1) {
            command.setTask(input.substring(CommandType.TODO.length() + 1));
        }

        return command;
    }

    private Command parseDeadlineCommand(String input, Ui ui) {
        Command command = new Command().setType(CommandType.DEADLINE);

        int byIndex = input.indexOf(CLI_BY);
        if (byIndex > 0) {
            command.setTask(input.substring(CommandType.DEADLINE.length(), byIndex));
            parseCliDate(input.substring(byIndex + CLI_BY.length()),
                    command::setBy, command, ui);
        }

        return command;
    }

    private Command parseEventCommand(String input, Ui ui) {
        Command command = new Command().setType(CommandType.EVENT);

        int fromIndex = input.indexOf(CLI_FROM);
        int toIndex = input.indexOf(CLI_TO);

        if (fromIndex > 0 && toIndex > fromIndex) {
            command.setTask(input.substring(6, fromIndex));

            parseCliDate(input.substring(fromIndex + CLI_FROM.length(), toIndex),
                    command::setFrom, command, ui);

            parseCliDate(input.substring(toIndex + CLI_TO.length()),
                    command::setTo, command, ui);
        }

        return command;
    }

    private Command parseFindCommand(String input) {
        Command command = new Command().setType(CommandType.FIND);

        if (input.length() > 5) {
            command.setTask(input.substring(5));
        }

        return command;
    }

    private void parseCliDate(String dateString,
                              Consumer<LocalDateTime> setter,
                              Command command,
                              Ui ui) {

        try {
            setter.accept(LocalDateTime.parse(
                    dateString,
                    DateTimeFormatter.ofPattern(CLI_DATE_PATTERN)
            ));
        } catch (DateTimeException e) {
            command.setType(CommandType.EMPTY);
            ui.showDateTimeError();
        }
    }
}