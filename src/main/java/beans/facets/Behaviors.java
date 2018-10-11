package beans.facets;

import beans.facets.values.BehaviorsFromActivity;
import beans.facets.values.BehaviorsFromText;

import java.util.List;

/**
 * Define Behaviors facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class Behaviors {
    private List<BehaviorsFromText> fromText;
    private List<BehaviorsFromActivity> fromActivity;

    public List<BehaviorsFromText> getFromText() {
        return fromText;
    }

    public void setFromText(List<BehaviorsFromText> fromText) {
        this.fromText = fromText;
    }

    public List<BehaviorsFromActivity> getFromActivity() {
        return fromActivity;
    }

    public void setFromActivity(List<BehaviorsFromActivity> fromActivity) {
        this.fromActivity = fromActivity;
    }
}
