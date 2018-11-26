package common;

import beans.survey.SurveyContext;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
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
     * Set the message login answer
     * @param received_text message received from user
     * @param userPreferences user preferences obtained by user interaction
     * @return text message when command is /login
     */
    public static String messageLogin(String received_text, UserPreferences userPreferences) {
        String text;
        String login[] = received_text.split("\n");

        RequestHTTP r = new RequestHTTP();
        int res = r.userLogin(login[0].toLowerCase(), login[1]);

        logger.info("Request code: " + res);

        if (res == 200) {
            text = "Username trovato.";
            userPreferences.setOntology(r.getOntology());
        } else {
            text = "Username non trovato. Usa /login per riprovare.";
        }

        return text;
    }

    /**
     * Set keyboard for survey
     * @return keyboard for message with answer values
     */
    public static ReplyKeyboardMarkup setReplyKeyboard() {

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

    /**
     * Set keyboard for survey  contexts
     * @param surveyContext SurveyContext representing the information about contexts chosen by user
     * @return keyboard for message with context indexes
     */
    public static InlineKeyboardMarkup setInlineKeyboard(SurveyContext surveyContext) {
        // Create ReplyKeyboardMarkup object
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        // Create the keyboard (list of keyboard rows)
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        // Create a keyboard row
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        for (int i = 0; i < surveyContext.getSurveyContext().size(); i++) {
            // Create a new line each five buttons
            if (i % 5 == 0) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }

            // Add button to rowline
            rowInline.add(new InlineKeyboardButton()
                    .setText(String.valueOf(i+1))
                    .setCallbackData(String.valueOf(i+1)));
        }
        rowsInline.add(rowInline);
        rowInline = new ArrayList<>();

        if (surveyContext.isComplete()) {
            rowInline.add(new InlineKeyboardButton()
                    .setText("Salva attività")
                    .setCallbackData("send_contexts"));
            rowsInline.add(rowInline);
        }

        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }
}
