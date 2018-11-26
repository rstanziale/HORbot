package settings;

import beans.survey.Location;
import com.vdurmont.emoji.EmojiParser;
import common.HORLogger;
import common.HORmessages;
import common.PropertyUtilities;
import common.UserPreferences;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
    private final static String HELP = "/help";

    // USER COMMAND
    private Map<Integer, String> userCommand = new HashMap<>();

    // USER PREFERENCES
    private Map<Integer, UserPreferences> userPreferences = new HashMap<>();

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
                userPreferences.put(toIntExact(user_id), new UserPreferences("/questions.txt", "/activities.txt"));
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
                message.setText("Ciao, per cominciare effettua il login per Myrror attraverso il comando /login!");
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
                message.setText("Risposte del questionario reimpostate.");
            }
            // HELP COMMAND
            else if (received_text.equals(HELP)) {
                userCommand.replace(toIntExact(user_id), HELP);
                message.setText("Puoi utilizzarmi con i seguenti comandi:\n\n" +
                        "/login - Effettua il login per Myrror\n" +
                        "/survey - Inizia il questionario\n" +
                        "/showanswer - Visualizza le risposte del questionario\n" +
                        "/resetanswer - Reimposta le risposte del questionario\n" +
                        "/help - Informazioni sui comandi");
            }
            // LOGIN COMMAND
            else if (received_text.equals(LOGIN)) {
                userCommand.replace(toIntExact(user_id), LOGIN);
                message.setText("Inviami le credenziali secondo questo modello:\n\nemail\npassword");
            }
            else if (!received_text.equals(LOGIN) && this.userCommand.get(toIntExact(user_id)).equals(LOGIN)) {
                userCommand.replace(toIntExact(user_id), "Comando sconosciuto");
                message.setText(HORmessages.messageLogin(received_text, userPreferences.get(toIntExact(user_id))) + "\n" +
                        "Inizia il questionario con il comando /survey.");
            }
            // SET POSITION COMMAND
            else if (received_text.equals(SETLOCATION)) {
                userCommand.replace(toIntExact(user_id), SETLOCATION);
                message.setText("Inviami la tua posizione");
            }
            else if (update.getMessage().hasLocation() && this.userCommand.get(toIntExact(user_id)).equals(SETLOCATION)) {
                Location l = new Location(update.getMessage().getLocation().getLongitude(), update.getMessage().getLocation().getLatitude());
                this.userPreferences.get(toIntExact(user_id)).setLocation(l);

                message.setText("Posizione salvata.");

                userCommand.replace(toIntExact(user_id), "Comando sconosciuto");
            }
            // SURVEY COMMAND
            else if (received_text.equals(SURVEY)) {
                userCommand.replace(toIntExact(user_id), SURVEY);

                if (!userPreferences.get(toIntExact(user_id)).getSurvey().isComplete()) {
                    message.setText("Indica quanto sei d'accordo con le seguenti affermazioni \n\n" +
                            userPreferences.get(toIntExact(user_id)).getSurvey().getNextQuestion());

                    // Add keyboard to message
                    message.setReplyMarkup(HORmessages.setReplyKeyboard());
                } else {
                    message.setText("Questionario già completato.");
                }
            }
            else if (!received_text.equals(SURVEY) && this.userCommand.get(toIntExact(user_id)).equals(SURVEY)) {
                userPreferences.get(toIntExact(user_id)).getSurvey().setNextAnswer(received_text);

                if (!userPreferences.get(toIntExact(user_id)).getSurvey().isComplete()) {
                    message.setText(userPreferences.get(toIntExact(user_id)).getSurvey().getNextQuestion());

                    // Add keyboard to message
                    message.setReplyMarkup(HORmessages.setReplyKeyboard());
                } else {
                    message.setText("Questionario completato.");

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
                    message.setText(EmojiParser.parseToUnicode("Scegli tre attività che ti interessano \n\n" +
                            userPreferences.get(toIntExact(user_id)).getSurveyContext()));

                    // Add keyboard to message
                    message.setReplyMarkup(HORmessages.setInlineKeyboard(userPreferences.get(toIntExact(user_id)).getSurveyContext()));
                } else {
                    message.setText("Attività già scelte.");
                }

            }
            else if (received_text.equals(RESETCONTEXTS)) {
                userCommand.replace(toIntExact(user_id), RESETCONTEXTS);

                userPreferences.get(toIntExact(user_id)).getSurveyContext().resetCheckValues();
                message.setText("Attività resettate.");
            }
            // UNKNOWN COMMAND
            else {
                message.setText("Comando sconosciuto: " + received_text);
            }

            // Log message values
            logger.info(new HORLogger().logUserInfo(user_first_name, user_last_name, user_username, Long.toString(user_id)));

            try{
                // Send answer
                execute(message);

            } catch(TelegramApiException e){
                e.printStackTrace();
            }
        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            long user_id = update.getCallbackQuery().getMessage().getChat().getId();

            EditMessageText new_message;

            if (call_data.equals("send_contexts")) {
                new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText("Attività salvate.");

            } else {
                userPreferences.get(toIntExact(user_id)).getSurveyContext().setContextCheck(Integer.valueOf(call_data));

                new_message = new EditMessageText()
                        .setChatId(chat_id)
                        .setMessageId(toIntExact(message_id))
                        .setText(EmojiParser.parseToUnicode(userPreferences.get(toIntExact(user_id)).getSurveyContext().toString()));

                new_message.setReplyMarkup(HORmessages.setInlineKeyboard(userPreferences.get(toIntExact(user_id)).getSurveyContext()));
            }

            try {
                execute(new_message);
            } catch (TelegramApiException e) {
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
}