
package beans.facets;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Define Cognitive Aspects values from Myrror facets
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "personalities",
    "empathies"
})
public class CognitiveAspects {

    @JsonProperty("personalities")
    private List<Personality> personalities = null;
    @JsonProperty("empathies")
    private List<Empathy> empathies = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("personalities")
    public List<Personality> getPersonalities() {
        return personalities;
    }

    @JsonProperty("personalities")
    public void setPersonalities(List<Personality> personalities) {
        this.personalities = personalities;
    }

    @JsonProperty("empathies")
    public List<Empathy> getEmpathies() {
        return empathies;
    }

    @JsonProperty("empathies")
    public void setEmpathies(List<Empathy> empathies) {
        this.empathies = empathies;
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
