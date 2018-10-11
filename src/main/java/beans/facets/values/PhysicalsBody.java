package beans.facets.values;

/**
 * Define a generic body element for Physical States facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class PhysicalsBody {
    private int bodyFat;
    private int bodyWeight;
    private int bodyBmi;
    private String nameBody;

    public int getBodyFat() {
        return bodyFat;
    }

    public void setBodyFat(int bodyFat) {
        this.bodyFat = bodyFat;
    }

    public int getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(int bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public int getBodyBmi() {
        return bodyBmi;
    }

    public void setBodyBmi(int bodyBmi) {
        this.bodyBmi = bodyBmi;
    }

    public String getNameBody() {
        return nameBody;
    }

    public void setNameBody(String nameBody) {
        this.nameBody = nameBody;
    }
}
