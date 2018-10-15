
package beans.facets;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Define Sleep values from Myrror facets
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "timestamp",
    "duration",
    "efficiency",
    "minutesAfterWakeup",
    "minutesAsleep",
    "minutesAwake",
    "minutesToFallAsleep",
    "timeInBed"
})
public class Sleep {

    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("efficiency")
    private Integer efficiency;
    @JsonProperty("minutesAfterWakeup")
    private Integer minutesAfterWakeup;
    @JsonProperty("minutesAsleep")
    private Integer minutesAsleep;
    @JsonProperty("minutesAwake")
    private Integer minutesAwake;
    @JsonProperty("minutesToFallAsleep")
    private Integer minutesToFallAsleep;
    @JsonProperty("timeInBed")
    private Integer timeInBed;
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

    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    @JsonProperty("duration")
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @JsonProperty("efficiency")
    public Integer getEfficiency() {
        return efficiency;
    }

    @JsonProperty("efficiency")
    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    @JsonProperty("minutesAfterWakeup")
    public Integer getMinutesAfterWakeup() {
        return minutesAfterWakeup;
    }

    @JsonProperty("minutesAfterWakeup")
    public void setMinutesAfterWakeup(Integer minutesAfterWakeup) {
        this.minutesAfterWakeup = minutesAfterWakeup;
    }

    @JsonProperty("minutesAsleep")
    public Integer getMinutesAsleep() {
        return minutesAsleep;
    }

    @JsonProperty("minutesAsleep")
    public void setMinutesAsleep(Integer minutesAsleep) {
        this.minutesAsleep = minutesAsleep;
    }

    @JsonProperty("minutesAwake")
    public Integer getMinutesAwake() {
        return minutesAwake;
    }

    @JsonProperty("minutesAwake")
    public void setMinutesAwake(Integer minutesAwake) {
        this.minutesAwake = minutesAwake;
    }

    @JsonProperty("minutesToFallAsleep")
    public Integer getMinutesToFallAsleep() {
        return minutesToFallAsleep;
    }

    @JsonProperty("minutesToFallAsleep")
    public void setMinutesToFallAsleep(Integer minutesToFallAsleep) {
        this.minutesToFallAsleep = minutesToFallAsleep;
    }

    @JsonProperty("timeInBed")
    public Integer getTimeInBed() {
        return timeInBed;
    }

    @JsonProperty("timeInBed")
    public void setTimeInBed(Integer timeInBed) {
        this.timeInBed = timeInBed;
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
