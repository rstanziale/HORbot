package beans.values;

/**
 * Define a generic sleep element for Physical States facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class PhysicalsSleep {
    private int duration;
    private int efficiency;
    private int minutesAfterWakeup;
    private int minutesAsleep;
    private int minutesAwake;
    private int minutesToFallAsleep;
    private int timeInBed;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getMinutesAfterWakeup() {
        return minutesAfterWakeup;
    }

    public void setMinutesAfterWakeup(int minutesAfterWakeup) {
        this.minutesAfterWakeup = minutesAfterWakeup;
    }

    public int getMinutesAsleep() {
        return minutesAsleep;
    }

    public void setMinutesAsleep(int minutesAsleep) {
        this.minutesAsleep = minutesAsleep;
    }

    public int getMinutesAwake() {
        return minutesAwake;
    }

    public void setMinutesAwake(int minutesAwake) {
        this.minutesAwake = minutesAwake;
    }

    public int getMinutesToFallAsleep() {
        return minutesToFallAsleep;
    }

    public void setMinutesToFallAsleep(int minutesToFallAsleep) {
        this.minutesToFallAsleep = minutesToFallAsleep;
    }

    public int getTimeInBed() {
        return timeInBed;
    }

    public void setTimeInBed(int timeInBed) {
        this.timeInBed = timeInBed;
    }
}
