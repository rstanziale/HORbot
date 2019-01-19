package common;

import beans.facets.Ontology;
import beans.recommender.Item;
import beans.survey.Location;
import beans.survey.Survey;
import beans.survey.SurveyContext;

import java.util.List;

/**
 * Define UserPreferences class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class UserPreferences {
    private Survey survey;
    private SurveyContext surveyContext;
    private Ontology ontology;
    private Location location;
    private List<Item> recommendPOI;

    /**
     * Constructor of the UserPreferences
     * @param questionPath String representing the path of the questions
     * @param contextsPath String representing the path of the contexts
     * @param activitiesPath String representing the path of the activities
     */
    public UserPreferences(String questionPath, String contextsPath, String activitiesPath) {
        this.survey = new Survey(questionPath);
        this.surveyContext = new SurveyContext(contextsPath, activitiesPath);
    }

    /**
     * Get the survey of questions
     * @return Survey representing the questions and answers
     */
    public Survey getSurvey() {
        return survey;
    }

    /**
     * Get the survey context
     * @return SurveyContext representing the survey of contexts chosen from user
     */
    public SurveyContext getSurveyContext() {
        return surveyContext;
    }

    /**
     * Get the ontology returned from Myrror after user login
     * @return Ontology user data
     */
    public Ontology getOntology() {
        return ontology;
    }

    /**
     * Set the user ontology data
     * @param ontology Ontology user data returned from Myrror after user login
     */
    public void setOntology(Ontology ontology) {
        this.ontology = ontology;
    }

    /**
     * Get the location set from user
     * @return Location representing the user position
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Set the user location
     * @param location Location representing the user position
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Check if the list of recommend Item is populate
     * @return boolean flag
     */
    public boolean checkListRecommendPOI() {
        return this.recommendPOI != null;
    }

    /**
     * Get the first non recommended Item
     * @return First Item non recommended to the user
     */
    public Item getRecommendPOI() {
        Item item = null;
        int index = 0;
        boolean value = true;

        while (value && index < this.recommendPOI.size()) {
            item = this.recommendPOI.get(index);
            if (!item.isRecommended()) {
                value = false;
                item.setRecommended();
            }
            index++;
        }

        return item;
    }

    /**
     * Check if the UserPreferences are complete before recommend an item
     * @return boolean flag
     */
    public boolean isComplete() {
        return this.location != null && this.surveyContext != null;
    }

    /**
     * Set recommendPOI given by Lucen search
     * @param recommendPOI List of Item
     */
    public void setRecommendPOI(List<Item> recommendPOI) {
        this.recommendPOI = recommendPOI;
    }
}
