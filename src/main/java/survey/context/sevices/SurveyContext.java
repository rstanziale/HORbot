package survey.context.sevices;

import survey.context.beans.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
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
     * @param contextsPath string representing the path of file
     * @param activitiesPath String representing the path of file
     */
    public SurveyContext(String contextsPath, String activitiesPath) {
        this.surveyContext = new HashMap<>();
        InputStream in = getClass().getResourceAsStream(contextsPath);

        try(BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            int index = 1;

            for(String line; (line = br.readLine()) != null; ) {
                this.surveyContext.put(index, new Context(line, activitiesPath));
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
    public Collection<Context> getSurveyValues() {
        return surveyContext.values();
    }

    /**
     * Get next context incomplete
     * @return Context incomplete
     */
    public Context getNextContext() {
        Context c;
        int index = 1;
        boolean value = true;

        do {
            c = this.surveyContext.get(index);

            if (!c.isComplete()) {
                value = false;
            }

            index++;
        } while (index <= this.surveyContext.values().size() && value);

        return c;
    }

    /**
     * Get activities for each context
     * @return String representing chosen activities for each context
     */
    public String showContextChosen() {
        String s = "";

        for (Context c : surveyContext.values()) {
            s = s.concat(c.getContextName() + ":\n" +
                    c.showActivitiesChosen() +
                    "\n\n");
        }
        return s;
    }

    /**
     * Check if all the contexts are completed
     * @return boolean representing if the survey context are completed
     */
    public boolean isComplete() {
        boolean value = true;

        Iterator<Context> itr = surveyContext.values().iterator();

        while(itr.hasNext() && value) {
            Context element = itr.next();
            if (!element.isComplete()) {
                value = false;
            }
        }

        return value;
    }
}
