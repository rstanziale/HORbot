package settings;

import com.vdurmont.emoji.EmojiParser;
import common.UserPreferences;
import ontology.services.RequestHTTP;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import survey.context.beans.Context;

import java.util.*;

/**
 * Define HORMessages class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class HORMessages implements LoggerInterface {

    // HOR MESSAGES
    public static int THRESHOLD = 2500;
    public static String CSV_SPLIT = ",";
    public static Map<String, String> TAGS;
    static String MESSAGE_START = "Ciao, per cominciare effettua il login per Myrror attraverso il comando /login " +
            "oppure crea il tuo contesto con il comando /setcontext!";
    static String MESSAGE_LOGIN = "Inviami le credenziali secondo questo modello:\n\nemail\npassword";
    static String MESSAGE_LOGIN_COMPLETE = "Login completato, visualizza il tuo contesto con /showcontext";
    static String MESSAGE_CONTEXT_START = "Per impostare il tuo contesto usa questi comandi:\n" +
            " - /setcompany\n - /setrested\n - /setmood\n - /setactivity\n - /setinterests";
    static String MESSAGE_CONTEXT_EDIT = "Per modificare il tuo contesto usa il comando /setcontext";
    static String MESSAGE_HELP = "Puoi utilizzarmi con i seguenti comandi:\n\n" +
            "/login - Effettua il login per Myrror\n" +
            "/setlocation - Invia la posizione\n" +
            "/setcontext - Imposta il tuo contesto attuale\n" +
            "/buildprofile - Imposta le tue preferenze\n" +
            "/showanswer - Visualizza le risposte del questionario\n" +
            "/showprofile - Visualizza le attività scelte\n" +
            "/showcontext - Visualizza il tuo contesto\n" +
            "/resetanswer - Reimposta le risposte del questionario\n" +
            "/resetprofile - Reimposta il tuo profilo\n" +
            "/recommend - Ottieni un suggerimento di attività da svolgere";
    static String MESSAGE_COMPANY = "Con chi sei?";
    static String MESSAGE_RESTED = "Ti senti riposato?";
    static String MESSAGE_ACTIVITY = "Hai fatto molta attività fisica?";
    static String MESSAGE_INTERESTS = "Vuoi usare gli interessi caricati da Myrror?";
    static String MESSAGE_MOOD = "Di che umore sei?";
    static String MESSAGE_CONTEXT_UPDATE = "Il tuo contesto è stato aggiornato.";
    static String MESSAGE_CONTEXT_ERROR = "Il valore da inserito non è corretto.";
    static String MESSAGE_POSITION = "Inviami la tua posizione";
    static String MESSAGE_POSITION_SAVED = "Posizione salvata.";
    static String MESSAGE_SURVEY_START = "Indica quanto sei d'accordo con le seguenti affermazioni \\n\\n";
    static String MESSAGE_SURVEY_ALREADY_COMPLETE = "Questionario già completato.";
    static String MESSAGE_SURVEY_COMPLETE = "Questionario completato.";
    static String MESSAGE_SURVEY_RESET = "Risposte del questionario reimpostate.";
    static String MESSAGE_ACTIVITIES_CHOSEN = "Attività già scelte.";
    static String MESSAGE_ACTIVITIES_ERROR = "Errore nell'input delle attività.";
    static String MESSAGE_ACTIVITIES_RESET = "Attività resettate.";
    static String MESSAGE_ACTIVITIES_SAVED = "Attività salvate.";
    static String MESSAGE_REFERENCES_NON_COMPLETE = "Impossibile suggerire un evento, " +
            "controlla se hai impostato il tuo contesto (/setcontext) e fornito la tua posizione (/setlocation)" +
            " e/o preferenze (/buildprofile).";
    static String MESSAGE_NO_ACTIVITY = "Nessuna attività da suggerire.";
    static String MESSAGE_ITEM_VOTED = "Item votato.";
    static String MESSAGE_MISSING_COMPANY = "Non so con chi ti trovi, per dirmelo usa il comando /setcompany";
    static String MESSAGE_MISSING_RESTED = "Non so se sei riposato, per dirmelo usa il comando /setrested";
    static String MESSAGE_MISSING_MOOD = "Non so di che umore sei, per dirmelo usa il comando /setmood";
    static String MESSAGE_MISSING_ACTIVITY = "Non so se hai fatto attività, per dirmelo usa il comando /setactivity";
    static String UNKNOWN_COMMAND = "Commando sconosciuto: ";

    static {
        TAGS = createMap();
    }

    private static Map<String, String> createMap() {
        Map<String, String> myMap = new HashMap<>();

        myMap.put("Mangiare un gelato", "Yogurterie Gelaterie Dolci Pasticcerie");
        myMap.put("Mangiare in pizzeria", "Pizzerie Panzerotti Panini Sandwich Friggitorie");
        myMap.put("Mangiare street food", "Hamburger Food Truck Panini Sandwich Panzerotti");
        myMap.put("Mangiare in un ristorante italiano", "Piatti a base di pesce, Cucina italiana, Cucina mediterranea");
        myMap.put("Mangiare in un ristorante etnico (giapponese, etc.)", "Cucina cinese Cucina giapponese sushi cucina messicana");
        myMap.put("Andare al cinema", "Cinema Teatri");
        myMap.put("Andare a un concerto", "Cucina irlandese Arene Sale da concerto Pub");
        myMap.put("Andare a fare un aperitivo", "Cocktail bar Wine bar Caffetteria Caffè Panini Sandwich");
        myMap.put("Andare in un pub", "Pub Cucina irlandese Friggitorie Hamburger");
        myMap.put("Andare in discoteca", "Arene Sale da concerto Discoteche");
        myMap.put("Andare a teatro", "Opera Danza classica Arene Sale da concerto Teatri");
        myMap.put("Andare in palestra", "Istruttori e personal trainer Palestre Arti marziali Fitness");
        myMap.put("Andare al parco", "Parchi di divertimento Luna park Parchi");
        myMap.put("Andare in libreria", "Librerie Giornali e riviste");
        myMap.put("Restare a casa", "");

        return myMap;
    }

    /**
     * Set the message login answer
     *
     * @param received_text   message received from user
     * @param userPreferences user preferences obtained by user interaction
     * @return text message when command is /login
     */
    static String messageLogin(String received_text, UserPreferences userPreferences) {
        String text;
        String[] login = received_text.split("\n");

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
     * Check if the answer for set context company is correct
     * @param value user answer
     * @return boolean flag
     */
    static boolean checkContextCompany(String value) {
        return value.equals("Amici") || value.equals("Famiglia/Fidanzata-o") || value.equals("Colleghi");
    }

    /**
     * Check if the answer for set context rested or activity is correct
     * @param value user answer
     * @return boolean flag
     */
    static boolean checkContextBoolean(String value) {
        return value.equals("Sì") || value.equals("No");
    }

    /**
     * Check if the answer for set context mood is correct
     * @param value user answer
     * @return boolean flag
     */
    static boolean checkContextMood(String value) {
        return value.equals("Buon umore") || value.equals("Cattivo umore");
    }

    /**
     * Set keyboard for survey
     *
     * @return keyboard for message with answer values
     */
    static ReplyKeyboardMarkup setReplyKeyboardForSurvey() {

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
     * Set keyboard for missing context company
     *
     * @return keyboard for message with answer values
     */
    static ReplyKeyboardMarkup setReplyKeyboardForCompany() {

        // Set answers
        String[] answers = new String[3];
        answers[0] = "Amici";
        answers[1] = "Famiglia/Fidanzata-o";
        answers[2] = "Colleghi";

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
     * Set keyboard for missing context mood
     *
     * @return keyboard for message with answer values
     */
    static ReplyKeyboardMarkup setReplyKeyboardForMood() {

        // Set answers
        String[] answers = new String[2];
        answers[0] = "Buon umore";
        answers[1] = "Cattivo umore";

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
        }
        keyboard.add(row);

        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    /**
     * Set keyboard for missing context rested
     *
     * @return keyboard for message with answer values
     */
    static ReplyKeyboardMarkup setReplyKeyboardBoolean() {

        // Set answers
        String[] answers = new String[2];
        answers[0] = "Sì";
        answers[1] = "No";

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
        }
        keyboard.add(row);

        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    /**
     * Set keyboard for evaluate item
     *
     * @return keyboard for message with answer values
     */
    static ReplyKeyboardMarkup setReplyKeyboardLike() {

        // Set answers
        String[] answers = new String[2];
        answers[0] = EmojiParser.parseToUnicode(":thumbsup:");
        answers[1] = EmojiParser.parseToUnicode(":thumbsdown:");

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
        }
        keyboard.add(row);

        // Set the keyboard to the markup
        keyboardMarkup.setKeyboard(keyboard);

        return keyboardMarkup;
    }

    /**
     * Set activity flags for the context taken input according input controls
     *
     * @param context representing the Context in exam
     * @param values  representing the values taken from user
     * @return true if there is no problem, else false
     */
    static boolean setActivityFlags(Context context, String[] values) {
        boolean check = true;

        for (int i = 0; i < values.length && values.length == 3; i++) {
            try {
                int value = Integer.parseInt(values[i]);

                if (value > 0 && value < 16) {
                    context.setActivityCheck(value);
                } else {
                    check = false;
                    context.resetCheckValues();
                }
            } catch (NumberFormatException e) {
                check = false;
                context.resetCheckValues();
            }
        }
        return check;
    }
}
