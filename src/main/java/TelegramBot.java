import settings.HORBot;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 * Define Main class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class TelegramBot {
    public static void main(String[] args) {

        // Inizializza le api per poter sviluppare
        ApiContextInitializer.init();

        // Crea il bot
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            // Registra il bot con gli attributi dati
            botsApi.registerBot(new HORBot());
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}