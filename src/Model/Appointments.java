package Model;
import java.sql.Timestamp;

/**
 * Appointment class model.
 *
 * @author Ruben Rios.
 */
public class Appointments {
    private int aptId;
    private String title;
    private String description;
    private String location;
    private String type;
    private Timestamp startDateTime;
    private Timestamp endDateTime;
    private Timestamp createDate;
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdatedBy;
    private int customerId;
    private int userId;
    private int contactId;

    /**
     * Constructor for Appointment object.
     * @param aptId
     * @param title
     * @param description
     * @param location
     * @param type
     * @param startDateTime
     * @param endDateTime
     * @param createDate
     * @param createdBy
     * @param lastUpdate
     * @param lastUpdatedBy
     * @param customerId
     * @param userId
     * @param contactId
     */
    public Appointments(int aptId, String title, String description, String location, String type, Timestamp startDateTime,
                        Timestamp endDateTime, Timestamp createDate, String createdBy, Timestamp lastUpdate, String lastUpdatedBy,
                        int customerId, int userId, int contactId) {
        this.aptId = aptId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.type = type;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdatedBy = lastUpdatedBy;
        this.customerId = customerId;
        this.userId = userId;
        this.contactId = contactId;
    }

    /**
     * Method to get appointment id.
     * @return integer.
     */
    public int getAptId() {
        return aptId;
    }

    /**
     * Method to get appointment title.
     * @return String.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Method to set appointment title.
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Method to get appointment description.
     * @return String.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Method to set appointment description.
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Method to get appointment location.
     * @return String.
     */
    public String getLocation() {
        return location;
    }

    /**
     * Method to set appointment location.
     * @param location
     */
    public void setLocation(String location) {
        this.location = location;
    }

    /**
     * Method to get appointment type.
     * @return String.
     */
    public String getType() {
        return type;
    }

    /**
     * Method to set appointment type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Method to get appointment date time.
     * @return Timestamp.
     */
    public Timestamp getStartDateTime() {
        return startDateTime;
    }

    /**
     * Method to set start date time.
     * @param startDateTime
     */
    public void setStartDateTime(Timestamp startDateTime) {
        this.startDateTime = startDateTime;
    }

    /**
     * Method to get end date time.
     * @return Timestamp.
     */
    public Timestamp getEndDateTime() {
        return endDateTime;
    }

    /**
     * Method to set end date time.
     * @param endDateTime
     */
    public void setEndDateTime(Timestamp endDateTime) {
        this.endDateTime = endDateTime;
    }

    /**
     * Method to get date appointment was created.
     * @return Timestamp.
     */
    public Timestamp getCreateDate() {
        return createDate;
    }

    /**
     * Method to set date appointment is created.
     * @param createDate
     */
    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    /**
     * Method to get who was appointment created by.
     * @return String.
     */
    public String getCreatedBy() {
        return createdBy;
    }

    /**
     * Method to set who appointment was created by.
     * @param createdBy
     */
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * Method to get timestamp.
     * @return timestamp.
     */
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * Method to get who it was updated by.
     * @return string.
     */
    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    /**
     * Method to get customer id.
     * @return integer.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * Method to set customer id.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * Method to get user id.
     * @return integer.
     */
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    /**
     * Method to get contact id.
     * @return integer.
     */
    public int getContactId() {
        return contactId;
    }

    /**
     * Method to set contact id.
     * @param contactId
     */
    public void setContactId(int contactId) {
        this.contactId = contactId;
    }
}
