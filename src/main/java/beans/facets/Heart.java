
package beans.facets;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Define heart values from Myrror facets
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "timestamp",
    "restingHeartRate",
    "outOfRange_minutes",
    "fatBurn_minutes",
    "cardio_minutes",
    "peak_minutes"
})
public class Heart {

    @JsonProperty("timestamp")
    private Long timestamp;
    @JsonProperty("restingHeartRate")
    private Integer restingHeartRate;
    @JsonProperty("outOfRange_minutes")
    private Integer outOfRangeMinutes;
    @JsonProperty("fatBurn_minutes")
    private Integer fatBurnMinutes;
    @JsonProperty("cardio_minutes")
    private Integer cardioMinutes;
    @JsonProperty("peak_minutes")
    private Integer peakMinutes;
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

    @JsonProperty("restingHeartRate")
    public Integer getRestingHeartRate() {
        return restingHeartRate;
    }

    @JsonProperty("restingHeartRate")
    public void setRestingHeartRate(Integer restingHeartRate) {
        this.restingHeartRate = restingHeartRate;
    }

    @JsonProperty("outOfRange_minutes")
    public Integer getOutOfRangeMinutes() {
        return outOfRangeMinutes;
    }

    @JsonProperty("outOfRange_minutes")
    public void setOutOfRangeMinutes(Integer outOfRangeMinutes) {
        this.outOfRangeMinutes = outOfRangeMinutes;
    }

    @JsonProperty("fatBurn_minutes")
    public Integer getFatBurnMinutes() {
        return fatBurnMinutes;
    }

    @JsonProperty("fatBurn_minutes")
    public void setFatBurnMinutes(Integer fatBurnMinutes) {
        this.fatBurnMinutes = fatBurnMinutes;
    }

    @JsonProperty("cardio_minutes")
    public Integer getCardioMinutes() {
        return cardioMinutes;
    }

    @JsonProperty("cardio_minutes")
    public void setCardioMinutes(Integer cardioMinutes) {
        this.cardioMinutes = cardioMinutes;
    }

    @JsonProperty("peak_minutes")
    public Integer getPeakMinutes() {
        return peakMinutes;
    }

    @JsonProperty("peak_minutes")
    public void setPeakMinutes(Integer peakMinutes) {
        this.peakMinutes = peakMinutes;
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
