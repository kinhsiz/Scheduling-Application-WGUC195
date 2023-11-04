package helper;

import Model.Countries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class used for database and controller interaction.
 *
 * @author Ruben Rios.
 */
public class CountryDB {
    /**
     * Gets all countries from database.
     * @return a Countries Array.
     */
    public static ObservableList<Countries> getAllCountries() {
        ObservableList<Countries> countryList = FXCollections.observableArrayList(); //list to return
        String sql = "SELECT * from countries"; //setup sql
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //prepare statement
            ResultSet rs = ps.executeQuery();
            while(rs.next()) { //pull db data out
                int id = rs.getInt("Country_ID");
                String name = rs.getString("Country");

                Countries country = new Countries(id,name); //create an object instance of Countries

                countryList.add(country);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return countryList;
    }
}

/*
Notes:
--ResultSet: The result set is an object that represents a set of data returned from a data source, usually as the result of a query.
  The result set contains rows and columns to hold the requested data elements, and it is navigated with a cursor

--executeQuery(): This method is used to execute statements that returns tabular data (example select). It returns an object of the class
  ResultSet.

  https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=1739c4da-a6ca-42d1-8a9f-af2901296583

  ObservableList<Countries> countryListt = CountryDB.getAllCountries();
        for(Countries countries : countryListt) {
            System.out.println(countries.getCountryId() +" " + countries.getCountry());
        }
 */