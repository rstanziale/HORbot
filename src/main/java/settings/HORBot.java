package settings;

import common.UserPreferences;
import common.Utils;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
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

    // MESSAGE HANDLER
    private HORMessageHandler messageHandler = new HORMessageHandler();
    private HORMessageCallbackHandler messageCallbackHandler = new HORMessageCallbackHandler();

    // USER PREFERENCES
    private Map<Integer, UserPreferences> userPreferences = new HashMap<>();

    // ADMIN STATE
    private String adminCommand;
    private int configuration = 0;

    /**
     * Get message from chat and send a new message
     * @param update message received
     */
    public void onUpdateReceived(Update update) {
        // Check text message
        if(update.hasMessage() && update.getMessage().hasText() ||
                update.hasMessage() && update.getMessage().hasLocation()) {

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

                this.messageHandler.initializeUser(user_id);
                this.userPreferences.get(toIntExact(user_id)).setConfiguration(this.configuration);
            }

            // Set chat ID
            Long sender_id = update.getMessage().getChatId();

            // Log message values
            logger.info(new HORLogger().logUserInfo(user_first_name,
                    user_last_name,
                    user_username,
                    Long.toString(user_id)));

            Object message;
            SendMessage sendMessage;
            SendDocument sendDocument;
            if (update.getMessage() != null) {
                if (update.getMessage() != null &&
                        update.getMessage().getText() != null &&
                        update.getMessage().getText().equals(HORCommands.SET_CONF)) {
                    this.adminCommand = HORCommands.SET_CONF;
                    message = new SendMessage();
                    ((SendMessage) message).setText(HORMessages.SET_CONFIGURATION);
                } else if (update.getMessage() != null &&
                        this.adminCommand != null &&
                        this.adminCommand.equals(HORCommands.SET_CONF)) {
                    this.configuration = Integer.valueOf(update.getMessage().getText());
                    for (long user : this.userPreferences.keySet()) {
                        this.userPreferences.get(toIntExact(user)).setConfiguration(this.configuration);
                    }
                    message = new SendMessage();
                    this.adminCommand = "unknown";
                    ((SendMessage) message).setText("Configurazione impostata.");
                } else if (update.getMessage() != null &&
                        update.getMessage().getText() != null &&
                        update.getMessage().getText().equals(HORCommands.CONF)) {
                    this.adminCommand = HORCommands.CONF;
                    message = new SendMessage();
                    ((SendMessage) message).setText(String.valueOf(this.configuration));
                } else {
                    message = messageHandler.setMessage(user_id,
                            userPreferences.get(toIntExact(user_id)),
                            update.getMessage());
                }

                try {
                    // Send answer
                    if (message instanceof SendMessage) {
                        sendMessage = (SendMessage) message;
                        sendMessage.setChatId(sender_id);
                        execute(sendMessage);
                    } else {
                        sendDocument = (SendDocument) message;
                        sendDocument.setChatId(sender_id);
                        switch (sendDocument.getCaption()) {
                            case Utils.CAPTION_LOGFILE:
                                sendDocument.setDocument(Utils.createLogFile(userPreferences));
                                break;
                            case Utils.CAPTION_PREFERENCES_LOGFILE:
                                sendDocument.setDocument(Utils.createPreferencesLogFile(userPreferences));
                                break;
                            case Utils.CAPTION_SURVEY_LOGFILE:
                                sendDocument.setDocument(Utils.createSurveyLogFile(userPreferences));
                                break;
                        }
                        execute(sendDocument);
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        } else if (update.hasCallbackQuery()) {
            long user_id = update.getCallbackQuery().getFrom().getId();
            long chat_id = update.getCallbackQuery().getMessage().getChatId();
            long message_id = update.getCallbackQuery().getMessage().getMessageId();
            String user_first_name = update.getCallbackQuery().getFrom().getFirstName();
            String user_last_name = update.getCallbackQuery().getFrom().getLastName();
            String user_username = update.getCallbackQuery().getFrom().getUserName();
            String call_data = update.getCallbackQuery().getData();

            try {
                // Log message values
                logger.info(new HORLogger().logUserInfo(user_first_name,
                        user_last_name,
                        user_username,
                        Long.toString(user_id)));

                execute(this.messageCallbackHandler.setMessage(message_id,
                        chat_id,
                        call_data,
                        this.userPreferences.get(toIntExact(user_id))));
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
        return Utils.getProperty("username");
    }

    /**
     * Get Bot Token
     * @return Bot Token
     */
    public String getBotToken() {
        return Utils.getProperty("token");
    }
}