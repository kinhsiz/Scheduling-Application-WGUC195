package Controller;
import Model.Appointments;
import Model.Report;
import helper.AppointmentDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

/**
 * Controller that handles report screen.
 *
 * @author Ruben Rios.
 */
public class ReportController implements Initializable {
    @FXML private TableColumn<Appointments, Integer> appIdColumn;
    @FXML private TableColumn<Report, Integer> appointment_a;
    @FXML private TableColumn<Report, Integer> appointment_b;
    @FXML private ComboBox contactCombo;
    @FXML private TableColumn<Appointments, Integer> contactIdColumn;
    @FXML private TableColumn<Report, String> country_b;
    @FXML private TableColumn<Appointments, Integer> customerIdColumn;
    @FXML private TableColumn<Appointments, String> descriptionColumn;
    @FXML private TableColumn<Appointments, LocalDateTime> endDateColumn;
    @FXML private TableColumn<Appointments, String> locationColumn;
    @FXML private TableView<Appointments> menuTableView;
    @FXML private TableColumn<Report, String> month_a;
    @FXML private TableColumn<Appointments, LocalDateTime> startDateColumn;
    @FXML private TableView<Report> table_a;
    @FXML private TableView<Report> table_b;
    @FXML private TableColumn<Appointments, String> titleColumn;
    @FXML private TableColumn<Appointments, String > typeColumn;
    @FXML private TableColumn<Report, String> type_a;
    /**
     * Array for contact names.
     */
    ObservableList<String> contacts = FXCollections.observableArrayList("Anika Acosta", "Daniel Garcia", "Li Lee");

    /**
     * Event that takes user to main menu screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void mainScreenBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Event that populates table view based on contact name.
     * @param event
     */
    @FXML
    private void OnContactOptionBtn(ActionEvent event) {
        int contactSelection = contactCombo.getSelectionModel().getSelectedIndex();
        if(contactSelection == 0) {
            menuTableView.setItems(AppointmentDB.getAppsByContact(1));
            appIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        } else if (contactSelection == 1) {
            menuTableView.setItems(AppointmentDB.getAppsByContact(2));
            appIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        } else {
            menuTableView.setItems(AppointmentDB.getAppsByContact(3));
            appIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
            endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        }
    }

    /**
     * Initializes controller by populating table views.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactCombo.setItems(contacts);
        contactCombo.getSelectionModel().selectFirst();

        menuTableView.setItems(AppointmentDB.getAppsByContact(1));
        appIdColumn.setCellValueFactory(new PropertyValueFactory<>("aptId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        contactIdColumn.setCellValueFactory(new PropertyValueFactory<>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDateTime"));
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDateTime"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));

        table_a.setItems(AppointmentDB.totalByType());
        type_a.setCellValueFactory(new PropertyValueFactory<>("type"));
        month_a.setCellValueFactory(new PropertyValueFactory<>("month"));
        appointment_a.setCellValueFactory(new PropertyValueFactory<>("total"));

        table_b.setItems(AppointmentDB.totalByCountry());
        country_b.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointment_b.setCellValueFactory(new PropertyValueFactory<>("total"));
    }
}
