package recommender.contentBased.beans;

/**
 * Define Item class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class Item implements Comparable<Item>  {
    private String name;
    private String website;
    private String address;
    private String phone;
    private String tags;
    private int price;
    private double ratingAverage;
    private int totalReview;
    private float lat;
    private float lng;
    private float score;
    private boolean recommended;
    private int recommenderType;
    private boolean liked;
    private long interactionTime;

    /**
     * Constructor of the item
     * @param website representing the item website
     * @param name representing the item name
     * @param address representing the item address
     * @param phone representing the item phone number
     * @param tags representing the item tag list
     * @param price representing the item price
     * @param ratingAverage representing the item rating average
     * @param totalReview representing the item total reviews
     * @param lat representing the item latitude
     * @param lng representing the item longitude
     */
    public Item(String website, String name, String address, String phone, String tags,
                String price, double ratingAverage, int totalReview,
                float lat, float lng) {
        this.website = website;
        this.name = name;
        this.address = address;
        this.phone = phone.equals("null") ? "" : phone;
        this.tags = tags;
        this.price = price.equals("null") ? 0 : Integer.valueOf(price);
        this.ratingAverage = ratingAverage;
        this.totalReview = totalReview;
        this.lat = lat;
        this.lng = lng;
        this.recommended = false;
    }

    /**
     * Constructor of the item
     * @param website representing the item website
     * @param name representing the item name
     * @param address representing the item address
     * @param phone representing the item phone number
     * @param tags representing the item tag list
     * @param ratingAverage representing the item rating average
     * @param lat representing the item latitude
     * @param lng representing the item longitude
     */
    public Item(String website, String name, String address, String phone, String tags,
                double ratingAverage, float lat, float lng) {
        this.website = website;
        this.name = name;
        this.address = address;
        this.phone = phone.equals("null") ? "" : phone;
        this.tags = tags;
        this.ratingAverage = ratingAverage;
        this.lat = lat;
        this.lng = lng;
    }

    /**
     * Get the Item name
     * @return Item name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the Item website
     * @return Item website
     */
    public String getWebsite() {
        return website;
    }

    /**
     * Get the Item address
     * @return Item address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Get the Item ppphone number
     * @return Item phone number
     */
    public String getPhone() {
        return phone;
    }

    /**
     * Get the Item tag list
     * @return Item tag list
     */
    public String getTags() {
        return tags;
    }

    /**
     * Get the Item rating average
     * @return Item rating average
     */
    public double getRatingAverage() {
        return ratingAverage;
    }

    /**
     * Get the Item latitude
     * @return Item latitude
     */
    public float getLat() {
        return lat;
    }

    /**
     * Get the Item longitude
     * @return Item longitude
     */
    public float getLng() {
        return lng;
    }

    /**
     * Set the Item score
     * @param score Lucene score
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * Check if the Item is recommended to the user
     * @return boolean flag
     */
    public boolean isRecommended() {
        return recommended;
    }

    /**
     * Change recommended flag of the item
     */
    public void setRecommended() {
        this.recommended = !this.recommended;
    }

    /**
     * Get recommender type
     * @return integer representing the recommend type
     */
    public int getRecommenderType() {
        return recommenderType;
    }

    /**
     * Set recommender type
     * @param recommenderType integer representing the recommender type
     */
    public void setRecommenderType(int recommenderType) {
        this.recommenderType = recommenderType;
    }

    /**
     * Check if the item is liked by user
     * @return boolean flag
     */
    public boolean isLiked() {
        return liked;
    }

    /**
     * Set item preference about recommend item
     * @param liked boolean flag
     */
    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    /**
     * Get time when user has liked an item
     * @return long representing stop time
     */
    public long getInteractionTime() {
        return interactionTime;
    }

    /**
     * Set time when user starts recommend and stop when user liked an item
     * @param interactionTime representing time in millis
     */
    public void setInteractionTime(long interactionTime) {
        this.interactionTime = interactionTime;
    }

    public int compareTo(Item o) {
        // Cannot use doubleToRawLongBits because of possibility of NaNs.
        long thisBits    = Double.doubleToLongBits(this.score);
        long anotherBits = Double.doubleToLongBits(o.score);

        return (thisBits == anotherBits ?  1 : // Values are equal
                (thisBits < anotherBits ? 1 : // (-0.0, 0.0) or (!NaN, NaN)
                        -1));
    }

    @Override
    public String toString() {
        String phone = this.phone.equals("") ? "Assente" : this.phone;
        return "Ti suggerisco '" + name + '\'' +
                " in " + address + " (" + lat + ", " + lng + ")" +
                " con punteggio: " + this.getReviewsStar() +
                "\nSito: " + website + "\nTelefono: +39 " +  phone;
    }

    /**
     * Build stars to show into recommend item message
     * @return a list of stars :D
     */
    private String getReviewsStar() {
        String stars = ":star:";
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < Math.round(this.ratingAverage); i++){
            sb.append(stars);
        }
        return sb.toString();
    }
}
