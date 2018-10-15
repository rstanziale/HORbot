
package beans.facets;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Define Physical state values from Myrror facets
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "heart",
    "sleep",
    "food",
    "body"
})
public class PhysicalStates {

    @JsonProperty("heart")
    private List<Heart> heart = null;
    @JsonProperty("sleep")
    private List<Sleep> sleep = null;
    @JsonProperty("food")
    private List<Food> food = null;
    @JsonProperty("body")
    private List<Object> body = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("heart")
    public List<Heart> getHeart() {
        return heart;
    }

    @JsonProperty("heart")
    public void setHeart(List<Heart> heart) {
        this.heart = heart;
    }

    @JsonProperty("sleep")
    public List<Sleep> getSleep() {
        return sleep;
    }

    @JsonProperty("sleep")
    public void setSleep(List<Sleep> sleep) {
        this.sleep = sleep;
    }

    @JsonProperty("food")
    public List<Food> getFood() {
        return food;
    }

    @JsonProperty("food")
    public void setFood(List<Food> food) {
        this.food = food;
    }

    @JsonProperty("body")
    public List<Object> getBody() {
        return body;
    }

    @JsonProperty("body")
    public void setBody(List<Object> body) {
        this.body = body;
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
