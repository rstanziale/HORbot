package settings;

import common.UserPreferences;

import java.util.HashMap;
import java.util.Map;

/**
 * Define HOR message handler class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class HORmessageHandler {

    // USER COMMAND
    private Map<Integer, String> userCommand = new HashMap<>();

    /**
     *
     * @param user_id
     * @param userPreferences
     * @param received_text
     * @param userCommand
     */
    public static void setMessageText(long user_id,
                                      UserPreferences userPreferences,
                                      String received_text,
                                      String userCommand) {
        switch (received_text) {
            case HORcommands.LOGIN:
                break;
            case HORcommands.HELP:
                break;
            case HORcommands.RESETANSWER:
                break;
            case HORcommands.SETACTIVITY:
                break;
        }
    }
}
