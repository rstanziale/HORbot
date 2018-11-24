package beans.survey;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Define SurveyContext class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class SurveyContext {

    private Map<Integer, Context> surveyContext;

    /**
     * Constructor of the survey context
     * @param filePath string representing the path of file
     */
    public SurveyContext(String filePath) {
        this.surveyContext = new HashMap<>();
        InputStream in = getClass().getResourceAsStream(filePath);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            int index = 1;

            for(String line; (line = br.readLine()) != null; ) {
                this.surveyContext.put(index, new Context(line));
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all contexts
     * @return a collection of Contexts
     */
    public Collection<Context> getSurveyContext() {
        return surveyContext.values();
    }

    /**
     * Check if the survey context is complete
     * @return true if the survey context has three checked items else false
     */
    public boolean isComplete() {
        int value = 0;

        for (Context c : this.surveyContext.values()) {
            if (c.isChecked()) {
                value++;
            }
        }

        return value == 3;
    }

    /**
     * Update context with the new checked value
     * @param index int representing the index of the context
     */
    public void setContextCheck(int index) {
        Context c = this.surveyContext.get(index);
        c.setChecked();
        this.surveyContext.replace(index, c);
    }

    /**
     * Reset check values if user wants to do survey context again
     */
    public void resetCheckValues() {
        for (Context c : surveyContext.values()) {
            c.resetChecked();
        }
    }

    @Override
    public String toString() {
        String s = "";
        int i = 1;

        for (Context c : surveyContext.values()) {
            s += String.valueOf(i) + ". " + c.toString() + "\n";
            i++;
        }
        return s;
    }
}
