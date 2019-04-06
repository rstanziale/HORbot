package settings;

import com.vdurmont.emoji.EmojiParser;
import common.UserPreferences;
import common.Utils;
import org.apache.lucene.queryparser.classic.ParseException;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import recommender.contentBased.beans.Item;
import recommender.contentBased.services.Recommender;
import recommender.contextAware.beans.UserContext;
import recommender.contextAware.services.ContextAwareRecommender;
import survey.context.beans.Context;
import survey.context.beans.Location;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.toIntExact;

/**
 * Define HOR message handler class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class HORMessageHandler {

    // USER COMMAND
    private Map<Integer, String> userCommand;

    // Recommender module for send items to users
    private Recommender recommenderForBari;
    private Recommender recommenderForTorino;

    /**
     * Constructor of HORMessageHandler
     */
    public HORMessageHandler() {
        this.userCommand = new HashMap<>();
    }

    /**
     * Initialize user command
     * @param user_id representing user id
     */
    void initializeUser(long user_id) {
        this.userCommand.put(toIntExact(user_id), "unknown");
    }

    /**
     * Set message to send to user
     * @param user_id representing user id
     * @param userPreferences representing user preferences
     * @param message representing received message from user
     * @return a text message or a document message
     */
    Object setMessage(long user_id,
                      UserPreferences userPreferences,
                      Message message) {
        String received_text = message.hasText() ? message.getText() : "";
        SendMessage sendMessage = new SendMessage();
        SendDocument sendDocument = new SendDocument();

        boolean noCommand = false;

        switch (received_text) {
            case HORCommands.START:
                this.userCommand.replace(toIntExact(user_id), HORCommands.START);
                userPreferences.setContextTime(System.currentTimeMillis());
                sendMessage.setText(HORMessages.MESSAGE_START);
                break;

            case HORCommands.LOGIN:
                userCommand.replace(toIntExact(user_id), HORCommands.LOGIN);
                userPreferences.setStartRecommendTime(System.currentTimeMillis());
                sendMessage.setText(HORMessages.MESSAGE_LOGIN);
                break;

            case HORCommands.SET_CONTEXT:
                userCommand.replace(toIntExact(user_id), HORCommands.SET_CONTEXT);
                userPreferences.setStartRecommendTime(System.currentTimeMillis());
                if (userPreferences.getUserContext() == null) {
                    userPreferences.setUserContext(new UserContext());
                }
                if (!userPreferences.isMyrrorUsed()) {
                    userPreferences.setMyrrorUsed(false);
                }
                sendMessage.setText(userPreferences.getUserContext().toString());
                sendMessage.setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.SURVEY:
                userCommand.replace(toIntExact(user_id), HORCommands.SURVEY);

                if (!userPreferences.getSurvey().isComplete()) {
                    sendMessage.setText(HORMessages.MESSAGE_SURVEY_START +
                            userPreferences.getSurvey().getNextQuestion());
                    // Add keyboard to message
                    sendMessage.setReplyMarkup(HORMessages.setReplyKeyboardForSurvey());
                } else {
                    sendMessage.setText(HORMessages.MESSAGE_SURVEY_ALREADY_COMPLETE);
                }
                break;

            case HORCommands.SET_LOCATION:
                userCommand.replace(toIntExact(user_id), HORCommands.SET_LOCATION);
                sendMessage.setText(HORMessages.MESSAGE_POSITION);
                break;

            case HORCommands.BUILD_PROFILE:
                userCommand.replace(toIntExact(user_id), HORCommands.BUILD_PROFILE);

                if (!userPreferences.getSurveyContext().isComplete()) {
                    Context c = userPreferences.getSurveyContext().getNextContext();
                    sendMessage.setText(EmojiParser.parseToUnicode(c.toString()))
                            .setParseMode("markdown");
                } else {
                    sendMessage.setText(HORMessages.MESSAGE_ACTIVITIES_CHOSEN);
                    userCommand.replace(toIntExact(user_id), "unknown");
                }
                break;

            case HORCommands.SHOW_CONTEXT:
                userCommand.replace(toIntExact(user_id), HORCommands.SHOW_CONTEXT);
                sendMessage.setText(userPreferences.getUserContext().toString() + "\n" +
                        HORMessages.MESSAGE_CONTEXT_EDIT);
                break;

            case HORCommands.SHOW_ANSWER:
                userCommand.replace(toIntExact(user_id), HORCommands.SHOW_ANSWER);
                sendMessage.setText(userPreferences.getSurvey().toString());
                break;

            case HORCommands.SHOW_PROFILE:
                userCommand.replace(toIntExact(user_id), HORCommands.SHOW_PROFILE);
                sendMessage.setText(EmojiParser.parseToUnicode(userPreferences.getSurveyContext().showContextChosen()));
                break;

            case HORCommands.RESET_ANSWER:
                userCommand.replace(toIntExact(user_id), HORCommands.RESET_ANSWER);
                userPreferences.getSurvey().resetAnswers();
                sendMessage.setText(HORMessages.MESSAGE_SURVEY_RESET);
                break;

            case HORCommands.RESET_PROFILE:
                userCommand.replace(toIntExact(user_id), HORCommands.RESET_PROFILE);

                for (Context c : userPreferences.getSurveyContext().getSurveyValues()) {
                    c.resetCheckValues();
                }
                sendMessage.setText(HORMessages.MESSAGE_ACTIVITIES_RESET);
                break;

            case HORCommands.RECOMMEND:
                try {
                    if (userPreferences.isComplete()) {
                        userPreferences.setStartRecommendTime(System.currentTimeMillis());
                        UserContext userContext = userPreferences.getUserContext();
                        int checkUserContext = ContextAwareRecommender.checkValuesUserContext(userContext);

                        if (checkUserContext == 0) {
                            if (!userPreferences.isFlagContextTime()) {
                                userPreferences.setContextTime(System.currentTimeMillis() - userPreferences.getContextTime());
                                userPreferences.setFlagContextTime(true);
                            }
                            Location location = userPreferences.getLocation();
                            this.initRecommender(location);

                            userPreferences.setRecommendPOI(this.getRecommender(location)
                                            .recommend(userPreferences, userContext, location));

                            String textRecommend;
                            Item item = userPreferences.getRecommendPOI();
                            if (item != null) {
                                textRecommend = item.toString();
                                sendMessage.setReplyMarkup(HORMessages.setReplyKeyboardLike());
                            } else {
                                textRecommend = HORMessages.MESSAGE_NO_ACTIVITY;
                            }

                            sendMessage.setText(EmojiParser.parseToUnicode(textRecommend));
                        } else if (checkUserContext == 1) {
                            sendMessage.setText(HORMessages.MESSAGE_MISSING_COMPANY);
                        } else if (checkUserContext == 2) {
                            sendMessage.setText(HORMessages.MESSAGE_MISSING_RESTED);
                        } else if (checkUserContext == 3) {
                            sendMessage.setText(HORMessages.MESSAGE_MISSING_MOOD);
                        } else if (checkUserContext == 4) {
                            sendMessage.setText(HORMessages.MESSAGE_MISSING_ACTIVITY);
                        }

                    } else {
                        sendMessage.setText(HORMessages.MESSAGE_REFERENCES_NON_COMPLETE);
                    }
                } catch (IOException | ParseException e) {
                    e.printStackTrace();
                }
                break;

            case HORCommands.SET_COMPANY:
                userCommand.replace(toIntExact(user_id), HORCommands.SET_COMPANY);
                // Add keyboard to message
                sendMessage.setReplyMarkup(HORMessages.setReplyKeyboardForCompany());
                sendMessage.setText(HORMessages.MESSAGE_COMPANY);
                break;

            case HORCommands.SET_RESTED:
                userCommand.replace(toIntExact(user_id), HORCommands.SET_RESTED);
                sendMessage.setReplyMarkup(HORMessages.setReplyKeyboardBoolean());
                sendMessage.setText(HORMessages.MESSAGE_RESTED);
                break;

            case HORCommands.SET_MOOD:
                userCommand.replace(toIntExact(user_id), HORCommands.SET_MOOD);
                sendMessage.setReplyMarkup(HORMessages.setReplyKeyboardForMood());
                sendMessage.setText(HORMessages.MESSAGE_MOOD);
                break;

            case HORCommands.SET_ACTIVITY:
                userCommand.replace(toIntExact(user_id), HORCommands.SET_ACTIVITY);
                sendMessage.setReplyMarkup(HORMessages.setReplyKeyboardBoolean());
                sendMessage.setText(HORMessages.MESSAGE_ACTIVITY);
                break;

            case HORCommands.SET_INTERESTS:
                userCommand.replace(toIntExact(user_id), HORCommands.SET_INTERESTS);
                sendMessage.setReplyMarkup(HORMessages.setReplyKeyboardBoolean());
                String textInterests = HORMessages.MESSAGE_INTERESTS;
                if (userPreferences.getUserContext().getPreferencesMapped() != null) {
                    String interests = "";
                    for (String interest : userPreferences.getUserContext().getPreferencesMapped()) {
                        interests = interests.concat(" - " + interest + "\n");
                    }
                    textInterests = textInterests.concat("\n\n" + interests);
                }
                sendMessage.setText(textInterests);
                break;

            case HORCommands.HELP:
                userCommand.replace(toIntExact(user_id), HORCommands.HELP);
                sendMessage.setText(HORMessages.MESSAGE_HELP);
                break;

            case HORCommands.LOGFILE:
                sendDocument.setCaption(Utils.CAPTION_LOGFILE);
                break;

            case HORCommands.LOG_PREFERENCES_FILE:
                sendDocument.setCaption(Utils.CAPTION_PREFERENCES_LOGFILE);
                break;

            case HORCommands.LOG_SURVEY_FILE:
                sendDocument.setCaption(Utils.CAPTION_SURVEY_LOGFILE);
                break;

            case HORCommands.SHOW_TAGS:
                sendMessage.setText(userPreferences.getLastRecommendPOI().getQuery());
                break;

            default:
                noCommand = true;
                if (received_text.equals(EmojiParser.parseToUnicode(":thumbsup:"))) {
                    userCommand.replace(toIntExact(user_id), "voteItem");
                    Item item = userPreferences.getLastRecommendPOI();
                    item.setLiked(true);
                    item.setInteractionTime(System.currentTimeMillis() - userPreferences.getStartRecommendTime());
                } else if (received_text.equals(EmojiParser.parseToUnicode(":thumbsdown:"))) {
                    userCommand.replace(toIntExact(user_id), "voteItem");
                    userPreferences.getLastRecommendPOI().setLiked(false);
                } else {
                    sendMessage.setText(HORMessages.UNKNOWN_COMMAND + received_text);
                }
                break;
        }
        /*
         * If a command has a complex logic interaction
         * When the user chooses a command
         * that needs a longer sequence of questions and answers
         */
        if (noCommand) {
            ReplyKeyboardRemove keyboardMarkup;
            switch (this.userCommand.get(toIntExact(user_id))) {
                case HORCommands.LOGIN:
                    userCommand.replace(toIntExact(user_id), "unknown");
                    String textLogin = HORMessages.messageLogin(received_text, userPreferences);
                    if (userPreferences.getOntology() != null) {
                        userPreferences.setMyrrorUsed(true);
                        userPreferences.setUserContext(new UserContext(userPreferences.getOntology()));
                        textLogin = textLogin.concat("\n" + HORMessages.MESSAGE_LOGIN_COMPLETE);
                    } else {
                        userPreferences.setMyrrorUsed(false);
                        userPreferences.setUserContext(new UserContext());
                    }
                    sendMessage.setText(textLogin);
                    break;

                case HORCommands.SURVEY:
                    userPreferences.getSurvey().setNextAnswer(received_text);

                    if (!userPreferences.getSurvey().isComplete()) {
                        sendMessage.setText(userPreferences.getSurvey().getNextQuestion());
                        // Add keyboard to message
                        sendMessage.setReplyMarkup(HORMessages.setReplyKeyboardForSurvey());
                    } else {
                        sendMessage.setText(HORMessages.MESSAGE_SURVEY_COMPLETE);

                        // Remove keyboard from message
                        keyboardMarkup = new ReplyKeyboardRemove();
                        sendMessage.setReplyMarkup(keyboardMarkup);
                        userCommand.replace(toIntExact(user_id), "unknown");
                    }
                    break;

                case HORCommands.SET_LOCATION:
                    Location l = new Location(message.getLocation().getLongitude(), message.getLocation().getLatitude());
                    userPreferences.setLocation(l);
                    sendMessage.setText(HORMessages.MESSAGE_POSITION_SAVED);
                    userCommand.replace(toIntExact(user_id), "unknown");
                    break;

                case HORCommands.BUILD_PROFILE:
                    Context c = userPreferences.getSurveyContext().getNextContext();
                    if (HORMessages.setActivityFlags(c, received_text.split(" "))) {
                        String textProfile = userPreferences.getSurveyContext().isComplete()
                                ? HORMessages.MESSAGE_ACTIVITIES_SAVED
                                : userPreferences.getSurveyContext().getNextContext().toString();

                        sendMessage.setText(EmojiParser.parseToUnicode(textProfile))
                                .setParseMode("markdown");
                    } else {
                        sendMessage.setText(HORMessages.MESSAGE_ACTIVITIES_ERROR);
                        userCommand.replace(toIntExact(user_id), "unknown");
                    }
                    break;

                case HORCommands.SET_COMPANY:
                    if (HORMessages.checkContextCompany(received_text)) {
                        userPreferences.getUserContext().setCompany(received_text);
                        userPreferences.addLabelToMyrrorUpdated("Company=" + received_text);
                        sendMessage.setText(HORMessages.MESSAGE_CONTEXT_UPDATE);
                    } else {
                        sendMessage.setText(HORMessages.MESSAGE_CONTEXT_ERROR);
                    }
                    // Remove keyboard from message
                    keyboardMarkup = new ReplyKeyboardRemove();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    userCommand.replace(toIntExact(user_id), "unknown");
                    break;

                case HORCommands.SET_RESTED:
                    if (HORMessages.checkContextBoolean(received_text)) {
                        userPreferences.getUserContext()
                                .setRested(received_text.equals("Sì"));
                        userPreferences.addLabelToMyrrorUpdated("Rested=" + received_text);
                        sendMessage.setText(HORMessages.MESSAGE_CONTEXT_UPDATE);
                    } else {
                        sendMessage.setText(HORMessages.MESSAGE_CONTEXT_ERROR);
                    }
                    // Remove keyboard from message
                    keyboardMarkup = new ReplyKeyboardRemove();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    userCommand.replace(toIntExact(user_id), "unknown");
                    break;

                case HORCommands.SET_ACTIVITY:
                    if (HORMessages.checkContextBoolean(received_text)) {
                        userPreferences.getUserContext()
                                .setActivity(received_text.equals("Sì"));
                        userPreferences.addLabelToMyrrorUpdated("Activity=" + received_text);
                        sendMessage.setText(HORMessages.MESSAGE_CONTEXT_UPDATE);
                    } else {
                        sendMessage.setText(HORMessages.MESSAGE_CONTEXT_ERROR);
                    }
                    // Remove keyboard from message
                    keyboardMarkup = new ReplyKeyboardRemove();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    userCommand.replace(toIntExact(user_id), "unknown");
                    break;

                case HORCommands.SET_INTERESTS:
                    if (HORMessages.checkContextBoolean(received_text)) {
                        userPreferences.getUserContext()
                                .setInterestsUsed(received_text.equals("Sì"));
                        sendMessage.setText(HORMessages.MESSAGE_CONTEXT_UPDATE);
                    } else {
                        sendMessage.setText(HORMessages.MESSAGE_CONTEXT_ERROR);
                    }
                    // Remove keyboard from message
                    keyboardMarkup = new ReplyKeyboardRemove();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    userCommand.replace(toIntExact(user_id), "unknown");
                    break;

                case HORCommands.SET_MOOD:
                    if (HORMessages.checkContextMood(received_text)) {
                        userPreferences.getUserContext()
                                .setMood(received_text.equals("Buon umore"));
                        userPreferences.addLabelToMyrrorUpdated("Mood=" + received_text);
                        sendMessage.setText(HORMessages.MESSAGE_CONTEXT_UPDATE);
                    } else {
                        sendMessage.setText(HORMessages.MESSAGE_CONTEXT_ERROR);
                    }
                    // Remove keyboard from message
                    keyboardMarkup = new ReplyKeyboardRemove();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    userCommand.replace(toIntExact(user_id), "unknown");
                    break;

                case "voteItem":
                    // Remove keyboard from message
                    keyboardMarkup = new ReplyKeyboardRemove();
                    sendMessage.setReplyMarkup(keyboardMarkup);
                    sendMessage.setText(HORMessages.MESSAGE_ITEM_VOTED);
                    userCommand.replace(toIntExact(user_id), "unknown");
                    break;
            }
        }

        if (sendDocument.getCaption() != null) {
            return sendDocument;
        } else {
            return sendMessage;
        }
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
            this.recommenderForBari = new Recommender(Utils.readCSV("/businesses_bari.csv"), "bari");
        } else {
            this.recommenderForTorino = new Recommender(Utils.readCSV("/businesses_torino.csv"), "torino");
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
