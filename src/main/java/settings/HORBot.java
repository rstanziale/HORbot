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
public class HORBot extends TelegramLongPollingBot implements LoggerInterface {

    // TELEGRAM COMMANDS
    private final static String START = "/start";
    private final static String LOGIN = "/login";
    private final static String HELP = "/help";
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
                message.setText("Ciao, per cominciare effettua il login per Myrror attraverso il comando /login!");

            }
            // SETUSERNAME COMMAND
            else if (received_text.equals(LOGIN)) {
                this.command = LOGIN;
                message.setText("Inviami le credenziali secondo questo modello:\n\nemail\npassword");
            }
            else if (!received_text.equals(LOGIN) && this.command.equals(LOGIN)) {
                String login[] = received_text.split("\n");

                RequestHTTP r = new RequestHTTP();
                int res = r.userLogin(login[0], login[1]);

                logger.info("Request code: " + res);

                if (res == 200) {
                    message.setText("Username trovato.");
                    this.command = "Comando sconosciuto";
                } else {
                    message.setText("Username non trovato. Usa /login per riprovare.");
                    this.command = "Comando sconosciuto";
                }
            }
            // HELP COMMAND
            else if (received_text.equals(HELP)) {
                message.setText("Puoi utilizzarmi con i seguenti comandi:\n\n/login - Effettua il login per Myrror\n/help - Informazioni sui comandi");
            }

            // UNKNOWN COMMAND
            else {
                message.setText("Comando sconosciuto");
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