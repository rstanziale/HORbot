package common;

import beans.survey.Survey;
import beans.survey.SurveyContext;

/**
 * Define UserPreferences class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class UserPreferences {
    private Survey survey;
    private SurveyContext surveyContext;

    /**
     * Constructor of the UserPreferences
     * @param questionPath String representing the path of the questions
     * @param contextsPath String representing the path of the contexts
     */
    public UserPreferences(String questionPath, String contextsPath) {
        this.survey = new Survey(questionPath);
        this.surveyContext = new SurveyContext(contextsPath);
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
}
