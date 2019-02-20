package recommender;

import common.UserPreferences;
import recommender.contextAware.beans.UserContext;
import recommender.contextAware.services.ContextAwareRecommender;
import settings.HORmessages;
import survey.context.beans.Activity;
import survey.context.beans.Context;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Define Recommend Utils class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class RecommendUtils {

    /**
     * Get recommend type
     * @return int representing recommend type (1: Content-based, 2: Pre-filtering, 3: Post-filtering)
     */
    public static int getRecommendType() {
        return getRandomIntegerBetweenRange(1, 3);
    }

    /**
     * Return the query according recommend type
     * @param userPreferences representing user preferences
     * @param userContext representing user context
     * @param recommendType representing the recommend type (Content-based, Pre-filtering, Post-filtering)
     * @return a string representing the query for Lucene
     */
    public static String generateQueryAccordingRecommendType(UserPreferences userPreferences,
                                                             UserContext userContext,
                                                             int recommendType) {
        String query = "";

        if (recommendType == 1) {
            query = queryTransform(generateGeneralContextQuery(userPreferences));
        } else if (recommendType == 2) {
            query = queryTransform(generateContextualQuery(userPreferences, userContext));
        } else if (recommendType == 3) {
            query = queryTransform(generateGeneralContextQuery(userPreferences)
                    + " " + generateContextualQuery(userPreferences, userContext));
        }

        return query;
    }

    /**
     * Count term by term of the input query
     * @param query representing the query to transform
     * @return transformed Query
     */
    private static String queryTransform(String query) {
        Map<String, Integer> fields = new HashMap<>();

        for (String s : query.split(" ")) {
            if (fields.containsKey(s)) {
                fields.put(s, fields.get(s) + 1);
            } else {
                fields.put(s, 1);
            }
        }

        return getTransformedQuery(fields);
    }

    /**
     * Transform mapped query in a Lucene string
     * @param fields representing a map with the term as key and value as number of occurrences of term
     * @return Transformed query
     */
    private static String getTransformedQuery(Map<String, Integer> fields) {
        String s = "";

        for (String key : fields.keySet()) {
            s = s.concat(key + "^" + fields.get(key) + " ");
        }
        return s;
    }

    /**
     * Generate query with chosen activities for general context
     * @param userPreferences representing user preferences
     * @return Query string
     */
    private static String generateGeneralContextQuery(UserPreferences userPreferences) {
        String query = "";
        Context c = userPreferences.getSurveyContext().getSurveyValues().iterator().next();
        for (Activity a : c.getActivities()) {
            if (a.isChecked()) {
                query = query.concat(HORmessages.TAGS.get(a.getActivityName()) + " ");
            }
        }
        return query;
    }

    /**
     * Generate contextual query according user preferences and user context
     * @param userPreferences representing the user preferences
     * @param userContext representing the user context
     * @return a contextual query string
     */
    private static String generateContextualQuery(UserPreferences userPreferences, UserContext userContext) {
        return ContextAwareRecommender.generateContextAwareQuery(userContext,
                userPreferences.getSurveyContext().getSurveyValues());
    }

    /**
     * Get random value between two values
     * @param min representing the minimum value
     * @param max representing the max value
     * @return integer value between min and max
     */
    private static int getRandomIntegerBetweenRange(int min, int max) {
        Random r = new Random();
        return r.nextInt((max - min) + 1) + min;
    }
}
