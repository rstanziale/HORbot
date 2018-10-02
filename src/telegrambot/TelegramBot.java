package telegrambot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class TelegramBot {

    public static void main(String[] args) {

        ApiContextInitializer.init(); //Inizializza le api per poter sviluppare

        TelegramBotsApi botsApi = new TelegramBotsApi(); //Crea il nostro bot

        try {
            botsApi.registerBot(new MyBots()); //Registra il bot con gli attributi che abbiamo dato
        } catch (TelegramApiRequestException e) {
            e.printStackTrace();
        }
    }
}