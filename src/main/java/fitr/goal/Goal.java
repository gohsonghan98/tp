package fitr.goal;

import fitr.list.ExerciseList;
import fitr.list.FoodList;
import fitr.user.User;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static fitr.goal.CheckGoalStatus.checkGoalStatus;

public class Goal {
    protected LocalDate createdDate;
    protected String goalType;
    protected String description;
    protected String goalStatus;

    public Goal(LocalDate createdDate, String goalType, String description) {
        this.createdDate = createdDate;
        this.goalType = goalType;
        this.description = description;
        this.goalStatus = "0.0";
    }

    public Goal(LocalDate createdDate, String goalType, String goalStatus, String description) {
        this.createdDate = createdDate;
        this.goalType = goalType;
        this.description = description;
        this.goalStatus = goalStatus;
    }

    public String getCreatedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        return createdDate.format(formatter);
    }

    public String getDescription() {
        return description;
    }

    public String getGoalType() {
        return goalType;
    }

    public String getStatus(Goal goal, FoodList foodList, ExerciseList exerciseList, User user) {
        String rawStatus = checkGoalStatus(goalStatus, goal, foodList, exerciseList, user) + "%";
        if (rawStatus.equals("0.0%") || rawStatus.equals("✘%")) {
            return "✘";
        } else if (rawStatus.equals("100.0%") || rawStatus.equals("✓%")) {
            return "✓";
        }
        return rawStatus;
    }

    public void markAsCompleted() {
        this.goalStatus = "✓";
    }

    public void setGoal(Goal goal, String goalStatus) {
        this.goalType = goal.getGoalType();
        this.goalStatus = goalStatus;
        this.description = goal.getDescription();
    }
}
