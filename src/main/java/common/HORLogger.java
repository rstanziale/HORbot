package common;

public class HORLogger {

    /**
     * Logging messages from user
     * @param first_name of the user
     * @param last_name of the user
     * @param user_name of the user
     * @param user_id of the user
     * @return message log
     */
    public String logUserInfo(String first_name, String last_name, String user_name, String user_id) {
        return "Message from " + first_name + " " + last_name
                + " (username = " + user_name + " + id = " + user_id + ").";
    }
}
