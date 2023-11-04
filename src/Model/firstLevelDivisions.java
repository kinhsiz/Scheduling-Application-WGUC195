package Model;

/**
 * A division class model.
 *
 * @author Ruben Rios.
 */
//firstLevelDivisions values are not to be modified. Setters are not needed.
public class firstLevelDivisions {
    private int divisionId;
    private String division;
    public int countryId;

    /**
     * A constructor for division object.
     * @param divisionId
     * @param division
     * @param countryId
     */
    public firstLevelDivisions(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    /**
     * A method to get division id.
     * @return integer.
     */
    public int getDivisionId() {
        return divisionId;
    }

    /**
     * A method to set division name.
     * @return string.
     */
    public String getDivision() {
        return division;
    }

    /**
     * A method to get country id.
     * @return integer.
     */
    public int getCountryId() {
        return countryId;
    }
}
