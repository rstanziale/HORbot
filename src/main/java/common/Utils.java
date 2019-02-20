package common;

import recommender.contentBased.beans.Item;
import settings.HORmessages;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utils {

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
                String[] items = line.split(HORmessages.CSV_SPLIT);
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
     * Create a document file of user recommend items
     * @param user_id representing user id
     * @param userPreferences representing user preferences
     */
    public static File createLogFile(long user_id, UserPreferences userPreferences) {
        File logFile = new File("log.csv");
        try (PrintWriter writer = new PrintWriter(logFile)) {
            StringBuilder sb = new StringBuilder();
            if (userPreferences.checkListRecommendPOI()) {
                for (Item i : userPreferences.getListRecommendPOI()) {
                    sb.append(user_id);
                    sb.append(",");
                    sb.append(i.getName());
                    sb.append(",");
                    sb.append(i.getRecommenderType());
                    sb.append("\n");
                    writer.write(sb.toString());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return logFile;
    }
}
