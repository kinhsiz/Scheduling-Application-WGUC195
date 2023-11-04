package Controller;

import Model.Appointments;
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
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;

import static helper.AppointmentDB.checkOverlappedAppt;

/**
 * Controller that handles appointment modifications.
 *
 * @author Ruben Rios.
 */
public class ModifyAptController implements Initializable {
    @FXML private TextField appointmentIDText;
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
    Appointments selectedAppointment;

    /**
     * Method that returns an array of customer ids.
     * @return array of integers.
     */
    private ObservableList<Integer> getCustomerID() { //gets customer ID from Customer DB Observable array
        ObservableList<Customers> customerID = CustomersDB.getAllCustomers();
        ObservableList<Integer> customerIDs = FXCollections.observableArrayList();

        for(Customers customer : customerID) {
            customerIDs.add(customer.getCustomerId());
        }
        return customerIDs;
    }

    /**
     * Array for contact names.
     */
    ObservableList<String> contacts = FXCollections.observableArrayList("Anika Acosta", "Daniel Garcia", "Li Lee");
    /**
     * Array for user Ids.
     */
    ObservableList<Integer> userId = FXCollections.observableArrayList(1, 2);
    /**
     * Array for appointment type.
     */
    ObservableList<String> type = FXCollections.observableArrayList("Planning Session", "De-Briefing","General Consulting","Specific Consulting");

    /**
     * Event that returns user to main screen.
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
     * Event that updates modified appointment.
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    void onUpdateAppBtn(ActionEvent event) throws SQLException, IOException {
        int appId = Integer.valueOf(appointmentIDText.getText());
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

        LocalDateTime formattedStartDate = convertToLocalDateTime(startDate, startTime);
        LocalDateTime formattedEndDate= convertToLocalDateTime(endDate, endTime );
        int contactID = AddAptController.getIdFromContact(contact);

        if(validateDate(startDate, endDate) && validateTextInput(title, description, location) &&
                validateTime(startTime, endTime)) {

            if(checkOverlappedAppt(customerId, formattedStartDate, contactID)){
                AppointmentDB.updateAppointment(appId, title, description, location,type,contactID,customerId,userId,formattedStartDate, formattedEndDate);

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
     * Checks for date requirements.
     * @param startDate
     * @param endDate
     * @return boolean.
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
     * Checks for empty fields.
     * @param title
     * @param description
     * @param location
     * @return boolean.
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
     * Checks for time requirements.
     * @param startTime
     * @param endTime
     * @return boolean.
     */
    private boolean validateTime(LocalTime startTime, LocalTime endTime){
        if(endTime.isBefore(startTime) || startTime.equals(endTime)) {
            displayAlert(11);
            return false;
        }
        return true;
    }

    /**
     * Combines date and time into LocalDateTime.
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
     * return contact id.
     * @param contactID
     * @return
     */
    public int returnContactName(int contactID){
        if(contactID == 1) {
            return 0;
        } else if (contactID == 2) {
            return 1;
        } else {
            return 2;
        }
    }

    /**
     * Method that display alert type.
     * @param alertType
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
            alert.setContentText("The appointment was successfully updated");
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
     * Initializes controller by populating pre-defined user choices.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
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

        selectedAppointment = MainMenuController.selectedApt();
        appointmentIDText.setText(String.valueOf(selectedAppointment.getAptId()));
        titleText.setText((selectedAppointment.getTitle()));
        descriptionText.setText(selectedAppointment.getDescription());
        locationText.setText(selectedAppointment.getLocation());
        comboBoxType.getSelectionModel().select(selectedAppointment.getType());
        choiceContactId.getSelectionModel().select(returnContactName(selectedAppointment.getContactId()));
        choiceUserId.getSelectionModel().select(Integer.valueOf(selectedAppointment.getUserId()));
        choiceCustomerId.getSelectionModel().select(Integer.valueOf(selectedAppointment.getCustomerId()));
        startDateText.setValue(selectedAppointment.getStartDateTime().toLocalDateTime().toLocalDate());
        endDateText.setValue(selectedAppointment.getEndDateTime().toLocalDateTime().toLocalDate());
        startTimeCombo.setValue(selectedAppointment.getStartDateTime().toLocalDateTime().toLocalTime());
        endTimeCombo.setValue(selectedAppointment.getEndDateTime().toLocalDateTime().toLocalTime());
    }
}
