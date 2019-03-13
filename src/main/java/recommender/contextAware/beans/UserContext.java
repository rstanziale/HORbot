package recommender.contextAware.beans;

import ontology.beans.facets.Interest;
import ontology.beans.facets.Ontology;
import settings.HORmessages;

import java.util.*;

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
    private List<String> interests;
    private List<String> myrrorData;

    /**
     * Constructor of context
     * @param ontology get from Myrror
     */
    public UserContext(Ontology ontology) {
        this.myrrorData = new ArrayList<>();
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
            this.addMyrrorData("Activity");
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
            this.addMyrrorData("Mood");
        }
        if (ontology.getPhysicalStates() != null
                && ontology.getPhysicalStates().getSleep() != null
                && ontology.getPhysicalStates().getSleep().size() > 0) {
            this.rested = ontology.getPhysicalStates()
                    .getSleep().get(ontology.getPhysicalStates().getSleep().size() - 1)
                    .getMinutesAsleep() >= 360; // 6 hours for rested
            this.addMyrrorData("Rested");
        }
        if (ontology.getInterests().size() > 0) {
            this.interests = new ArrayList<>();
            this.setPreferencesMapped(ontology.getInterests());
        }
    }

    public UserContext() {
        this.myrrorData = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        this.week = ((calendar.get(Calendar.DAY_OF_WEEK) >= Calendar.MONDAY) &&
                (calendar.get(Calendar.DAY_OF_WEEK) <= Calendar.FRIDAY));
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

    /**
     * Get Myrror interests mapped with Yelp categories
     * @return a list of interests
     */
    public Collection<String> getPreferencesMapped() {
        return this.interests;
    }

    /**
     * Get Myrror data returned from Myrror after login
     * @return a list of String
     */
    public List<String> getMyrrorData() {
        return myrrorData;
    }

    @Override
    public String toString() {
        String company = "Amici".equals(this.company) ?  "Sei in compagnia di amici"
                : "Famiglia/Fidanzata-o".equals(this.company) ? "Sei in compagnia di Famiglia/Fidanzata-o"
                : "Colleghi".equals(this.company) ? "Sei in compagnia di colleghi"
                : "Non so con chi sei";
        String rested = this.rested == null ? "Non so se sei riposato" : this.rested ? "Sei riposato" : "Non sei riposato";
        String mood = this.mood == null ? "Non so se di che umore sei" : this.mood ? "Sei di buon umore" : "Non sei ddi bon umore";
        String activity = this.activity == null ? "Non so se hai fatto attività fisica" : this.activity ? "Hai fatto attività fisica" : "Non hai fatto attività fisica";

        return "Il tuo contesto risulta: \n" +
                " - " + company + "\n" +
                " - " + rested + "\n" +
                " - " + mood + "\n" +
                " - " + activity;
    }

    /**
     * Mapping user Myrror interests with Yelp categories
     * @param interests representing user Myrorr interests
     */
    private void setPreferencesMapped(List<Interest> interests) {
        for (Interest interest : interests) {
            for (String activity : HORmessages.TAGS.keySet()) {
                if (activity.contains(interest.getValue())) {
                    this.interests.add(HORmessages.TAGS.get(activity));
                }
            }
        }
    }

    /**
     * Add a label that identify Myrror data returned after login
     * @param label representing Myrror facet
     */
    private void addMyrrorData(String label) {
        this.myrrorData.add(label);
    }
}
