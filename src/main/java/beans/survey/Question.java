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

    public Question(String question, int answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "'Q: " + question + '\'' +
                "\nA: " + answer + '\n';
    }
}
