package settings;

import common.HORLogger;
import common.HORmessages;
import common.PropertyUtilities;
import common.Survey;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.HashMap;
import java.util.Map;

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
    private final static String SHOWANSWER = "/showanswer";
    private final static String RESETANSWER = "/resetanswer";
    private final static String HELP = "/help";

    // USER COMMAND
    private Map<Integer, String> userCommand = new HashMap<>();

    // SURVEYS
    private Map<Integer, Survey> surveys = new HashMap<>();

    /**
     * Get message from chat and send a new message
     * @param update message received
     */
    public void onUpdateReceived(Update update) {
        // Check text message
        if(update.hasMessage() && update.getMessage().hasText()) {

            // Set user info for logging
            String user_first_name = update.getMessage().getChat().getFirstName();
            String user_last_name = update.getMessage().getChat().getLastName();
            String user_username = update.getMessage().getChat().getUserName();
            long user_id = update.getMessage().getChat().getId();

            // Survey for this user
            if (!surveys.containsKey((int)(long) user_id)) {
                surveys.put((int)(long) user_id, new Survey("/questions.txt"));
                userCommand.put((int)(long) user_id, "Comando sconosciuto");
            }

            // Set chat ID
            Long sender_id = update.getMessage().getChatId();

            // Set text received
            String received_text = update.getMessage().getText();

            // Set message structure
            SendMessage message = new SendMessage()
                    .setChatId(sender_id);

            // START COMMAND
            if (received_text.equals(START)) {
                userCommand.replace((int)(long) user_id, START);
                message.setText("Ciao, per cominciare effettua il login per Myrror attraverso il comando /login!");
            }
            // SHOW ANSWER COMMAND
            else if (received_text.equals(SHOWANSWER)) {
                userCommand.replace((int)(long) user_id, SHOWANSWER);
                message.setText(surveys.get((int)(long) user_id).toString());
            }
            // RESET ANSWERS
            else if (received_text.equals(RESETANSWER)) {
                userCommand.replace((int)(long) user_id, RESETANSWER);
                surveys.get((int)(long) user_id).resetAnswers();
                message.setText("Risposte del questionario reimpostate.");
            }
            // HELP COMMAND
            else if (received_text.equals(HELP)) {
                userCommand.replace((int)(long) user_id, HELP);
                message.setText("Puoi utilizzarmi con i seguenti comandi:\n\n" +
                        "/login - Effettua il login per Myrror\n" +
                        "/survey - Inizia il questionario\n" +
                        "/showanswer - Visualizza le risposte del questionario\n" +
                        "/resetanswer - Reimposta le risposte del questionario\n" +
                        "/help - Informazioni sui comandi");
            }
            // LOGIN COMMAND
            else if (received_text.equals(LOGIN)) {
                userCommand.replace((int)(long) user_id, LOGIN);
                message.setText("Inviami le credenziali secondo questo modello:\n\nemail\npassword");
            }
            else if (!received_text.equals(LOGIN) && this.userCommand.get((int)(long) user_id).equals(LOGIN)) {
                userCommand.replace((int)(long) user_id, "Comando sconosciuto");
                message.setText(HORmessages.messageLogin(received_text));
            }
            // SURVEY COMMAND
            else if (received_text.equals(SURVEY)) {
                userCommand.replace((int)(long) user_id, SURVEY);

                if (!surveys.get((int)(long) user_id).isComplete()) {
                    message.setText(surveys.get((int)(long) user_id).getNextQuestion());

                    // Add keyboard to message
                    message.setReplyMarkup(HORmessages.setKeyboard());
                } else {
                    message.setText("Questionario già completato.");
                }
            }
            else if (!received_text.equals(SURVEY) && this.userCommand.get((int)(long) user_id).equals(SURVEY)) {
                surveys.get((int)(long) user_id).setNextAnswer(received_text);

                if (!surveys.get((int)(long) user_id).isComplete()) {
                    message.setText(surveys.get((int)(long) user_id).getNextQuestion());

                    // Add keyboard to message
                    message.setReplyMarkup(HORmessages.setKeyboard());
                } else {
                    message.setText("Questionario completato.");

                    // Remove keyboard from message
                    ReplyKeyboardRemove keyboardMarkup = new ReplyKeyboardRemove();
                    message.setReplyMarkup(keyboardMarkup);
                    userCommand.replace((int)(long) user_id, "Comando sconosciuto");
                }
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