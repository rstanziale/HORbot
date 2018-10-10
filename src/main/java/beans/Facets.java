package beans;

import beans.values.AffectsValue;
import beans.values.InterestsValue;
import beans.values.SocialRelationsValue;

import java.util.List;

/**
 * Define Facets collection
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class Facets {
    private Demographics demographics;
    private List<AffectsValue> affects;
    private Behaviors behaviors;
    private CognitiveAspects cognitiveAspects;
    private List<InterestsValue> interests;
    private PhysicalStates physicalStates;
    private List<SocialRelationsValue> socialRelations;

    public Demographics getDemographics() {
        return demographics;
    }

    public void setDemographics(Demographics demographics) {
        this.demographics = demographics;
    }

    public List<AffectsValue> getAffects() {
        return affects;
    }

    public void setAffects(List<AffectsValue> affects) {
        this.affects = affects;
    }

    public Behaviors getBehaviors() {
        return behaviors;
    }

    public void setBehaviors(Behaviors behaviors) {
        this.behaviors = behaviors;
    }

    public CognitiveAspects getCognitiveAspects() {
        return cognitiveAspects;
    }

    public void setCognitiveAspects(CognitiveAspects cognitiveAspects) {
        this.cognitiveAspects = cognitiveAspects;
    }

    public List<InterestsValue> getInterests() {
        return interests;
    }

    public void setInterests(List<InterestsValue> interests) {
        this.interests = interests;
    }

    public PhysicalStates getPhysicalStates() {
        return physicalStates;
    }

    public void setPhysicalStates(PhysicalStates physicalStates) {
        this.physicalStates = physicalStates;
    }

    public List<SocialRelationsValue> getSocialRelations() {
        return socialRelations;
    }

    public void setSocialRelations(List<SocialRelationsValue> socialRelations) {
        this.socialRelations = socialRelations;
    }
}
