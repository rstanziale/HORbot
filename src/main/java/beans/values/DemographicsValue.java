package beans.values;

/**
 * Define a generic element for Demographics facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class DemographicsValue {
    private String value;
    private String source;
    private int confidence;
    private int timestamp;
    private String _id;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getConfidence() {
        return confidence;
    }

    public void setConfidence(int confidence) {
        this.confidence = confidence;
    }

    public int getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
