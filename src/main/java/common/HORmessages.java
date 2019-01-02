package common;

import beans.survey.Context;
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

    // HOR MESSAGES
    public static String MESSAGE_START = "Ciao, per cominciare effettua il login per Myrror attraverso il comando /login!";
    public static String MESSAGE_LOGIN = "Inviami le credenziali secondo questo modello:\n\nemail\npassword";
    public static String MESSAGE_LOGIN_COMPLETE = "Inizia il questionario con il comando /survey.";
    public static String MESSAGE_HELP = "Puoi utilizzarmi con i seguenti comandi:\n\n" +
            "/login - Effettua il login per Myrror\n" +
            "/survey - Inizia il questionario\n" +
            "/showanswer - Visualizza le risposte del questionario\n" +
            "/resetanswer - Reimposta le risposte del questionario\n" +
            "/help - Informazioni sui comandi";
    public static String MESSAGE_POSITION = "Inviami la tua posizione";
    public static String MESSAGE_POSITION_SAVED = "Posizione salvata.";
    public static String MESSAGE_SURVEY_START = "Indica quanto sei d'accordo con le seguenti affermazioni \\n\\n";
    public static String MESSAGE_SURVEY_ALREADY_COMPLETE = "Questionario già completato.";
    public static String MESSAGE_SURVEY_COMPLETE = "Questionario completato.";
    public static String MESSAGE_SURVEY_RESET = "Risposte del questionario reimpostate.";
    public static String MESSAGE_ACTIVITIES_CHOSEN = "Attività già scelte.";
    public static String MESSAGE_ACTIVITIES_RESET = "Attività resettate.";
    public static String MESSAGE_ACTIVITIES_SAVED = "Attività salvate.";
    public static String MESSAGE_ACTIVITIES_SAVED_WITH_CONTEXTS = "Attività salvate. Continua con gli altri contesti con ";
    public static String UNKNOWN_COMMAND = "Commando sconosciuto: ";

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
     * Set keyboard for survey contexts
     * @param context Context representing the information about contexts chosen by user
     * @return keyboard for message with context indexes
     */
    public static InlineKeyboardMarkup setInlineKeyboard(Context context) {
        // Create ReplyKeyboardMarkup object
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();

        // Create the keyboard (list of keyboard rows)
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();

        // Create a keyboard row
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        for (int i = 0; i < context.getActivities().size(); i++) {
            // Create a new line each five buttons
            if (i % 5 == 0) {
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }

            // Add button to rowline
            rowInline.add(new InlineKeyboardButton()
                    .setText(String.valueOf(i + 1))
                    .setCallbackData(String.valueOf(i + 1)));
        }
        rowsInline.add(rowInline);
        rowInline = new ArrayList<>();

        if (context.isComplete()) {
            rowInline.add(new InlineKeyboardButton()
                    .setText("Salva attività")
                    .setCallbackData("send_contexts"));
            rowsInline.add(rowInline);
        }

        // Add it to the message
        markupInline.setKeyboard(rowsInline);

        return markupInline;
    }

    public void setActivityFlags(Context context, String[] values) {
        // TODO: add controls on values and single value
        for (String value : values) {
            context.setActivityCheck(Integer.valueOf(value));
        }
    }
}
