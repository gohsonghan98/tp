package fitr;

import fitr.command.Command;
import fitr.storage.StorageManager;
import fitr.tip.TipManager;
import fitr.list.ExerciseList;
import fitr.list.FoodList;
import fitr.list.GoalList;
import fitr.list.TipList;
import fitr.ui.Ui;
import fitr.user.User;
import fitr.parser.Parser;

import java.io.IOException;

public class Fitr {
    private StorageManager storageManager;
    private ExerciseList exerciseList;
    private FoodList foodList;
    private User user;
    private GoalList goalList;
    private Recommender recommender;

    public Fitr() {
        try {
            Ui.printGreetingMessage();

            storageManager = new StorageManager();

            user = storageManager.loadUserProfile();
            storageManager.writeUserProfile(user);

            foodList = new FoodList(storageManager.loadFoodList());
            exerciseList = new ExerciseList(storageManager.loadExerciseList());
            goalList = new GoalList(storageManager.loadGoalList());

            TipList tipList = new TipList(storageManager.loadTipList());
            TipManager tipOfTheDay = new TipManager(tipList);
            recommender = new Recommender();
            tipOfTheDay.execute();

            Ui.printSuggestQuestion();
        } catch (IOException e) {
            Ui.printCustomError("An error has occurred - the file cannot be opened!");
        }
    }

    public void run() {
        boolean isExit = false;
        while (!isExit) {
            String userInput = Ui.read();
            Command c = Parser.parse(userInput);
            c.execute(foodList, exerciseList, storageManager, user, goalList, recommender);
            isExit = c.isExit();
        }
        Ui.printExitMessage();
    }

    public static void main(String[] args) {
        new Fitr().run();
    }
}
