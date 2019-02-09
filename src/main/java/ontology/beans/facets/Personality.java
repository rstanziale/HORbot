
package ontology.beans.facets;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Define personality values from Myrror facets
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "timestamp",
    "openness",
    "conscientiousness",
    "extroversion",
    "agreeableness",
    "neuroticism",
    "confidence",
    "source",
    "_id"
})
public class Personality {

    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("openness")
    private Double openness;
    @JsonProperty("conscientiousness")
    private Double conscientiousness;
    @JsonProperty("extroversion")
    private Double extroversion;
    @JsonProperty("agreeableness")
    private Double agreeableness;
    @JsonProperty("neuroticism")
    private Double neuroticism;
    @JsonProperty("confidence")
    private Double confidence;
    @JsonProperty("source")
    private String source;
    @JsonProperty("_id")
    private String id;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("timestamp")
    public Long getTimestamp() {
        return timestamp;
    }

    @JsonProperty("timestamp")
    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    @JsonProperty("openness")
    public Double getOpenness() {
        return openness;
    }

    @JsonProperty("openness")
    public void setOpenness(Double openness) {
        this.openness = openness;
    }

    @JsonProperty("conscientiousness")
    public Double getConscientiousness() {
        return conscientiousness;
    }

    @JsonProperty("conscientiousness")
    public void setConscientiousness(Double conscientiousness) {
        this.conscientiousness = conscientiousness;
    }

    @JsonProperty("extroversion")
    public Double getExtroversion() {
        return extroversion;
    }

    @JsonProperty("extroversion")
    public void setExtroversion(Double extroversion) {
        this.extroversion = extroversion;
    }

    @JsonProperty("agreeableness")
    public Double getAgreeableness() {
        return agreeableness;
    }

    @JsonProperty("agreeableness")
    public void setAgreeableness(Double agreeableness) {
        this.agreeableness = agreeableness;
    }

    @JsonProperty("neuroticism")
    public Double getNeuroticism() {
        return neuroticism;
    }

    @JsonProperty("neuroticism")
    public void setNeuroticism(Double neuroticism) {
        this.neuroticism = neuroticism;
    }

    @JsonProperty("confidence")
    public Double getConfidence() {
        return confidence;
    }

    @JsonProperty("confidence")
    public void setConfidence(Double confidence) {
        this.confidence = confidence;
    }

    @JsonProperty("source")
    public String getSource() {
        return source;
    }

    @JsonProperty("source")
    public void setSource(String source) {
        this.source = source;
    }

    @JsonProperty("_id")
    public String getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(String id) {
        this.id = id;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
