package beans.values;

/**
 * Define a generic element for Interest facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class InterestsValue {
    private String value;
    private int confidence;
    private String timestamp;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
