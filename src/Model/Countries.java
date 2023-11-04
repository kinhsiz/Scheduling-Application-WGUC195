package Model;

/**
 * A Countries class model.
 *
 * @author Ruben Rios.
 */
//country values are not to be modified. Setters are not needed.
public class Countries {
    private int countryId;
    private String country;

    /**
     * A constructor for Countries object.
     * @param countryId
     * @param country
     */
    public Countries(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    /**
     * Gets country id.
     * @return integer.
     */
    public int getCountryId() {
        return countryId;
    }

    /**
     * Gets country name.
     * @return String.
     */
    public String getCountry() {
        return country;
    }
}

