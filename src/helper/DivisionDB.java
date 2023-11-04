package helper;

import Model.firstLevelDivisions;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * A class for database and controller interaction.
 *
 * @author Ruben Rios.
 */
public class DivisionDB {
    /**
     * Method that retrieves all divisions from database.
     * @return a division array.
     */
    public static ObservableList<firstLevelDivisions> getAllDivisions() {
        ObservableList<firstLevelDivisions> divisionList = FXCollections.observableArrayList(); //list to return
        String sql = "SELECT * from first_level_divisions"; //setup sql
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //prepare statement
            ResultSet rs = ps.executeQuery();
            while(rs.next()) { //pull db data out
                int id = rs.getInt("Division_ID");
                String name = rs.getString("Division");
                int countryId = rs.getInt("Country_ID");

                firstLevelDivisions division = new firstLevelDivisions(id,name, countryId); //create an object instance of Countries

                divisionList.add(division);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return divisionList;
    }
}
