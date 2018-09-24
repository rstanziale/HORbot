import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class MyBots extends TelegramLongPollingBot {

    public void onUpdateReceived(Update update) { // Qui andiamo a scrivere tutte le cose che il bot dove fare quando arriva un messaggio
        // Cosa fare
    }

    @Override
    public String getBotUsername() { // Qui dobbiamo fare ritornare lo Username del bot
        // Cosa fare
        return null;
    }

    @Override
    public String getBotToken() {  // Qui dobbiamo far ritornare il Token per lo sviluppo
        // Cosa fare
        return null;
    }

}