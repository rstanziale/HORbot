package settings;

import common.*;
import beans.survey.Activity;
import beans.survey.Context;
import beans.survey.Location;
import com.vdurmont.emoji.EmojiParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.toIntExact;

/**
 * Define HORBot class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class HORBot extends TelegramLongPollingBot implements LoggerInterface {

    // TELEGRAM COMMANDS
    private final static String START = "/start";
    private final static String LOGIN = "/login";
    private final static String SURVEY = "/survey";
    private final static String SETLOCATION = "/setlocation";
    private final static String SETCONTEXTS = "/setcontexts";
    private final static String SHOWANSWER = "/showanswer";
    private final static String SHOWCONTEXTS = "/showcontexts";
    private final static String RESETANSWER = "/resetanswer";
    private final static String RESETCONTEXTS = "/resetcontexts";
    private final static String GETRECOMMEND = "/getrecommend";
    private final static String HELP = "/help";

    // USER COMMAND
    private Map<Integer, String> userCommand = new HashMap<>();

    // USER PREFERENCES
    private Map<Integer, UserPreferences> userPreferences = new HashMap<>();

    // Recommender module for send items to users
    private Recommender recommenderForBari;
    private Recommender recommenderForTorino;

    /**
     * Get message from chat and send a new message
     * @param update message received
     */
    public void onUpdateReceived(Update update) {
        // Check text message
        if(update.hasMessage() && update.getMessage().hasText() || update.getMessage().hasLocation()) {

            // Set user info for logging
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChat().getId();

            // Survey for this user
            if (!userPreferences.containsKey(toIntExact(user_id))) {
                userPreferences.put(toIntExact(user_id),
                        new UserPreferences("/questions.txt",
                                "/contexts.txt",
                                "/activities.txt"));

                userCommand.put(toIntExact(user_id), "Comando sconosciuto");
            }

            // Set chat ID
            Long sender_id = update.getMessage().getChatId();

            // Set text received
            String received_text = update.getMessage().hasText() ? update.getMessage().getText() : "";

            // Set message structure
            SendMessage message = new SendMessage()
                    .setChatId(sender_id);

            // START COMMAND
            if (received_text.equals(START)) {
                userCommand.replace(toIntExact(user_id), START);
                message.setText(HORmessages.MESSAGE_START);
            }
            // SHOW ANSWER COMMAND
            else if (received_text.equals(SHOWANSWER)) {
                userCommand.replace(toIntExact(user_id), SHOWANSWER);
                message.setText(userPreferences.get(toIntExact(user_id)).getSurvey().toString());
            }
            // SHOW CONTEXTS COMMAND
            else if (received_text.equals(SHOWCONTEXTS)) {
                userCommand.replace(toIntExact(user_id), SHOWCONTEXTS);
                message.setText(EmojiParser.parseToUnicode(userPreferences.get(toIntExact(user_id)).getSurveyContext().showContextChosen()));
            }
            // RESET ANSWERS
            else if (received_text.equals(RESETANSWER)) {
                userCommand.replace(toIntExact(user_id), RESETANSWER);
                userPreferences.get(toIntExact(user_id)).getSurvey().resetAnswers();
                message.setText(HORmessages.MESSAGE_SURVEY_RESET);
            }
            // HELP COMMAND
            else if (received_text.equals(HELP)) {
                userCommand.replace(toIntExact(user_id), HELP);
                message.setText(HORmessages.MESSAGE_HELP);
            }
            // SET POSITION COMMAND
            else if (received_text.equals(SETLOCATION)) {
                userCommand.replace(toIntExact(user_id), SETLOCATION);
                message.setText(HORmessages.MESSAGE_POSITION);
            }
            else if (update.getMessage().hasLocation() && this.userCommand.get(toIntExact(user_id)).equals(SETLOCATION)) {
                Location l = new Location(update.getMessage().getLocation().getLongitude(), update.getMessage().getLocation().getLatitude());
                this.userPreferences.get(toIntExact(user_id)).setLocation(l);

                message.setText(HORmessages.MESSAGE_POSITION_SAVED);

                userCommand.replace(toIntExact(user_id), "Comando sconosciuto");
            }
            // GET RECOMMEND COMMAND
            else if (received_text.equals(GETRECOMMEND)) {
                try {
                    if (userPreferences.get(toIntExact(user_id)).isComplete()) {
                        Location location = userPreferences.get(toIntExact(user_id)).getLocation();
                        this.initRecommender(location);

                        if(!userPreferences.get(toIntExact(user_id)).checkListRecommendPOI()) {
                            // General context
                            userPreferences.get(toIntExact(user_id))
                                    .setRecommendPOI(this.getRecommender(location)
                                            .searchItems(this.generateQuery(toIntExact(user_id)), location));
                        }

                        String text = userPreferences.get(toIntExact(user_id)).getRecommendPOI() != null
                                ? userPreferences.get(toIntExact(user_id)).getRecommendPOI().toString()
                                : HORmessages.MESSAGE_NO_ACTIVITY;
                        message.setText(text);
                    } else {
                        message.setText(HORmessages.MESSAGE_REFERENCES_NON_COMPLETE);
                    }
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
            }
            // LOGIN COMMAND
            else if (received_text.equals(LOGIN)) {
                userCommand.replace(toIntExact(user_id), LOGIN);
                message.setText(HORmessages.MESSAGE_LOGIN);
            }
            else if (!received_text.equals(LOGIN) && this.userCommand.get(toIntExact(user_id)).equals(LOGIN)) {
                userCommand.replace(toIntExact(user_id), "Comando sconosciuto");
                message.setText(HORmessages.messageLogin(received_text, userPreferences.get(toIntExact(user_id))) + "\n" +
                        HORmessages.MESSAGE_LOGIN_COMPLETE);
            }
            // SURVEY COMMAND
            else if (received_text.equals(SURVEY)) {
                userCommand.replace(toIntExact(user_id), SURVEY);

                if (!userPreferences.get(toIntExact(user_id)).getSurvey().isComplete()) {
                    message.setText(HORmessages.MESSAGE_SURVEY_START +
                            userPreferences.get(toIntExact(user_id)).getSurvey().getNextQuestion());

                    // Add keyboard to message
                    message.setReplyMarkup(HORmessages.setReplyKeyboard());
                } else {
                    message.setText(HORmessages.MESSAGE_SURVEY_ALREADY_COMPLETE);
                }
            }
            else if (!received_text.equals(SURVEY) && this.userCommand.get(toIntExact(user_id)).equals(SURVEY)) {
                userPreferences.get(toIntExact(user_id)).getSurvey().setNextAnswer(received_text);

                if (!userPreferences.get(toIntExact(user_id)).getSurvey().isComplete()) {
                    message.setText(userPreferences.get(toIntExact(user_id)).getSurvey().getNextQuestion());

                    // Add keyboard to message
                    message.setReplyMarkup(HORmessages.setReplyKeyboard());
                } else {
                    message.setText(HORmessages.MESSAGE_SURVEY_COMPLETE);

                    // Remove keyboard from message
                    ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
                    message.setReplyMarkup(keyboardMarkup);
                    userCommand.replace(toIntExact(user_id), "Comando sconosciuto");
                }
            }
            // SET CONTEXTS COMMAND
            else if (received_text.equals(SETCONTEXTS)) {
                userCommand.replace(toIntExact(user_id), SETCONTEXTS);

                if (!userPreferences.get(toIntExact(user_id)).getSurveyContext().isComplete()) {
                    Context c = userPreferences.get(toIntExact(user_id)).getSurveyContext().getNextContext();
                    message.setText(EmojiParser.parseToUnicode(c.toString()))
                            .setParseMode("markdown");
                } else {
                    message.setText(HORmessages.MESSAGE_ACTIVITIES_CHOSEN);
                    userCommand.replace(toIntExact(user_id), "Comando sconosciuto");
                }
            }
            else if (!received_text.equals(SETCONTEXTS) && this.userCommand.get(toIntExact(user_id)).equals(SETCONTEXTS)) {
                Context c = userPreferences.get(toIntExact(user_id)).getSurveyContext().getNextContext();
                if (HORmessages.setActivityFlags(c, received_text.split(" "))) {
                    String text = userPreferences.get(toIntExact(user_id)).getSurveyContext().isComplete()
                            ? HORmessages.MESSAGE_ACTIVITIES_SAVED
                            : userPreferences.get(toIntExact(user_id)).getSurveyContext().getNextContext().toString();

                    message.setText(EmojiParser.parseToUnicode(text))
                            .setParseMode("markdown");
                } else {
                    message.setText(HORmessages.MESSAGE_ACTIVITIES_ERROR);
                    userCommand.replace(toIntExact(user_id), "Comando sconosciuto");
                }
            }
            // GET RESET CONTEXTS COMMAND
            else if (received_text.equals(RESETCONTEXTS)) {
                userCommand.replace(toIntExact(user_id), RESETCONTEXTS);

                for (Context c : userPreferences.get(toIntExact(user_id)).getSurveyContext().getSurveyValues()) {
                    c.resetCheckValues();
                }
                message.setText(HORmessages.MESSAGE_ACTIVITIES_RESET);
            }
            // UNKNOWN COMMAND
            else {
                message.setText(HORmessages.UNKNOWN_COMMAND + received_text);
            }

            // Log message values
            logger.info(new HORLogger().logUserInfo(user_first_name, user_last_name, user_username, Long.toString(user_id)));

            try {
                // Send answer
                execute(message);

            } catch(TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Get Bot Username
     * @return Bot Username
     */
    public String getBotUsername() {
        return new PropertyUtilities().getProperty("username");
    }

    /**
     * Get Bot Token
     * @return Bot Token
     */
    public String getBotToken() {
        return new PropertyUtilities().getProperty("token");
    }

    /**
     * Generate query with chosen activities for general context
     * @param user_id representing user ID
     * @return Query string
     */
    private String generateQuery(int user_id) {
        String query = "";
        Context c = userPreferences.get(toIntExact(user_id)).getSurveyContext().getSurveyValues().iterator().next();
        for (Activity a : c.getActivities()) {
            if (a.isChecked()) {
                query += HORmessages.TAGS.get(a.getActivityName()) + " ";
            }
        }
        return query;
    }

    /**
     * Initialize recommender system according user position
     * Bari: 41.1115511, 16.7419939
     * Torino: 45.0702388, 7.6000489
     * @param location representing the user position
     */
    private void initRecommender(Location location) {
        double fromHereToBari = Utils.distance(location.getLatitude(), 41.1115511,
                location.getLongitude(), 16.7419939,
                0.0, 0.0);
        double fromHereToTorino = Utils.distance(location.getLatitude(), 45.0702388,
                location.getLongitude(), 7.6000489,
                0.0, 0.0);

        if (fromHereToBari < fromHereToTorino) {
            this.recommenderForBari = new Recommender("/businesses_bari.csv");
        } else {
            this.recommenderForTorino = new Recommender("/businesses_torino.csv");
        }
    }

    /**
     * Get recommender according user position
     * Bari: 41.1115511, 16.7419939
     * Torino: 45.0702388, 7.6000489
     * @param location representing the user position
     * @return Recommender
     */
    private Recommender getRecommender(Location location) {
        double fromHereToBari = Utils.distance(location.getLatitude(), 41.1115511,
                location.getLongitude(), 16.7419939,
                0.0, 0.0);
        double fromHereToTorino = Utils.distance(location.getLatitude(), 45.0702388,
                location.getLongitude(), 7.6000489,
                0.0, 0.0);

        return  fromHereToBari < fromHereToTorino
                ? this.recommenderForBari
                : this.recommenderForTorino;
    }
}