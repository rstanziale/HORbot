
package ontology.beans.facets;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Define Behaviors from activity values from Myrror facets
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "timestamp",
    "minutesVeryActive",
    "nameActivity"
})
public class FromActivity {

    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("minutesVeryActive")
    private Integer minutesVeryActive;
    @JsonProperty("nameActivity")
    private String nameActivity;
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

    @JsonProperty("minutesVeryActive")
    public Integer getMinutesVeryActive() {
        return minutesVeryActive;
    }

    @JsonProperty("minutesVeryActive")
    public void setMinutesVeryActive(Integer minutesVeryActive) {
        this.minutesVeryActive = minutesVeryActive;
    }

    @JsonProperty("nameActivity")
    public String getNameActivity() {
        return nameActivity;
    }

    @JsonProperty("nameActivity")
    public void setNameActivity(String nameActivity) {
        this.nameActivity = nameActivity;
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
