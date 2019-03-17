package recommender.contextAware.services;

import recommender.contextAware.beans.UserContext;
import settings.HORMessages;
import survey.context.beans.Activity;
import survey.context.beans.Context;

import java.util.*;

/**
 * Define Context Aware Recommender class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class ContextAwareRecommender {

    /**
     * Check if the user context has all values for recommend
     * @param userContext representing the user context
     * @return an integer flag representing the check
     */
    public static int checkValuesUserContext(UserContext userContext) {
        int check = 0;

        if (userContext.getCompany() == null) {
            check = 1;
        } else if (userContext.isRested() == null) {
            check = 2;
        } else if (userContext.isMood() == null) {
            check = 3;
        } else if (userContext.isActivity() == null) {
            check = 4;
        }

        return check;
    }

    /**
     * Generate contextual query for user according his preferences
     * @param userContext representing user context
     * @param contexts representing a collection of contexts
     * @return String representing the query
     */
    public static String generateContextAwareQuery(UserContext userContext, Collection<Context> contexts) {
        String query = "";
        Map<String, Context> contextMap = new HashMap<>();
        List<Context> contextList = new ArrayList<>(contexts);

        contextMap.put("week", userContext.isWeek() ? contextList.get(1) : contextList.get(2));
        contextMap.put("rested", userContext.isRested() ? contextList.get(9) : contextList.get(10));
        contextMap.put("mood", userContext.isMood() ? contextList.get(3) : contextList.get(4));
        contextMap.put("activity", userContext.isActivity() ? contextList.get(8) : null);
        switch (userContext.getCompany()) {
            case "Amici":
                contextMap.put("company", contextList.get(5));
                break;
            case "Famiglia/Fidanzata-o":
                contextMap.put("company", contextList.get(6));
                break;
            case "Colleghi":
                contextMap.put("company", contextList.get(7));
                break;
        }

        for (Context context : contextMap.values()) {
            query = query.concat(generateQueryByContext(context) + " ");
        }

        // If there are interests that it is possible to use with Yelp
        if (userContext.isInterestsUsed() && userContext.getPreferencesMapped() != null) {
            for (String interest : userContext.getPreferencesMapped()) {
                query = query.concat(interest + " ");
            }
        }

        return query;
    }

    /**
     * Set query according user preferences according context
     * @param context representing a generic Context
     * @return String representing user preferences in this context
     */
    private static String generateQueryByContext(Context context) {
        String query = "";
        if (context != null) {
            for (Activity a : context.getActivities()) {
                if (a.isChecked()) {
                    query = query.concat(HORMessages.TAGS.get(a.getActivityName()) + " ");
                }
            }
        }
        return query;
    }
}
