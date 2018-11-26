package beans.survey;

/**
 * Define Location class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class Location {
    private Float longitude;
    private Float latitude;

    /**
     * Constructor of Location class
     * @param longitude Float representing the longitude of position
     * @param latitude Float representing the latitude of position
     */
    public Location(Float longitude, Float latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    /**
     * Get the longitude of user position
     * @return Float representing the longitude
     */
    public Float getLongitude() {
        return longitude;
    }

    /**
     * Set the longitude of user position
     * @param longitude Float representing the longitude
     */
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    /**
     * Get the latitude of user position
     * @return Float representing the latitude
     */
    public Float getLatitude() {
        return latitude;
    }

    /**
     * Set the latitude of user position
     * @param latitude Float representing the latitude
     */
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return "Location{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }
}
