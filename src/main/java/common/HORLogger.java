package common;

public class HORLogger {

    /**
     * Logging messages from user
     * @param first_name of the user
     * @param last_name of the user
     * @param user_id of the user
     * @param txt message from the user
     * @param bot_answer message to the user
     */
    public String logUserInfo(String first_name, String last_name, String user_name, String user_id, String txt, String bot_answer) {
        return "Message from " + first_name + " " + last_name + ". (username = " + user_name + " + id = " + user_id + ") "
                + "\nText: " + txt + "\nBot answer: " + bot_answer + "\n";
    }
}
