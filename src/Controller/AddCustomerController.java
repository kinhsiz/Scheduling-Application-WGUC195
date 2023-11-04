package Controller;

import Model.Countries;
import Model.firstLevelDivisions;
import helper.CountryDB;
import helper.CustomersDB;
import helper.DivisionDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

/**
 * Controller for addition of new customers.
 *
 * @author Ruben Rios
 */
public class AddCustomerController implements Initializable {
    @FXML private TextField addressText;
    @FXML private ComboBox countryCombo;
    @FXML private ComboBox divisionCombo;
    @FXML private TextField nameText;
    @FXML private TextField pCodeText;
    @FXML private TextField pNumberText;

    /**
     * Array to get all countries from database
     * @return
     */
    private ObservableList<String> getAllCountries() { //gets country from Country DB Observable array
        ObservableList<Countries> country = CountryDB.getAllCountries();
        ObservableList<String> countries = FXCollections.observableArrayList();

        for(Countries countryList : country) {
            countries.add(countryList.getCountry());
        }
        return countries;
    }

    /**
     * Populates division combo box based on user country choise
     * @param event
     */
    public void onSelectDivision(ActionEvent event) {
        int countryChoice = countryCombo.getSelectionModel().getSelectedIndex();
        ObservableList<firstLevelDivisions> division = DivisionDB.getAllDivisions();
        ObservableList<String> divisions = FXCollections.observableArrayList();
        if (countryChoice == 0) {
            for (int i = 0; i < division.size(); i++) {
                if (division.get(i).getCountryId() == 1) {
                    divisions.add(division.get(i).getDivision());
                }
            }
        } else if (countryChoice == 1) {
            for (int i = 0; i < division.size(); i++) {
                if (division.get(i).getCountryId() == 2) {
                    divisions.add(division.get(i).getDivision());
                }
            }
        } else if (countryChoice == 2) {
            for (int i = 0; i < division.size(); i++) {
                if (division.get(i).getCountryId() == 3) {
                    divisions.add(division.get(i).getDivision());
                }
            }
        }
        divisionCombo.setItems(divisions);
    }

    /**
     * Returns to view customer screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onCancelBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Saves new customer to database.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onSaveAppBtn(ActionEvent event) throws SQLException, IOException {
        Object country = countryCombo.getSelectionModel().getSelectedItem();
        Object divisionName = divisionCombo.getSelectionModel().getSelectedItem();

        int customerId = CustomersDB.generateNewCustomerId();
        String customerName = nameText.getText();
        String address = addressText.getText();
        String postalCode = pCodeText.getText();
        String phoneNumber = pNumberText.getText();
        int divisionID;

        if(validateName(customerName) && validateAddress(address) && validatePostalCode(postalCode)&&
        validatePhoneNumber(phoneNumber) && validateCountry(country) && validateDivision(divisionName)) {
            divisionID = getDivisionID(divisionName);
            CustomersDB.addNewCustomer(customerId, customerName, address, postalCode,phoneNumber, divisionID);

            Parent parent = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } else {
            System.out.println("Couldn't add new customer, please check again");
        }
    }

    /**
     * Gets division id from division name.
     * @param divisionName division name.
     * @return integer division id.
     */
    private int getDivisionID(Object divisionName) {
        ObservableList<firstLevelDivisions> division = DivisionDB.getAllDivisions();

        int id = 0;
        if(divisionName == null){
            displayAlert(7);
        } else {
            for(firstLevelDivisions divisions : division) {
                if(divisions.getDivision().equals(divisionName)) { //if(divisions.getDivision().contains(divisionName)) {
                    id = divisions.getDivisionId();
                }
            }
        }
        return id;
    }

    /**
     * Checks for empty fields.
     * @param country
     * @return boolean.
     */
    private boolean validateCountry(Object country){
        if(country == null) {
            displayAlert(6);
            return false;
        }
        return true;
    }
    private boolean validateDivision(Object division){
        if(division == null) {
            displayAlert(7);
            return false;
        }
        return true;
    }

    /**
     * checks for empty fields.
     * @param name
     * @return boolean.
     */
    private boolean validateName(String name) {
        if(name.isEmpty()) {
            displayAlert(1);
            return false;
        }
        return true;
    }

    /**
     * check for empty fields.
     * @param postalCode
     * @return boolean.
     */
    private boolean validatePostalCode(String postalCode) {
        if(postalCode.isEmpty()) {
            displayAlert(2);
            return false;
        }
        return true;
    }

    /**
     * Checks for empty field.
     * @param phoneNumber
     * @return boolean.
     */
    private boolean validatePhoneNumber(String phoneNumber) {
        if(phoneNumber.isEmpty()) {
            displayAlert(3);
            return false;
        }
        return true;
    }

    /**
     * Checks for empty fields.
     * @param address
     * @return boolean.
     */
    private boolean validateAddress(String address) {
        if(address.isEmpty()) {
            displayAlert(4);
            return false;
        }
        return true;
    }

    /**
     * Checks for empty field.
     * @param country
     * @return boolean.
     */
    private boolean validateCountry(String country) {
        if(country == null) {
            displayAlert(5);
            return false;
        }
        return true;
    }
    /**
     * A function that display alerts for particular errors
     * @param alertType type of alert
     */
    private void displayAlert(int alertType) {
        if (alertType == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, type a name for the new customer");
            alert.showAndWait();
        }
        if (alertType == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, type a postal code for the new customer");
            alert.showAndWait();
        }
        if (alertType == 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Please, type phone number for the customer");
            alert.showAndWait();
        }
        if (alertType == 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, type an address for the new customer");
            alert.showAndWait();
        }
        if (alertType == 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, choose a country for the new customer");
            alert.showAndWait();
        }
        if (alertType == 7) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, choose a division for the new customer");
            alert.showAndWait();
        }
    }

    /**
     * Initializes controller by populating country combo box.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        countryCombo.setItems(getAllCountries());
    }
}
