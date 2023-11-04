package Controller;

import Model.Appointments;
import helper.AppointmentDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller that handles main screen.
 *
 * @author Ruben Rios,
 */
public class MainMenuController implements Initializable {
    @FXML private TableColumn<Appointments, Integer> appIdColumn;
    @FXML private TableColumn<Appointments, Integer> contactIdColumn;
    @FXML private TableColumn<Appointments, Integer> customerIdColumn;
    @FXML private TableColumn<Appointments, String> descriptionColumn;
    @FXML private TableColumn<Appointments, LocalDateTime> endDateColumn;
    @FXML private TableColumn<Appointments, String> locationColumn;
    @FXML private TableView<Appointments> menuTableView;
    @FXML private TableColumn<Appointments, LocalDateTime> startDateColumn;
    @FXML private TableColumn<Appointments, String> titleColumn;
    @FXML private TableColumn<Appointments, String> typeColumn;
    @FXML private TableColumn<Appointments, Integer> userIdColumn;
    @FXML private ToggleGroup TG = new ToggleGroup();
    @FXML private RadioButton allRadioBtn;
    @FXML private RadioButton monthRadioBtn;
    @FXML RadioButton weekRadioBtn;
    private String confirmedDeletion;
    private static Appointments selectedAppointment;

    public static Appointments selectedApt() {return selectedAppointment;}
    @FXML
    void onAddAppointmentsBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/AddApt.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Button that takes user to appointment modification screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onModifyAppointmentBtn(ActionEvent event) throws IOException {
        selectedAppointment = menuTableView.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null) {
            displayAlert();
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyApt.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * Event that deletes a selected appointment by user.
     *
     * <p> LAMBDA EXPRESSION--
     *     I included the lambda function in this section of code.
     *     It helps to make the code more concise and avoid making another variable
     *     to hold the user's response.
     * </p>
     * @param event
     * @throws SQLException
     * @throws IOException
     */
    @FXML
    protected void onDeleteAppointmentBtn(ActionEvent event) throws SQLException, IOException {
        selectedAppointment = menuTableView.getSelectionModel().getSelectedItem();
        if(selectedAppointment == null) {
            displayAlert();
        }else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete the selected appointment?");
            alert.showAndWait().ifPresent(result -> {
                if(result == ButtonType.OK){
                    AppointmentDB.deleteAppointment(selectedAppointment.getAptId());
                    confirmedDeletion = "The following " + selectedAppointment.getType() +  " appointment with ID: " + selectedAppointment.getAptId() + " was deleted";

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.setContentText(confirmedDeletion);
                    alert.showAndWait();

                    Parent parent = null;
                    try {
                        parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();
                }

            });
        }
    }

    /**
     * Event that takes user to main customer screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onViewCustomersBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Event that takes user to report screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onViewReportBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/Report.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Event that populates table view with appointments.
     * @param event
     */
    @FXML
    void onViewAllCustomers(ActionEvent event) {
        menuTableView.setItems(AppointmentDB.getAllApps());
        appIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }

    /**
     * Event that shows appointments by current month.
     * @param event
     */
    @FXML
    void onViewAppointmentByMonth(ActionEvent event){
        if(AppointmentDB.getAppsByMonth().isEmpty()) { //when list is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Found");
            alert.setContentText("Appointments by value selected are not found");
            alert.show();
        }
        menuTableView.setItems(AppointmentDB.getAppsByMonth());
        appIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }
    /**
     * Event that shows appointments by current week.
     * @param event
     */
    @FXML
    void onViewAppointmentByWeek(ActionEvent event){
        if(AppointmentDB.getAppsByWeek().isEmpty()) { //when list is empty
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Not Found");
            alert.setContentText("Appointments by value selected are not found");
            alert.show();
        }
        menuTableView.setItems(AppointmentDB.getAppsByWeek());
        appIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }
    /**
     * Event that logs user out.
     * @param event
     * @throws IOException
     */
    @FXML
    void onSignOutBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Method that displays an alert.
     */
    private void displayAlert() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Alert");
        alert.setContentText("Please, select an appointment");
        alert.showAndWait();
    }

    /**
     * Initializes controller by populating table view with appointments.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        allRadioBtn.setToggleGroup(TG);
        monthRadioBtn.setToggleGroup(TG);
        weekRadioBtn.setToggleGroup(TG);

        menuTableView.setItems(AppointmentDB.getAllApps());
        appIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
    }
}
