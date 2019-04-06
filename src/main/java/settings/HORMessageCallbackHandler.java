package settings;

import common.UserPreferences;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;

import static java.lang.Math.toIntExact;

/**
 * Define HOR message callback handler class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class HORMessageCallbackHandler {

    /**
     * Constructor of HORMessageCallbackHandler
     */
    public HORMessageCallbackHandler() {}

    /**
     * Set message to send to user
     * @param message_id representing message received
     * @param chat_id representing chat id from which the message came
     * @param call_data representing command of callback keyboard
     * @param userPreferences representing user preferences
     * @return a text message edited
     */
    public EditMessageText setMessage(long message_id,
                                      long chat_id,
                                      String call_data,
                                      UserPreferences userPreferences) {

        EditMessageText editMessageText = new EditMessageText()
                .setMessageId(toIntExact(message_id))
                .setChatId(chat_id);

        switch (call_data) {
            case "setcompany":
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboardForCompany());
                break;

            case "setrested":
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboardForRested());
                break;

            case "setmood":
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboardForMood());
                break;

            case "setactivity":
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboardForActivity());
                break;

            case "contextdone":
                editMessageText.setText(userPreferences.getUserContext().toString());
                break;

            case "friends":
                userPreferences.getUserContext().setCompany("Amici");
                userPreferences.addLabelToMyrrorUpdated("Company=" + "Amici");
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case "familypartner":
                userPreferences.getUserContext().setCompany("Famiglia/Fidanzata-o");
                userPreferences.addLabelToMyrrorUpdated("Company=" + "Famiglia/Fidanzata-o");
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case "associates":
                userPreferences.getUserContext().setCompany("Colleghi");
                userPreferences.addLabelToMyrrorUpdated("Company=" + "Colleghi");
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case "restedtrue":
                userPreferences.getUserContext()
                        .setRested(true);
                userPreferences.addLabelToMyrrorUpdated("Rested=" + true);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case "restedfalse":
                userPreferences.getUserContext()
                        .setRested(false);
                userPreferences.addLabelToMyrrorUpdated("Rested=" + false);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case "activitytrue":
                userPreferences.getUserContext()
                        .setActivity(true);
                userPreferences.addLabelToMyrrorUpdated("Activity=" + true);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case "activityfalse":
                userPreferences.getUserContext()
                        .setActivity(false);
                userPreferences.addLabelToMyrrorUpdated("Activity=" + false);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case "moodtrue":
                userPreferences.getUserContext()
                        .setMood(true);
                userPreferences.addLabelToMyrrorUpdated("Mood=" + true);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case "moodfalse":
                userPreferences.getUserContext()
                        .setMood(false);
                userPreferences.addLabelToMyrrorUpdated("Mood=" + false);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;
        }

        return editMessageText;
    }
}
