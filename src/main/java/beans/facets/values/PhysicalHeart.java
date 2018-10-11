package beans.facets.values;

/**
 * Define a generic heart element for Physical States facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class PhysicalHeart {
    private int restingHeartRate;
    private int outOfRange_minutes;
    private int fatBurn_minutes;
    private int cardio_minutes;
    private int peak_minutes;

    public int getRestingHeartRate() {
        return restingHeartRate;
    }

    public void setRestingHeartRate(int restingHeartRate) {
        this.restingHeartRate = restingHeartRate;
    }

    public int getOutOfRange_minutes() {
        return outOfRange_minutes;
    }

    public void setOutOfRange_minutes(int outOfRange_minutes) {
        this.outOfRange_minutes = outOfRange_minutes;
    }

    public int getFatBurn_minutes() {
        return fatBurn_minutes;
    }

    public void setFatBurn_minutes(int fatBurn_minutes) {
        this.fatBurn_minutes = fatBurn_minutes;
    }

    public int getCardio_minutes() {
        return cardio_minutes;
    }

    public void setCardio_minutes(int cardio_minutes) {
        this.cardio_minutes = cardio_minutes;
    }

    public int getPeak_minutes() {
        return peak_minutes;
    }

    public void setPeak_minutes(int peak_minutes) {
        this.peak_minutes = peak_minutes;
    }
}
