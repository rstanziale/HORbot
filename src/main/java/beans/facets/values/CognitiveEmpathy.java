package beans.facets.values;

/**
 * Define a generic empathy element for Cognitive Aspects facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class CognitiveEmpathy {
    private String timestamp;
    private int value;
    private int confidence;
    private String source;
    private String _id;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
