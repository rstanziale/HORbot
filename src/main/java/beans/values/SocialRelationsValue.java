package beans.values;

/**
 * Define a generic element for Social Relations facet
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class SocialRelationsValue {
    private String contactId;
    private String source;

    public String getContactId() {
        return contactId;
    }

    public void setContactId(String contactId) {
        this.contactId = contactId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
