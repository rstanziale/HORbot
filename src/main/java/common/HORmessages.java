package common;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import settings.LoggerInterface;

import java.util.ArrayList;
import java.util.List;

/**
 * Define HORMessages class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class HORmessages implements LoggerInterface {

    /**
     *
     * @param received_text message received from user
     * @return text message when command is /login
     */
    public static String messageLogin(String received_text) {
        String text;
        String login[] = received_text.split("\n");

        RequestHTTP r = new RequestHTTP();
        int res = r.userLogin(login[0].toLowerCase(), login[1]);

        logger.info("Request code: " + res);

        if (res == 200) {
            text = "Username trovato.";
        } else {
            text = "Username non trovato. Usa /login per riprovare.";
        }

        return text;
    }

    /**
     * Set keyboard for survey
     * @return keyboard for message with answer values
     */
    public static ReplyKeyboardMarkup setKeyboard() {

        // Set answers
        String[] answers = new String[4];
        answers[0] = "In disaccordo";
        answers[1] = "Parzialmente d'accordo";
        answers[2] = "Abbastanza d'accordo";
        answers[3] = "D'accordo";

        // Create ReplyKeyboardMarkup object
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        keyboardMarkup.setResizeKeyboard(true);

        // Create the keyboard (list of keyboard rows)
        List<KeyboardRow> keyboard = new ArrayList<>();

        // Create a keyboard row
        KeyboardRow row = new KeyboardRow();

        // Set each button, you can also use KeyboardButton objects if you need something else than text
        for (String answer : answers) {
            row.add(answer);
            keyboard.add(row);
            row = new KeyboardRow();
        }

        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }
}
