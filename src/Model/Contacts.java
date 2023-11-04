package Model;

/**
 * A contact class model.
 *
 * @author Ruben Rios.
 */
public class Contacts {
    private int contactId;
    private String contactName;

    /**
     * A constructor for Contacts object.
     * @param contactId
     * @param contactName
     */
    public Contacts(int contactId, String contactName) {
        this.contactId = contactId;
        this.contactName = contactName;
    }
}
