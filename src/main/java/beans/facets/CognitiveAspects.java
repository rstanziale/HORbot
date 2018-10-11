package beans.facets;

import beans.facets.values.CognitiveEmpathy;
import beans.facets.values.CognitivePersonality;

import java.util.List;

/**
 * Define Cognitive Aspects facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class CognitiveAspects {
    private List<CognitivePersonality> personalities;
    private List<CognitiveEmpathy> empathies;

    public List<CognitivePersonality> getPersonalities() {
        return personalities;
    }

    public void setPersonalities(List<CognitivePersonality> personalities) {
        this.personalities = personalities;
    }

    public List<CognitiveEmpathy> getEmpathies() {
        return empathies;
    }

    public void setEmpathies(List<CognitiveEmpathy> empathies) {
        this.empathies = empathies;
    }
}
