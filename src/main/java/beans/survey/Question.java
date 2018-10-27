package beans.survey;

/**
 * Define Question class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class Question {
    private String question;
    private int answer;

    /**
     * Constructor of a question and answer
     * @param question representing the question of the survey
     * @param answer representing the answer of the survey
     */
    public Question(String question, int answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Get the question
     * @return the question
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Get the answer of the question
     * @return the answer of the question
     */
    public int getAnswer() {
        return answer;
    }

    /**
     * Set the answer of the question
     * @param answer of the question
     */
    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Q: '" + question + '\'' +
                "\nA: " + answer + '\n';
    }
}
