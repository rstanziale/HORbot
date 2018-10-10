package beans.values;

import java.util.Date;

/**
 * Define a generic element for Affects facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class AffectsValue {
    private Date date;
    private int sentimen;
    private String emotion;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getSentimen() {
        return sentimen;
    }

    public void setSentimen(int sentimen) {
        this.sentimen = sentimen;
    }

    public String getEmotion() {
        return emotion;
    }

    public void setEmotion(String emotion) {
        this.emotion = emotion;
    }
}
