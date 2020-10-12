package seedu.duke;

public class Parser {
    public static Command parse(String userInput) {
        String[] fullCommand = userInput.split("\\s+");
        switch (fullCommand[0].toLowerCase()) {
        case "food":
            return new AddFoodCommand(userInput);
        case "exercise":
            return new AddExerciseCommand(userInput);
        case "view":
            return new ViewCommand(userInput);
        case "delete":
            return new DeleteCommand(userInput);
        case "bye":
            return new ExitCommand(userInput);
        default:
            return new InvalidCommand(userInput);
        }
    }
}
