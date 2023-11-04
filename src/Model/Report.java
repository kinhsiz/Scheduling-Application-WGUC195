package Model;

/**
 * A report class model.
 *
 * @author Ruben Rios.
 */
public class Report {
    private String month;
    private String type;
    private int total;

    /**
     * A constructor for report object.
     * @param type
     * @param month
     * @param total
     */
    public Report(String type,String month, int total) {
        this.month = month;
        this.type = type;
        this.total = total;
    }

    /**
     * A method that gets month.
     * @return string.
     */
    public String getMonth() {
        return month;
    }

    /**
     * A method that sets month.
     * @param month
     */
    public void setMonth(String month) {
        this.month = month;
    }

    /**
     * A method that gets appointment type.
     * @return string.
     */
    public String getType() {
        return type;
    }

    /**
     * A method that sets appointment type.
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * A method that gets total amount.
     * @return integer.
     */
    public int getTotal() {
        return total;
    }

    /**
     * A method that sets a total.
     * @param total
     */
    public void setTotal(int total) {
        this.total = total;
    }
}
