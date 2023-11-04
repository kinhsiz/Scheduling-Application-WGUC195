package Model;

/**
 * A Customers class model.
 *
 * @author Ruben Rios.
 */
public class Customers {
    private int customerId;
    private String name;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;

    /**
     * A constructor for Customers object.
     * @param customerId
     * @param name
     * @param address
     * @param phoneNumber
     * @param postalCode
     * @param divisionId
     */
    public Customers(int customerId, String name, String address, String phoneNumber, String postalCode, int divisionId) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.postalCode = postalCode;
        this.divisionId = divisionId;
    }

    /**
     * A method that gets customer id.
     * @return integer.
     */
    public int getCustomerId() {
        return customerId;
    }

    /**
     * A method that sets customer id.
     * @param customerId
     */
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    /**
     * A method that gets customer name.
     * @return string.
     */
    public String getName() {
        return name;
    }

    /**
     * A method that sets customer name.
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * A method that gets customer address.
     * @return string.
     */
    public String getAddress() {
        return address;
    }

    /**
     * A method that sets customer address.
     * @param address
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * A method that gets customer postal code.
     * @return string.
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * A method that sets customer postal code.
     * @param postalCode
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * A method that gets customer phone number.
     * @return string.
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /**
     * A method that sets customer phone number.
     * @param phoneNumber
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /**
     * A method that gets customer division id.
     * @return integer.
     */

    public int getDivisionId() {
        return divisionId;
    }
    /**
     *A method that sets customer division id.
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }
}

/*
Notes:
Timestamp timestamp = new Timestamp(System.currentTimeMillis());
System.out.println(timestamp); --> 2022-12-14 19:37:19.764
 */