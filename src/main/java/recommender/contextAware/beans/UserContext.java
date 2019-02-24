package recommender.contextAware.beans;

import ontology.beans.facets.Ontology;

import java.util.Calendar;
import java.util.Date;

/**
 * Define User Context class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class UserContext {
    private boolean week; // True for weekday, false for week-end
    private String company; // Friend or Family/boy-girlfriend or associates
    private Boolean rested; // True for rested, false for tired
    private Boolean mood; // True for good humor, false for bad humor
    private Boolean activity; // True for activity done, false for activity not done

    /**
     * Constructor of context
     * @param ontology get from Myrror
     */
    public UserContext(Ontology ontology) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        this.week = ((calendar.get(Calendar.DAY_OF_WEEK) >= Calendar.MONDAY) &&
                (calendar.get(Calendar.DAY_OF_WEEK) <= Calendar.FRIDAY));
        if (ontology.getBehaviors().getFromActivity() != null
                && ontology.getBehaviors().getFromActivity().size() > 0) {
            this.activity = ontology.getBehaviors()
                    .getFromActivity()
                    .get(ontology.getBehaviors().getFromActivity().size() - 1)
                    .getNameActivity().equals("veryActive");
        }
        if (ontology.getAffects() != null
                && ontology.getAffects().size() > 0) {
            this.mood = ontology.getAffects()
                    .get(ontology.getAffects().size() - 1)
                    .getEmotion().equals("joy")
                    || ontology.getAffects()
                            .get(ontology.getAffects().size() - 1)
                            .getEmotion().equals("surprise")
                    || ontology.getAffects()
                            .get(ontology.getAffects().size() - 1)
                            .getEmotion().equals("neutrality");
        }
        if (ontology.getPhysicalStates() != null
                && ontology.getPhysicalStates().getSleep() != null
                && ontology.getPhysicalStates().getSleep().size() > 0) {
            this.rested = ontology.getPhysicalStates()
                    .getSleep().get(ontology.getPhysicalStates().getSleep().size() - 1)
                    .getMinutesAsleep() >= 360; // 6 hours for rested
        }
    }

    /**
     * Check if todayu is weekday or week-end
     * @return boolean flag
     */
    public boolean isWeek() {
        return week;
    }

    /**
     * Get context company
     * @return String representing the user company
     */
    public String getCompany() {
        return company;
    }

    /**
     * Set context company
     * @param company String representing the user company
     */
    public void setCompany(String company) {
        this.company = company;
    }

    /**
     * Check if the user is rested
     * @return boolean flag, true for rested, false for tired
     */
    public Boolean isRested() {
        return rested;
    }

    /**
     * Set context user rested
     * @param rested boolean flag, true for rested, false for tired
     */
    public void setRested(boolean rested) {
        this.rested = rested;
    }

    /**
     * Check if the user is in a good humor or not
     * @return boolean flag, true for good humor, false for bad humor
     */
    public Boolean isMood() {
        return mood;
    }

    /**
     * Set context user mood
     * @param mood boolean flag, true for good humor, false for bad humor
     */
    public void setMood(boolean mood) {
        this.mood = mood;
    }

    /**
     * Check if the user has done any physical activity
     * @return boolean flag, true for activity done, false for activity not done
     */
    public Boolean isActivity() {
        return activity;
    }

    /**
     * Set context user activity
     * @param activity boolean flag, true for activity done, false for activity not done
     */
    public void setActivity(boolean activity) {
        this.activity = activity;
    }
}
