
package beans.facets;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Define onotology values from Myrror facets
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "@context",
    "user",
    "demographics",
    "affects",
    "behaviors",
    "cognitiveAspects",
    "interests",
    "physicalStates",
    "socialRelations"
})
public class Ontology {

    @JsonProperty("@context")
    private String context;
    @JsonProperty("user")
    private String user;
    @JsonProperty("demographics")
    private Demographics demographics;
    @JsonProperty("affects")
    private List<Affect> affects = null;
    @JsonProperty("behaviors")
    private Behaviors behaviors;
    @JsonProperty("cognitiveAspects")
    private CognitiveAspects cognitiveAspects;
    @JsonProperty("interests")
    private List<Interest> interests = null;
    @JsonProperty("physicalStates")
    private PhysicalStates physicalStates;
    @JsonProperty("socialRelations")
    private List<SocialRelation> socialRelations = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("@context")
    public String getContext() {
        return context;
    }

    @JsonProperty("@context")
    public void setContext(String context) {
        this.context = context;
    }

    @JsonProperty("user")
    public String getUser() {
        return user;
    }

    @JsonProperty("user")
    public void setUser(String user) {
        this.user = user;
    }

    @JsonProperty("demographics")
    public Demographics getDemographics() {
        return demographics;
    }

    @JsonProperty("demographics")
    public void setDemographics(Demographics demographics) {
        this.demographics = demographics;
    }

    @JsonProperty("affects")
    public List<Affect> getAffects() {
        return affects;
    }

    @JsonProperty("affects")
    public void setAffects(List<Affect> affects) {
        this.affects = affects;
    }

    @JsonProperty("behaviors")
    public Behaviors getBehaviors() {
        return behaviors;
    }

    @JsonProperty("behaviors")
    public void setBehaviors(Behaviors behaviors) {
        this.behaviors = behaviors;
    }

    @JsonProperty("cognitiveAspects")
    public CognitiveAspects getCognitiveAspects() {
        return cognitiveAspects;
    }

    @JsonProperty("cognitiveAspects")
    public void setCognitiveAspects(CognitiveAspects cognitiveAspects) {
        this.cognitiveAspects = cognitiveAspects;
    }

    @JsonProperty("interests")
    public List<Interest> getInterests() {
        return interests;
    }

    @JsonProperty("interests")
    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    @JsonProperty("physicalStates")
    public PhysicalStates getPhysicalStates() {
        return physicalStates;
    }

    @JsonProperty("physicalStates")
    public void setPhysicalStates(PhysicalStates physicalStates) {
        this.physicalStates = physicalStates;
    }

    @JsonProperty("socialRelations")
    public List<SocialRelation> getSocialRelations() {
        return socialRelations;
    }

    @JsonProperty("socialRelations")
    public void setSocialRelations(List<SocialRelation> socialRelations) {
        this.socialRelations = socialRelations;
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
