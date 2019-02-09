package survey.context.beans;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Define Context class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class Context {
    private String contextName;
    private Map<Integer, Activity> activities;

    /**
     * Constructor of the context
     * @param contextName String representing the name of the context
     * @param activitiesPath String representing the path of activities
     */
    public Context(String contextName, String activitiesPath) {
        this.contextName = contextName;
        this.activities = new HashMap<>();
        InputStream in = getClass().getResourceAsStream(activitiesPath);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            int index = 1;

            for(String line; (line = br.readLine()) != null; ) {
                this.activities.put(index, new Activity(line));
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get the name of the context
     * @return String representing the name of the context
     */
    public String getContextName() {
        return contextName;
    }

    /**
     * Get all activities of given context
     * @return Collection of activities og given context
     */
    public Collection<Activity> getActivities() {
        return activities.values();
    }

    /**
     * Get list of activities chosen by user
     * @return String representing list of activities chosen by user
     */
    public String showActivitiesChosen() {
        String s = "";

        for (Activity a : activities.values()) {
            if (a.isChecked()) {
                s += a.toString() + "\n";
            }
        }

        if (s.equals("")) {
            s = "Nessuna attività scelta.";
        }

        return s;
    }

    /**
     * Check if the survey activity is complete
     * @return true if the survey activity has three checked items else false
     */
    public boolean isComplete() {
        int value = 0;

        for (Activity a: this.activities.values()) {
            if (a.isChecked()) {
                value++;
            }
        }

        return value == 3;
    }

    /**
     * Update activity with the new checked value
     * @param index int representing the index of the activity
     */
    public void setActivityCheck(int index) {
        Activity a = this.activities.get(index);
        a.setChecked();
        this.activities.replace(index, a);
    }

    /**
     * Reset check values if user wants to do survey activities again
     */
    public void resetCheckValues() {
        for (Activity a : activities.values()) {
            a.resetChecked();
        }
    }

    @Override
    public String toString() {
        String s = "*" + this.getContextName() +
                "*:\n(Scegli tre attività che ti interessano, separate da uno spazio)\n\n";
        int i = 1;

        for (Activity a : activities.values()) {
            s += String.valueOf(i) + ". " + a.toString() + "\n";
            i++;
        }
        return s;
    }
}
