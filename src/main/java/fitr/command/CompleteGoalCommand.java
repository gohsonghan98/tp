package fitr.command;

import fitr.goal.Goal;
import fitr.exercise.Recommender;
import fitr.exception.FitrException;
import fitr.list.ListManager;
import fitr.storage.StorageManager;
import fitr.ui.Ui;
import fitr.user.User;

import java.io.IOException;

import static fitr.common.Commands.COMMAND_COMPLETE;
import static fitr.common.Commands.COMMAND_GOAL;
import static fitr.common.Messages.ERROR_INDEX_DOES_NOT_EXIST;
import static fitr.common.Messages.ERROR_IN_FILE;
import static fitr.common.Messages.PHRASE_EXTRA_PARAMETERS;
import static fitr.common.Messages.SPACE_STRING;
import static fitr.common.Messages.SYMBOL_YES;

/**
 * Marks a particular goal as complete.
 */
public class CompleteGoalCommand extends Command {
    public CompleteGoalCommand(String command) {
        this.command = command;
    }

    @Override
    public void execute(ListManager listManager, StorageManager storageManager, User user, Recommender recommender) {
        try {
            if (command.split(SPACE_STRING, 2)[0].equals(COMMAND_GOAL)) {
                if (command.split(SPACE_STRING).length < 2) {
                    Ui.printCustomError("No index specified!");
                    return;
                }
                if (command.split(SPACE_STRING).length > 2) {
                    Ui.printFormatError(PHRASE_EXTRA_PARAMETERS);
                    return;
                }
                command = command.split(SPACE_STRING, 2)[1];
                int completedGoalIndex = Integer.parseInt(command) - 1;
                if (completedGoalIndex < -1) {
                    throw new NumberFormatException();
                }
                Goal completedGoal = listManager.getGoal(completedGoalIndex);
                if (completedGoal.getStatus(completedGoal, listManager.getFoodList(),
                        listManager.getExerciseList(), user) == SYMBOL_YES) {
                    Ui.printCustomError("This goal has already been completed.");
                    return;
                }
                listManager.getGoalList().deleteGoal(completedGoalIndex);
                listManager.getGoalList().addGoal(completedGoal);
                completedGoal.markAsCompleted();
                storageManager.writeGoalList(listManager.getGoalList(), listManager.getFoodList(),
                        listManager.getExerciseList(), user);
                Ui.printCustomMessage("Yay! You completed:\n\t"
                        + completedGoal.getDescription());
            } else {
                throw new FitrException();
            }
        } catch (IndexOutOfBoundsException e) {
            Ui.printCustomError(ERROR_INDEX_DOES_NOT_EXIST);
        } catch (NumberFormatException e) {
            Ui.printCustomError("Sorry, deletion index must be an positive integer.");
        } catch (IOException e) {
            Ui.printCustomError(ERROR_IN_FILE);
        } catch (FitrException e) {
            Ui.printFormatError(COMMAND_COMPLETE);
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
