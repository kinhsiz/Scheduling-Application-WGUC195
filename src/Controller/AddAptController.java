package Controller;

import Model.Customers;
import helper.AppointmentDB;
import helper.CustomersDB;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static helper.AppointmentDB.checkOverlappedAppt;

/**
 * A controller that handles addition of new appointments.
 *
 * @author Ruben Rios
 */

public class AddAptController implements Initializable {
    @FXML private ComboBox<String> choiceContactId;
    @FXML private ComboBox<Integer> choiceCustomerId;
    @FXML private ComboBox<Integer> choiceUserId;
    @FXML private ComboBox<String> comboBoxType;
    @FXML private ComboBox<LocalTime> startTimeCombo;
    @FXML private  ComboBox<LocalTime> endTimeCombo;
    @FXML private DatePicker startDateText;
    @FXML private DatePicker endDateText;
    @FXML private TextField titleText;
    @FXML private TextField descriptionText;
    @FXML private TextField locationText;

    /**
     *  Method to get an array of customer ids *
     *  <p>
     *      LAMBDA EXPRESSION-- *
     *      This expression adds all customer ids obtained from a list of customers.
     *      It simplifies code by using less lines and makes it more readable as well.
     *  </p>
     *
     */

    protected ObservableList<Integer> getCustomerID() { //gets customer ID from Customer DB Observable array
        ObservableList<Customers> customerID = CustomersDB.getAllCustomers();
        ObservableList<Integer> customerIDs = FXCollections.observableArrayList();

        //lambda
        customerID.forEach(customers -> customerIDs.add(customers.getCustomerId()));

        /* for(Customers customer : customerID) {
            customerIDs.add(customer.getCustomerId());
        } */
        return customerIDs;
    }
    ObservableList<String> contacts = FXCollections.observableArrayList("Anika Acosta", "Daniel Garcia", "Li Lee");
    ObservableList<Integer> userId = FXCollections.observableArrayList(1, 2);
    ObservableList<String> type = FXCollections.observableArrayList("Planning Session", "De-Briefing","General Consulting","Specific Consulting");

    /**
     * Action event that saves a new appointment to the database.
     * @param event saves new appointment.
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onSaveAppBtn(ActionEvent event) throws SQLException, IOException {
        int appId = AppointmentDB.generateNewAppId(); //generates a new appointment ID
        String title = titleText.getText();
        String description = descriptionText.getText();
        String location = locationText.getText();
        String type = comboBoxType.getSelectionModel().getSelectedItem();
        String contact = choiceContactId.getSelectionModel().getSelectedItem();
        Integer customerId = choiceCustomerId.getSelectionModel().getSelectedItem();
        Integer userId = choiceUserId.getSelectionModel().getSelectedItem();
        LocalDate startDate = startDateText.getValue();
        LocalDate endDate = endDateText.getValue();
        LocalTime startTime = startTimeCombo.getValue();
        LocalTime endTime = endTimeCombo.getValue();

        int contactID = getIdFromContact(contact);

        if(validateDate(startDate, endDate) && validateTextInput(title, description, location) &&
                validateComboOptions(type, contact, customerId,userId) && validateTime(startTime, endTime)) {

            LocalDateTime formattedStartDate = convertToLocalDateTime(startDate, startTime);
            LocalDateTime formattedEndDate= convertToLocalDateTime(endDate, endTime );

            if(checkOverlappedAppt(customerId, formattedStartDate, contactID)){
                AppointmentDB.addAppointment(appId, title, description, location,type,contactID,customerId,userId,formattedStartDate, formattedEndDate);

                displayAlert(13);
                Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/MainMenu.fxml")));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(scene);
                stage.show();
            } else {
                displayAlert(14);
            }
        }
    }

    /**
     * Event that takes user to main screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onCancelBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Validates user date input
     * @param startDate initial time
     * @param endDate end time
     * @return
     */
    private boolean validateDate(LocalDate startDate, LocalDate endDate) {
        if(startDate == null || endDate == null) {
            displayAlert(1);
            return false;
        }
        if(startDate.isBefore(LocalDate.now())) {
            displayAlert(2);
            return false;
        }
        if(endDate.isBefore(startDate)) {
            displayAlert(3);
            return false;
        }
        if(startDate.getDayOfWeek() == DayOfWeek.SATURDAY || endDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
                endDate.getDayOfWeek() == DayOfWeek.SUNDAY || endDate.getDayOfWeek() == DayOfWeek.SUNDAY){
            displayAlert(12);
            return false;
        }
        return true;
    }

    /**
     * Validates user text input for empty text fields.
     * @param title title
     * @param description description
     * @param location location
     * @return boolean
     */
    private boolean validateTextInput(String title, String description, String location) {
        if (title.isEmpty()) {
            displayAlert(4);
            return false;
        }
        if(description.isEmpty()) {
            displayAlert(5);
            return false;
        }
        if (location.isEmpty()){
            displayAlert(6);
            return false;
        }
        return true;
    }

    /**
     * Checks for empty fields
     * @param type
     * @param contact
     * @param customerId
     * @param userId
     * @return boolean
     */
    private boolean validateComboOptions(String type, String contact, Integer customerId, Integer userId) {
        if(type == null) {
            displayAlert(10);
            return false;
        }
        if(contact == null) {
            displayAlert(9);
            return false;
        }
        if(customerId == null) {
            displayAlert(7);
            return false;
        }
        if (userId == null) {
            displayAlert(8);
            return false;
        }
        return true;
    }

    /**
     * Checks for time requirements
     * @param startTime
     * @param endTime
     * @return boolean
     */
    private boolean validateTime(LocalTime startTime, LocalTime endTime){
        if(endTime.isBefore(startTime) || startTime.equals(endTime)) {
            displayAlert(11);
              return false;
        }
        return true;
    }
//LocalDate — A date without a time-zone. LocalTime — A time without a time-zone. LocalDateTime — A date-time without a time-zone.

    /**
     * Combines date and time to LocalDateTime.
     * @param date
     * @param time
     * @return LocalDateTime value.
     */
    public LocalDateTime convertToLocalDateTime(LocalDate date, LocalTime time){
        String convertStr = date + " " + time + ":00.0";
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME
                .ofPattern("yyy-MM-dd HH:mm:ss.S");
        LocalDateTime dateAndTime = LocalDateTime.parse(convertStr, formatter);

        return dateAndTime;
    }

    /**
     * Converts string from customer name to integer equivalent.
     * @param contact
     * @return integer
     */
    public static int getIdFromContact(String contact) {
        int contactID = 0;
        if(contact == "Anika Acosta"){
            contactID =1;
        } else if (contact == "Daniel Garcia") {
            contactID =2;
        } else {
            contactID=3;
        }
        return contactID;
    }

    /**
     * A function that display alerts for particular errors
     * @param alertType type of alert
     */
    private void displayAlert(int alertType) {
        if(alertType == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, choose a start and end appointment date");
            alert.showAndWait();
        }
        if(alertType == 2){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, choose a date after today");
            alert.showAndWait();
        }
        if(alertType == 3){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Confirmation Dialog");
            alert.setContentText("Please, for end date choose a date after start date");
            alert.showAndWait();
        }
        if(alertType == 4) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, type a title for the appointment");
            alert.showAndWait();
        }
        if(alertType == 5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, type a description for the appointment");
            alert.showAndWait();
        }
        if(alertType == 6) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, type a location for the appointment");
            alert.showAndWait();
        }
        if(alertType == 7) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, choose a Customer ID");
            alert.showAndWait();
        }
        if(alertType == 8) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, choose a User ID");
            alert.showAndWait();
        }
        if(alertType == 9) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, choose a contact name");
            alert.showAndWait();
        }
        if(alertType == 10) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, choose an appointment type");
            alert.showAndWait();
        }
        if(alertType == 11) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, choose a later end time for the appointment");
            alert.showAndWait();
        }
        if(alertType == 12) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, choose a day other than Saturday or Sunday");
            alert.showAndWait();
        }
        if(alertType == 13) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation Dialogue");
            alert.setContentText("The appointment was successfully added");
            alert.showAndWait();
        }
        if(alertType == 14) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmation Dialogue");
            alert.setContentText("There is an overlapping appointment with the selected customer. Please, " +
                            "choose a different time or date");
            alert.showAndWait();
        }
    }

    /**
     * Initializes controller by populating combo boxes
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getCustomerID();

        choiceContactId.setItems(contacts);

        choiceCustomerId.setItems(getCustomerID()); //IS THIS VALID?

        choiceUserId.setItems(userId);

        comboBoxType.setItems(type);
     //   comboBoxType.getSelectionModel().selectFirst();

        LocalTime startTime = LocalTime.of(8, 0);
        LocalTime endTime = LocalTime.of(22, 0);

        while(startTime.isBefore(endTime.plusSeconds(1))){
            startTimeCombo.getItems().add(startTime);
            endTimeCombo.getItems().add(startTime);
            startTime = startTime.plusMinutes(60);
        }
        startTimeCombo.getSelectionModel().selectFirst();
        endTimeCombo.getSelectionModel().selectFirst();

    }
}