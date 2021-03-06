package common;

import recommender.contentBased.beans.Item;
import settings.HORMessages;
import survey.context.beans.Activity;
import survey.context.beans.Context;
import survey.question.beans.Question;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import static java.lang.Math.toIntExact;

public class Utils {

    public static final String CAPTION_LOGFILE = "Users log file.";
    public static final String CAPTION_PREFERENCES_LOGFILE = "Users preferences log file.";
    public static final String CAPTION_SURVEY_LOGFILE = "Users survey log file.";

    /**
     * Get Bot property
     * @param property property name
     * @return property value
     */
    public static String getProperty(String property) {
        InputStream inputStream;
        String result = "";

        try {
            Properties prop = new Properties();
            String propFileName = "horbot.properties";

            inputStream = Utils.class.getClassLoader().getResourceAsStream(propFileName);

            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

            // Get the property value and print it out
            result = prop.getProperty(property);
            inputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Calculate distance between two points in latitude and longitude taking
     * into account height difference. If you are not interested in height
     * difference pass 0.0. Uses Haversine method as its base.
     *
     * lat1, lon1 Start point lat2, lon2 End point el1 Start altitude in meters
     * el2 End altitude in meters
     * @param lat1 representing the latitude of point A
     * @param lat2 representing the latitude of point B
     * @param lon1 representing the longitude of point A
     * @param lon2 representing the longitude of point B
     * @param el1 representing the altitude of point A
     * @param el2 representing the altitude of point B
     * @return Distance in Meters
     */
    public static double distance(double lat1, double lat2,
                                  double lon1, double lon2,
                                  double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    /**
     * Read items to recommend to user from a CSV file
     * @param csvFile representing the file path of CSV file
     * @return an item list
     */
    public static List<Item> readCSV(String csvFile) {
        List<Item> pois = new ArrayList<>();
        InputStream in = Utils.class.getResourceAsStream(csvFile);
        BufferedReader br = null;
        String line;

        try {
            br = new BufferedReader(new InputStreamReader(in));

            while ((line = br.readLine()) != null) {
                // Use comma as separator
                String[] items = line.split(HORMessages.CSV_SPLIT);
                pois.add(new Item(items[0], items[1], items[2], items[3], items[4],
                        items[5], Double.valueOf(items[6]), Integer.valueOf(items[7]),
                        Float.valueOf(items[8]), Float.valueOf(items[9])));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return pois;
    }

    /**
     * Create log file of HOR users about their interactions
     * @param userPreferences representing users preferences
     * @return a file to send to admin
     */
    public static File createLogFile(Map<Integer, UserPreferences>  userPreferences) {
        File logFile = new File("log.csv");
        try (PrintWriter writer = new PrintWriter(logFile)) {
            for (long user_id : userPreferences.keySet()) {
                writer.write(createLogFileByUser(user_id, userPreferences.get(toIntExact(user_id))));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return logFile;
    }

    /**
     * Create log file of HOR users about their preferences
     * @param userPreferences representing users preferences
     * @return a file to send to admin
     */
    public static File createPreferencesLogFile(Map<Integer, UserPreferences>  userPreferences) {
        File logFile = new File("logPreferences.csv");
        try (PrintWriter writer = new PrintWriter(logFile)) {
            for (long user_id : userPreferences.keySet()) {
                writer.write(createPreferencesLogFileByUser(user_id, userPreferences.get(toIntExact(user_id))));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return logFile;
    }

    /**
     * Create log file of HOR users about their survey
     * @param userPreferences representing users preferences
     * @return a file to send to admin
     */
    public static File createSurveyLogFile(Map<Integer, UserPreferences>  userPreferences) {
        File logFile = new File("logSurvey.csv");
        try (PrintWriter writer = new PrintWriter(logFile)) {
            for (long user_id : userPreferences.keySet()) {
                writer.write(createSurveyLogFileByUser(user_id, userPreferences.get(toIntExact(user_id))));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return logFile;
    }

    /**
     * Create a document file of user recommend items
     * @param user_id representing user id
     * @param userPreferences representing user preferences
     * @return a String to write into file
     */
    private static String createLogFileByUser(long user_id, UserPreferences userPreferences) {
        StringBuilder sb = new StringBuilder();
        sb.append("UserID"); // User id
        sb.append(';');
        sb.append("Myrror Used"); // True if user did login
        sb.append(';');
        sb.append("Myrror Data"); // Data returned after login
        sb.append(';');
        sb.append("Myrror Changed"); // Data updated by user
        sb.append(';');
        sb.append("Context Time"); // Time for set user context
        sb.append(';');
        sb.append("Item Name"); // Recommend item name
        sb.append(';');
        sb.append("Recommned Type"); // Recommend type
        sb.append(';');
        sb.append("Index Item"); // Index of item in the list of user recommend items
        sb.append(';');
        sb.append("Item Liked"); // True if user like it
        sb.append(';');
        sb.append("Recommend Time"); // Time from start recommend and first like
        sb.append('\n');
        if (userPreferences.checkListRecommendPOI()) {
            int index = 0;
            while (index < userPreferences.getListRecommendPOI().size()) {
                Item i = (Item)userPreferences.getListRecommendPOI().toArray()[index];
                if (i.isRecommended()) {
                    sb.append(user_id); // User id
                    sb.append(';');
                    sb.append(userPreferences.isMyrrorUsed()); // True if user did login
                    sb.append(';');
                    sb.append(userPreferences.getUserContext().getMyrrorData()); // Data returned after login
                    sb.append(';');
                    sb.append(userPreferences.getMyrrorUpdated()); // Data updated by user
                    sb.append(';');
                    sb.append(userPreferences.getContextTime()); // Time for set user context
                    sb.append(';');
                    sb.append(i.getName()); // Recommend item name
                    sb.append(';');
                    sb.append(i.getRecommenderType()); // Recommend type
                    sb.append(';');
                    sb.append(index + 1); // Index of item in the list of user recommend items
                    sb.append(';');
                    sb.append(i.isLiked()); // True if user like it
                    sb.append(';');
                    sb.append(i.getInteractionTime()); // Time from start recommend and first like
                    sb.append('\n');
                }
                index++;
            }
        }
        return sb.toString();
    }

    /**
     * Create a document file of user preferences
     * @param user_id representing user id
     * @param userPreferences representing user preferences
     * @return a String to write into file
     */
    private static String createPreferencesLogFileByUser(long user_id, UserPreferences userPreferences) {
        StringBuilder sb = new StringBuilder();
        for (Context context : userPreferences.getSurveyContext().getSurveyValues()) {
            List<String> activities = new ArrayList<>();
            for (Activity activity : context.getActivities()) {
                if (activity.isChecked()) {
                    activities.add(activity.getActivityName());
                }
            }
            sb.append(user_id);
            sb.append(';');
            sb.append(context.getContextName());
            sb.append(';');
            sb.append(activities);
            sb.append('\n');
        }
        return sb.toString();
    }

    /**
     * Create a document file of user survey
     * @param user_id representing user id
     * @param userPreferences representing user preferences
     * @return a String to write into file
     */
    private static String createSurveyLogFileByUser(long user_id, UserPreferences userPreferences) {
        StringBuilder sb = new StringBuilder();
        for (Question question : userPreferences.getSurvey().getSurvey()) {
            sb.append(user_id);
            sb.append(';');
            sb.append(question.getQuestion());
            sb.append(';');
            sb.append(question.getAnswer()); // From 1 to 4
            sb.append('\n');
        }
        return sb.toString();
    }
}
