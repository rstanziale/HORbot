package common;

import ontology.beans.facets.Ontology;
import recommender.contentBased.beans.Item;
import recommender.contextAware.beans.UserContext;
import survey.context.beans.Location;
import survey.context.sevices.SurveyContext;
import survey.question.services.Survey;

import java.util.*;

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
    private UserContext userContext;
    private int recommendType;
    private List<Item> recommendPOI;
    private Map<Integer, Item> recommendAllPOI;
    private boolean myrrorUsed;
    private List<String> myrrorUpdated;
    private long contextTime;
    private boolean flagContextTime;
    private long startRecommendTime;
    private int configuration;

    /**
     * Constructor of the UserPreferences
     * @param questionPath String representing the path of the questions
     * @param contextsPath String representing the path of the contexts
     * @param activitiesPath String representing the path of the activities
     */
    public UserPreferences(String questionPath, String contextsPath, String activitiesPath) {
        this.survey = new Survey(questionPath);
        this.surveyContext = new SurveyContext(contextsPath, activitiesPath);
        this.recommendPOI = new ArrayList<>();
        this.recommendAllPOI = new HashMap<>();
        this.myrrorUpdated = new ArrayList<>();
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
     * Get user context
     * @return user context when ask a recommend
     */
    public UserContext getUserContext() {
        return userContext;
    }

    /**
     * Set user context
     * @param userContext user context when ask a recommend
     */
    public void setUserContext(UserContext userContext) {
        this.userContext = userContext;
    }

    /**
     * Set flag if the user has used Myrror
     * @param myrrorUsed true if user has used Myror, else false
     */
    public void setMyrrorUsed(boolean myrrorUsed) {
        this.myrrorUsed = myrrorUsed;
    }

    /**
     * Add label that identify context attribute modified by user
     * @param label representing context attribute
     */
    public void addLabelToMyrrorUpdated(String label) {
        this.myrrorUpdated.add(label);
    }

    /**
     * Get time when user starts to use recommender system
     * @return long representing start time
     */
    public long getStartRecommendTime() {
        return startRecommendTime;
    }

    /**
     * Set time when user starts to set own context
     * @param contextTime representing start time
     */
    public void setContextTime(long contextTime) {
        this.contextTime = contextTime;
    }

    /**
     * Get time when user starts to set own context
     * @return long representing start time
     */
    public long getContextTime() {
        return contextTime;
    }

    /**
     * Check if context tie is set
     * @return boolean flag
     */
    public boolean isFlagContextTime() {
        return flagContextTime;
    }

    /**
     * Set context time flag
     * @param flagContextTime boolean flag
     */
    public void setFlagContextTime(boolean flagContextTime) {
        this.flagContextTime = flagContextTime;
    }

    /**
     * Set time when user starts to use recommender system
     * @param startRecommendTime representing start time
     */
    public void setStartRecommendTime(long startRecommendTime) {
        this.startRecommendTime = startRecommendTime;
    }

    /**
     * Get the first non recommended Item and set it as recommended
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
     * Get last recommend item to user
     * @return Last Item recommended to the user
     */
    public Item getLastRecommendPOI() {
        Item item = null;
        int index = 0;
        boolean value = true;

        while (value && index < this.recommendPOI.size()) {
            item = this.recommendPOI.get(index);
            Item nextItem = (index + 1 < this.recommendPOI.size())
                    ? this.recommendPOI.get(index + 1)
                    : this.recommendPOI.get(this.recommendPOI.size() - 1);
            if (item.isRecommended() && !nextItem.isRecommended()) {
                value = false;
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
        return this.location != null && this.surveyContext.isComplete() && this.userContext != null;
    }

    /**
     * Set recommendPOI given by Lucene search
     * @param recommendPOI List of Item
     */
    public void setRecommendPOI(List<Item> recommendPOI) {
        boolean flag = false;
        int index = 0;

        while (!flag && index < recommendPOI.size()) {
            if (this.addRecommendPOI(recommendPOI.get(index))) {
                flag = true;
            }
            index++;
        }
    }

    /**
     * Add recommend POI into a list
     * @param item item recommended
     */
    public void setRecommendPOI(Item item) {
        this.recommendPOI.add(item);
    }

    /**
     * Get system configuration of this user
     * @return int
     */
    public int getConfiguration() {
        return configuration;
    }

    /**
     * Set configuration of this user
     * @param configuration int
     */
    public void setConfiguration(int configuration) {
        this.configuration = configuration;
    }

    /**
     * Check if the user has used Myrror
     * @return boolean flag
     */
    public boolean isMyrrorUsed() {
        return myrrorUsed;
    }

    /**
     * Check if the list of recommend Item is populate
     * @return boolean flag
     */
    boolean checkListRecommendPOI() {
        return this.recommendPOI != null;
    }

    /**
     * Get recommend type
     * @return int representing recommend type
     */
    public int getRecommendType() {
        return recommendType;
    }

    /**
     * Set recommend type
     * @param recommendType int representing recommend type
     */
    public void setRecommendType(int recommendType) {
        this.recommendType = recommendType;
    }

    /**
     * Set recommend list for chose
     * @param recommendPOI reresenting item to chose
     */
    public void setRecommendAllPOI(List<Item> recommendPOI) {
        boolean flag = false;
        int index = 0;

        while (!flag && index < recommendPOI.size()) {
            if (this.addRecommendAllPOI(recommendPOI.get(index))) {
                flag = true;
            }
            index++;
        }
    }

    /**
     * Print all four element
     * @return a string
     */
    public String printRecommendAllPOI() {
        String text = "";
        for (int rt = 1; rt <= 4; rt++) {
            text = text.concat(this.getRecommendAllPOI(rt).toString() + "\n\n");
        }
        return text;
    }

    /**
     * Get item according recommend type
     * @param recommendType representing type of recommendation
     * @return an item
     */
    public Item getRecommendAllPOI(int recommendType) {
        return this.recommendAllPOI.get(recommendType);
    }

    /**
     * Get list of user recommend POI
     * @return a collection of Item
     */
    Collection<Item> getListRecommendPOI() {
        return this.recommendPOI;
    }

    /**
     * Get list of context attributes updated by user
     * @return list of String
     */
    List<String> getMyrrorUpdated() {
        return this.myrrorUpdated;
    }

    /**
     * Add an item to recommend item list
     * @param item representing recommend item
     * @return boolean flag
     */
    private boolean addRecommendPOI(Item item) {
        boolean flag = false;

        if (!this.checkItemInRecommendList(item)) {
            this.recommendPOI.add(item);
            flag = true;
        }

        return flag;
    }

    /**
     * Add item into map that user has to chose
     * @param item representing the item to chose
     * @return a boolean flag
     */
    private boolean addRecommendAllPOI(Item item) {
        boolean flag = false;

        if (!this.checkItemInRecommendList(item) && !checkItemInRecommendAllMap(item)) {
            item.setRecommended();
            this.recommendAllPOI.put(item.getRecommenderType(), item);
            flag = true;
        }

        return flag;
    }

    /**
     * Check if an item is present in recommend item list
     * @param item representing recommend item
     * @return boolean flag
     */
    private boolean checkItemInRecommendList(Item item) {
        boolean flag = false;
        int index = 0;

        while (!flag && index < this.recommendPOI.size()) {
            if (item.getName().equals(this.recommendPOI.get(index).getName())) {
                flag = true;
            }
            index++;
        }

        return flag;
    }

    /**
     * Check if an item is present in map chose structure
     * @param item representing item to chose
     * @return boolean flag
     */
    private boolean checkItemInRecommendAllMap(Item item) {
        boolean flag = false;
        Iterator<Integer> iterator = this.recommendAllPOI.keySet().iterator();

        while (!flag && iterator.hasNext()) {
            Item i = this.recommendAllPOI.get(iterator.next());
            if (i.getName().equals(item.getName())) {
                flag = true;
            }
        }

        return flag;
    }
}
