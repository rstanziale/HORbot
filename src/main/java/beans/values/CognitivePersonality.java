package beans.values;

/**
 * Define a generic personality element for Cognitive Aspects facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class CognitivePersonality {
    private int openness;
    private int conscientiousness;
    private int extroversion;
    private int agreeableness;
    private int neuroticism;
    private int confidence;
    private String source;

    public int getOpenness() {
        return openness;
    }

    public void setOpenness(int openness) {
        this.openness = openness;
    }

    public int getConscientiousness() {
        return conscientiousness;
    }

    public void setConscientiousness(int conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    public int getExtroversion() {
        return extroversion;
    }

    public void setExtroversion(int extroversion) {
        this.extroversion = extroversion;
    }

    public int getAgreeableness() {
        return agreeableness;
    }

    public void setAgreeableness(int agreeableness) {
        this.agreeableness = agreeableness;
    }

    public int getNeuroticism() {
        return neuroticism;
    }

    public void setNeuroticism(int neuroticism) {
        this.neuroticism = neuroticism;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
