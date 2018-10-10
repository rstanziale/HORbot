package beans.values;

import java.util.Date;

/**
 * Define a generic text element for Behaviors facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class BehaviorsFromText {
    private String text;
    private int latitude;
    private int longitude;
    private Date date;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getLatitude() {
        return latitude;
    }

    public void setLatitude(int latitude) {
        this.latitude = latitude;
    }

    public int getLongitude() {
        return longitude;
    }

    public void setLongitude(int longitude) {
        this.longitude = longitude;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
