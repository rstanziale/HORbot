package telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class MyBots extends TelegramLongPollingBot{

    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){ // Controlliamo che il messaggio contenga un testo
            Long sender_id = update.getMessage().getChatId(); // Assegniamo ad una variabile l'Id della chat
            String received_text = update.getMessage().getText(); // Assegniamo ad una varibile il testo ricevuto
            String text_to_send = ""; // Inizializziamo una variabile che conterrà il messaggio da inviare

            SendMessage message = new SendMessage(); // Inizializziamo un'altra variabile per l'invio del messaggio

            text_to_send = received_text + " anche a te!"; // Questo è il messaggio

            message.setChatId(sender_id); // Settiamo l'Id della chat
            message.setText(text_to_send); // Settiamo il messaggio

            try{
                execute(message);
            }catch(TelegramApiException e){
                e.printStackTrace();
            }
        }
    }

    public String getBotUsername() {
        return "HOR_chatbot"; // Nome del bot
    }

    public String getBotToken() {
        return "606310301:AAEG0oCm-glhfCUOzvCks9piv5dC8YleFIY"; // Token del bot per lo sviluppo
    }
}