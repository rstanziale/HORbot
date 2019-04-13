package settings;

/**
 * Define HOR commands class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class HORCommands {
    // TELEGRAM COMMANDS
    public final static String START = "/start";
    final static String LOGIN = "/login";
    final static String SURVEY = "/survey";
    final static String SET_LOCATION = "/setlocation";
    final static String SET_CONTEXT = "/setcontext";
    final static String BUILD_PROFILE = "/buildprofile";
    final static String SHOW_CONTEXT = "/showcontext";
    final static String SHOW_ANSWER = "/showanswer";
    final static String SHOW_PROFILE = "/showprofile";
    final static String RESET_ANSWER = "/resetanswer";
    final static String RESET_PROFILE = "/resetprofile";
    final static String RESET_CONTEXT = "/resetcontext";
    final static String RECOMMEND = "/recommend";
    final static String RECOMMEND_ALL = "/recommendall";
    final static String HELP = "/help";

    // RECOMMEND ALL COMMANDS
    final static String CONTENT_BASED = "contentbased";
    final static String CONTEXT_AWARE_PRE = "contextawarepre";
    final static String CONTEXT_AWARE_POST = "contextawarepost";
    final static String GRAPH_BASED = "graphbased";

    // SET CONTEXT COMMANDS
    final static String SET_COMPANY = "setcompany";
    final static String SET_RESTED = "setrested";
    final static String SET_MOOD = "setmood";
    final static String SET_ACTIVITY = "setactivity";
    final static String SET_INTERESTS = "setinterests";
    final static String CONTEXT_DONE = "contextdone";
    final static String FRIENDS = "friends";
    final static String FAMILY_PARTNER = "familypartner";
    final static String ASSOCIATES = "associates";
    final static String RESTED_TRUE = "restedtrue";
    final static String RESTED_FALSE = "restedfalse";
    final static String ACTIVITY_TRUE = "activitytrue";
    final static String ACTIVITY_FALSE = "activityfalse";
    final static String MOOD_TRUE = "moodtrue";
    final static String MOOD_FALSE = "moodfalse";
    final static String INTERESTS_TRUE = "intereststrue";
    final static String INTERESTS_FALSE = "interestsfalse";

    // ADMIN COMMANDS
    final static String LOGFILE = "/logfile";
    final static String LOG_PREFERENCES_FILE = "/logpreferencesfile";
    final static String LOG_SURVEY_FILE = "/logsurveyfile";
    final static String SET_CONF = "/setconf";
    final static String CONF = "/conf";
    final static String SHOW_TAGS = "/showtags";
}
