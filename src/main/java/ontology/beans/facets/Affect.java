
package ontology.beans.facets;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Define affects values from Myrror facets
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "date",
    "sentiment",
    "emotion"
})
public class Affect {

    @JsonProperty("date")
    private String date;
    @JsonProperty("sentiment")
    private Integer sentiment;
    @JsonProperty("emotion")
    private String emotion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("date")
    public String getDate() {
        return date;
    }

    @JsonProperty("date")
    public void setDate(String date) {
        this.date = date;
    }

    @JsonProperty("sentiment")
    public Integer getSentiment() {
        return sentiment;
    }

    @JsonProperty("sentiment")
    public void setSentiment(Integer sentiment) {
        this.sentiment = sentiment;
    }

    @JsonProperty("emotion")
    public String getEmotion() {
        return emotion;
    }

    @JsonProperty("emotion")
    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return "Affect{" +
                "date='" + date + '\'' +
                ", sentiment=" + sentiment +
                ", emotion='" + emotion + '\'' +
                ", additionalProperties=" + additionalProperties +
                '}';
    }
}
