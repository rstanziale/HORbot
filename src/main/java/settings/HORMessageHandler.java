package settings;

import com.vdurmont.emoji.EmojiParser;
import common.UserPreferences;
import common.Utils;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import survey.context.beans.Context;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Math.toIntExact;

/**
 * Define HOR message handler class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class HORMessageHandler {

    // USER COMMAND
    private Map<Integer, String> userCommand;

    /**
     * Constructor of HORMessageHandler
     */
    public HORMessageHandler() {
        this.userCommand = new HashMap<>();
    }

    /**
     * Initialize user command
     * @param user_id representing user id
     */
    public void initializeUser(long user_id) {
        this.userCommand.put(toIntExact(user_id), "unknown");
    }

    /**
     * Set message to send to user
     * @param user_id representing user id
     * @param userPreferences representing user preferences
     * @param received_text representing received text from user
     */
    public Object setMessage(long user_id,
                                            UserPreferences userPreferences,
                                            String received_text) {
        SendMessage message = new SendMessage();
        SendDocument sendDocument = new SendDocument();

        switch (received_text) {
            case HORcommands.START:
                this.userCommand.replace(toIntExact(user_id), HORcommands.START);
                message.setText(HORmessages.MESSAGE_START);
                break;
            case HORcommands.LOGIN:
                break;
            case HORcommands.SURVEY:
                break;
            case HORcommands.SET_LOCATION:
                break;
            case HORcommands.SET_CONTEXTS:
                break;
            case HORcommands.SHOWANSWER:
                userCommand.replace(toIntExact(user_id), HORcommands.SHOWANSWER);
                message.setText(userPreferences.getSurvey().toString());
                break;

            case HORcommands.SHOWCONTEXTS:
                userCommand.replace(toIntExact(user_id), HORcommands.SHOWCONTEXTS);
                message.setText(EmojiParser.parseToUnicode(userPreferences.getSurveyContext().showContextChosen()));
                break;

            case HORcommands.RESETANSWER:
                userCommand.replace(toIntExact(user_id), HORcommands.RESETANSWER);
                userPreferences.getSurvey().resetAnswers();
                message.setText(HORmessages.MESSAGE_SURVEY_RESET);
                break;

            case HORcommands.RESETCONTEXTS:
                userCommand.replace(toIntExact(user_id), HORcommands.RESETCONTEXTS);

                for (Context c : userPreferences.getSurveyContext().getSurveyValues()) {
                    c.resetCheckValues();
                }
                message.setText(HORmessages.MESSAGE_ACTIVITIES_RESET);
                break;

            case HORcommands.GETRECOMMEND:
                break;
            case HORcommands.SETCOMPANY:
                break;
            case HORcommands.SETRESTED:
                break;
            case HORcommands.SETMOOD:
                break;
            case HORcommands.SETACTIVITY:
                break;
            case HORcommands.HELP:
                userCommand.replace(toIntExact(user_id), HORcommands.HELP);
                message.setText(HORmessages.MESSAGE_HELP);
                break;
            case HORcommands.LOGFILE:
                this.sendDocUploadingAFile(sendDocument, user_id,
                        Utils.createLogFile(user_id, userPreferences));
                break;
            default:
                message.setText(HORmessages.UNKNOWN_COMMAND + received_text);
                break;
        }
        switch (this.userCommand.get(toIntExact(user_id))) {
            case HORcommands.LOGIN:
                break;
            case HORcommands.HELP:
                break;
            case HORcommands.RESETANSWER:
                break;
            case HORcommands.SETACTIVITY:
                break;
        }
        if (sendDocument.getDocument() != null) {
            return sendDocument;
        } else {
            return message;
        }
    }

    /**
     * Generate request for send a preferences log file to user
     * @param sendDocumentRequest representing document to send to user
     * @param chatId representing chat user id
     * @param logFile representing the file to send user
     */
    private void sendDocUploadingAFile(SendDocument sendDocumentRequest, Long chatId, File logFile) {
        sendDocumentRequest.setChatId(chatId);
        sendDocumentRequest.setDocument(logFile);
        sendDocumentRequest.setCaption("Users log file.");
    }
}
