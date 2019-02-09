package survey.context.beans;

/**
 * Define Activity class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class Activity {
    private boolean checked;
    private String activityName;

    /**
     * Constructor of thectivity
     * @param activityName String representing the name of the activity
     */
    public Activity(String activityName) {
        this.checked = false;
        this.activityName = activityName;
    }

    /**
     * Check if the activity is checked
     * @return boolean representing the check value
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Change the activity check value
     */
    public void setChecked() {
        this.checked = !this.checked;
    }

    /**
     * Reset the checked value
     */
    public void resetChecked() {
        this.checked = false;
    }

    /**
     * Get the activity name
     * @return String representing the activity name
     */
    public String getActivityName() {
        return activityName;
    }

    @Override
    public String toString() {
        return this.checked ? ":ballot_box_with_check:" + " " + this.activityName
                : ":white_medium_small_square:" + " " + this.activityName;
    }
}
