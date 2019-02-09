
package ontology.beans.facets;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Define behaviors values from Myrror facets
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "fromText",
    "fromActivity"
})
public class Behaviors {

    @JsonProperty("fromText")
    private List<FromText> fromText = null;
    @JsonProperty("fromActivity")
    private List<FromActivity> fromActivity = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("fromText")
    public List<FromText> getFromText() {
        return fromText;
    }

    @JsonProperty("fromText")
    public void setFromText(List<FromText> fromText) {
        this.fromText = fromText;
    }

    @JsonProperty("fromActivity")
    public List<FromActivity> getFromActivity() {
        return fromActivity;
    }

    @JsonProperty("fromActivity")
    public void setFromActivity(List<FromActivity> fromActivity) {
        this.fromActivity = fromActivity;
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
