package beans.values;

import java.util.Date;

/**
 * Define a generic activity element for Behaviors facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class BehaviorsFromActivity {
    private int steps;
    private int distance;
    private int floors;
    private int elevation;
    private int minutesSedentary;
    private int minutesLightlyActive;
    private int minutesFairlyActive;
    private int minutesVeryActive;
    private int activityCalories;
    private String nameActivity;
    private Date startTime;
    private String description;

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public int getFloors() {
        return floors;
    }

    public void setFloors(int floors) {
        this.floors = floors;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public int getMinutesSedentary() {
        return minutesSedentary;
    }

    public void setMinutesSedentary(int minutesSedentary) {
        this.minutesSedentary = minutesSedentary;
    }

    public int getMinutesLightlyActive() {
        return minutesLightlyActive;
    }

    public void setMinutesLightlyActive(int minutesLightlyActive) {
        this.minutesLightlyActive = minutesLightlyActive;
    }

    public int getMinutesFairlyActive() {
        return minutesFairlyActive;
    }

    public void setMinutesFairlyActive(int minutesFairlyActive) {
        this.minutesFairlyActive = minutesFairlyActive;
    }

    public int getMinutesVeryActive() {
        return minutesVeryActive;
    }

    public void setMinutesVeryActive(int minutesVeryActive) {
        this.minutesVeryActive = minutesVeryActive;
    }

    public int getActivityCalories() {
        return activityCalories;
    }

    public void setActivityCalories(int activityCalories) {
        this.activityCalories = activityCalories;
    }

    public String getNameActivity() {
        return nameActivity;
    }

    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
