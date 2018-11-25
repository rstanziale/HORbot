package beans.survey;

/**
 * Define Context class
 *
 * @author Roberto B. Stanziale
 * @version 1.0
 */
public class Context {
    private boolean checked;
    private String contextName;

    /**
     * Constructor of the context
     * @param contextName String representing the name of the context
     */
    public Context(String contextName) {
        this.checked = false;
        this.contextName = contextName;
    }

    /**
     * Check if the context is checked
     * @return true if the context is checked
     */
    public boolean isChecked() {
        return checked;
    }

    /**
     * Change the checked value
     */
    public void setChecked() {
        this.checked = !this.checked;
    }

    /**
     * Reset the checked value
     */
    public void resetChecked() {
        this.checked = false;
    }

    /**
     * Get the name of the context
     * @return String representing the name of the context
     */
    public String getContextName() {
        return contextName;
    }

    @Override
    public String toString() {
        return this.checked ? ":ballot_box_with_check:" + " " + this.contextName
                : ":white_medium_small_square:" + " " + this.contextName;
    }
}
