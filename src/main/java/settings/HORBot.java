package settings;

import common.HORLogger;
import common.PropertyUtilities;
import common.RequestHTTP;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Define HORBot class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class HORBot extends TelegramLongPollingBot implements LoggerInterface{

    // TELEGRAM COMMANDS
    private final static String START = "/start";
    private final static String SETUSERNAME = "/setusername";
    private String command = "UnknownCommand";

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

            // Set chat ID
            Long sender_id = update.getMessage().getChatId();

            // Set text received
            String received_text = update.getMessage().getText();

            // Set message structure
            SendMessage message = new SendMessage() // Create a message object object
                    .setChatId(sender_id);

            // START COMMAND
            if (received_text.equals(START)) {
                this.command = START;
                message.setText("Ciao, per cominciare fornisci il tuo username di Myrror attraverso il comando /setusername!");

            }
            // SETUSERNAME COMMAND
            else if (received_text.equals(SETUSERNAME)) {
                this.command = SETUSERNAME;
                message.setText("Inviami le credenziali secondo questo modello:\n\nemail\npassword");
            }
            else if (!received_text.equals(SETUSERNAME) && this.command.equals(SETUSERNAME)) {
                String email;
                String password;

                // TODO: check \n in message
                String m[] = received_text.split("\n");
                email = m[0];
                password = m[1];

                RequestHTTP r = new RequestHTTP();
                int res = r.userLogin(email, password);

                logger.info("Request code: " + res);

                if (res == 200) {
                    message.setText("Username trovato.");
                    this.command = "Unknown command";
                } else {
                    message.setText("Username non trovato. Clicca /setusername per riprovare");
                    this.command = "Unknown command";
                }
            }

            // UNKNOWN COMMAND
            else {
                message.setText("Comando sconosciuto");
            }

            // Log message values
            logger.info(new HORLogger().logUserInfo(user_first_name, user_last_name, user_username, Long.toString(user_id), received_text, message.getText()));

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