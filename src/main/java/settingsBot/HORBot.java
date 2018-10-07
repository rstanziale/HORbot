package settingsBot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class HORBot extends TelegramLongPollingBot{

    /**
     * Get message from chat and send a new message
     * @param update message received
     */
    public void onUpdateReceived(Update update) {
        // Controlliamo che il messaggio contenga un testo
        if(update.hasMessage() && update.getMessage().hasText()) {
            // Assegniamo ad una variabile l'Id della chat
            Long sender_id = update.getMessage().getChatId();

            // Assegniamo ad una varibile il testo ricevuto
            String received_text = update.getMessage().getText();

            // Inizializziamo una variabile che conterrà il messaggio da inviare
            String text_to_send = "";

            // Inizializziamo un'altra variabile per l'invio del messaggio
            SendMessage message = new SendMessage();

            // Questo è il messaggio
            text_to_send = received_text + " anche a te!";

            // Settiamo l'Id della chat
            message.setChatId(sender_id);

            // Settiamo il messaggio
            message.setText(text_to_send);

            try{
                execute(message);
            }catch(TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * Get Bot Username
     * @return Bot Username
     */
    public String getBotUsername() {
        return "HOR_chatbot";
    }

    /**
     * Get Bot Token
     * @return Bot Token
     */
    public String getBotToken() {
        return "606310301:AAEG0oCm-glhfCUOzvCks9piv5dC8YleFIY";
    }
}