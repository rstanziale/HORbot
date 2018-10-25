package common;

import beans.survey.Question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Define Survey class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class Survey {
    private Map<Integer, Question> survey;

    /**
     * Constructor of the survey
     * @param filePath string representing the path of file
     */
    public Survey(String filePath) {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource(filePath).getFile());

        this.survey = new HashMap<>();

        try(BufferedReader br = new BufferedReader(new FileReader(file))) {
            int index = 0;

            for(String line; (line = br.readLine()) != null; ) {
                this.survey.put(index, new Question(line, 0));
                index++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all questions
     * @return a collection of Q&A
     */
    public Collection<Question> getSurvey() {
        return survey.values();
    }

    /**
     * Check if the survey is complete
     * @return true if the survey is complete else false
     */
    public boolean isComplete() {
        boolean value = true;

        for (Question q : this.survey.values()) {
            if (q.getAnswer() == 0) {
                value = false;
            }
        }

        return value;
    }

    /**
     * Get next question without answer
     * @return question without answer
     */
    public String getNextQuestion() {
        Question q;
        int index = 0;
        boolean value = true;

        do {
            q = this.survey.get(index);

            if (q.getAnswer() == 0) {
                value = false;
            }

            index++;
        } while (index < this.survey.values().size() && value);

        return q.getQuestion();
    }

    /**
     * Set next answer
     * @param answer of first question without answer
     */
    public void setNextAnswer(String answer) {
        Question q;
        int index = 0;
        boolean value = true;

        do {
            q = this.survey.get(index);

            if (q.getAnswer() == 0) {
                q.setAnswer(Integer.valueOf(answer));
                value = false;
            }

            index++;
        } while (index < this.survey.values().size() && value);
    }

    @Override
    public String toString() {
        String s = "";
        for (Question q : survey.values()) {
            s += q.toString() + "\n";
        }
        return s;
    }
}
