package settings;

import com.vdurmont.emoji.EmojiParser;
import common.UserPreferences;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import recommender.contentBased.beans.Item;

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
    EditMessageText setMessage(long message_id,
                                      long chat_id,
                                      String call_data,
                                      UserPreferences userPreferences) {

        EditMessageText editMessageText = new EditMessageText()
                .setMessageId(toIntExact(message_id))
                .setChatId(chat_id);
        Item item;

        switch (call_data) {
            case HORCommands.SET_COMPANY:
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboardForCompany());
                break;

            case HORCommands.SET_RESTED:
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboardForRested());
                break;

            case HORCommands.SET_MOOD:
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboardForMood());
                break;

            case HORCommands.SET_ACTIVITY:
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboardForActivity());
                break;

            case HORCommands.SET_INTERESTS:
                String textInterests = HORMessages.MESSAGE_INTERESTS;
                if (userPreferences.getUserContext().getPreferencesMapped() != null) {
                    String interests = "";
                    for (String interest : userPreferences.getUserContext().getPreferencesMapped()) {
                        interests = interests.concat(" - " + interest + "\n");
                    }
                    textInterests = textInterests.concat("\n\n" + interests);
                }
                editMessageText.setText(userPreferences.getUserContext().toString() + '\n' + textInterests)
                        .setReplyMarkup(HORMessages.setInlineKeyboardForInterests());
                break;

            case HORCommands.CONTEXT_DONE:
                editMessageText.setText(userPreferences.getUserContext().toString());
                break;

            case HORCommands.FRIENDS:
                userPreferences.getUserContext().setCompany("Amici");
                userPreferences.addLabelToMyrrorUpdated("Company=" + "Amici");
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.FAMILY_PARTNER:
                userPreferences.getUserContext().setCompany("Famiglia/Fidanzata-o");
                userPreferences.addLabelToMyrrorUpdated("Company=" + "Famiglia/Fidanzata-o");
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.ASSOCIATES:
                userPreferences.getUserContext().setCompany("Colleghi");
                userPreferences.addLabelToMyrrorUpdated("Company=" + "Colleghi");
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.RESTED_TRUE:
                userPreferences.getUserContext()
                        .setRested(true);
                userPreferences.addLabelToMyrrorUpdated("Rested=" + true);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.RESTED_FALSE:
                userPreferences.getUserContext()
                        .setRested(false);
                userPreferences.addLabelToMyrrorUpdated("Rested=" + false);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.ACTIVITY_TRUE:
                userPreferences.getUserContext()
                        .setActivity(true);
                userPreferences.addLabelToMyrrorUpdated("Activity=" + true);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.ACTIVITY_FALSE:
                userPreferences.getUserContext()
                        .setActivity(false);
                userPreferences.addLabelToMyrrorUpdated("Activity=" + false);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.MOOD_TRUE:
                userPreferences.getUserContext()
                        .setMood(true);
                userPreferences.addLabelToMyrrorUpdated("Mood=" + true);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.MOOD_FALSE:
                userPreferences.getUserContext()
                        .setMood(false);
                userPreferences.addLabelToMyrrorUpdated("Mood=" + false);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.INTERESTS_TRUE:
                userPreferences.getUserContext()
                        .setInterestsUsed(true);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.INTERESTS_FALSE:
                userPreferences.getUserContext()
                        .setInterestsUsed(false);
                editMessageText.setText(userPreferences.getUserContext().toString())
                        .setReplyMarkup(HORMessages.setInlineKeyboard());
                break;

            case HORCommands.CONTENT_BASED:
                item = userPreferences.getRecommendAllPOI(1);
                item.setLiked(true);
                item.setInteractionTime(System.currentTimeMillis() - userPreferences.getStartRecommendTime());
                userPreferences.setRecommendPOI(item);
                editMessageText.setText(EmojiParser.parseToUnicode(
                        item.toString()));
                break;

            case HORCommands.CONTEXT_AWARE_PRE:
                item = userPreferences.getRecommendAllPOI(2);
                item.setLiked(true);
                item.setInteractionTime(System.currentTimeMillis() - userPreferences.getStartRecommendTime());
                userPreferences.setRecommendPOI(item);
                editMessageText.setText(EmojiParser.parseToUnicode(
                        item.toString()));
                break;

            case HORCommands.CONTEXT_AWARE_POST:
                item = userPreferences.getRecommendAllPOI(3);
                item.setLiked(true);
                item.setInteractionTime(System.currentTimeMillis() - userPreferences.getStartRecommendTime());
                userPreferences.setRecommendPOI(item);
                editMessageText.setText(EmojiParser.parseToUnicode(
                        item.toString()));
                break;

            case HORCommands.GRAPH_BASED:
                item = userPreferences.getRecommendAllPOI(4);
                item.setLiked(true);
                item.setInteractionTime(System.currentTimeMillis() - userPreferences.getStartRecommendTime());
                userPreferences.setRecommendPOI(item);
                editMessageText.setText(EmojiParser.parseToUnicode(
                        item.toString()));
                break;
        }
        return editMessageText;
    }
}
