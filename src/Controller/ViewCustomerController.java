package Controller;
import Model.Customers;
import helper.AppointmentDB;
import helper.CustomersDB;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

import java.util.ResourceBundle;
/**
 * Controller that handles customer modifications by user.
 *
 * @author Ruben Rios
 */
public class ViewCustomerController implements Initializable {
    @FXML
    private TableColumn<Customers,String> addressColumn;
    @FXML
    private TableColumn<Customers, Integer> IdColumn;
    @FXML
    private TableColumn<Customers, String> postalCodeColumn;
    @FXML
    private TableColumn<Customers, Integer> divisionIdColumn;
    @FXML
    private TableView<Customers> menuTableView;
    @FXML
    private TableColumn<Customers, String> nameColumn;
    @FXML
    private TableColumn<Customers, String> phoneColumn;
    public static Customers selectedCustomer;
    private String confirmedDeletion;
    /**
     * Method that returns customer information selected by user.
     * @return selected customer information.
     */
    public static Customers selectedCustomer() {return selectedCustomer;
    }
    /**
     * Event that returns user to main menu screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onExitBtn(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Event that takes user to add customer screen.
     * @param event
     * @throws IOException
     */
    @FXML
    void onAddCustomer(ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/view/AddCustomer.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    /**
     * Event that takes user to modify customer information.
     * @param event
     * @throws IOException
     */
    @FXML
    void onModifyCustomer(ActionEvent event) throws IOException {
        selectedCustomer = menuTableView.getSelectionModel().getSelectedItem();
        if(selectedCustomer == null) {
            displayAlert(1);
        } else {
            Parent parent = FXMLLoader.load(getClass().getResource("/view/ModifyCustomer.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        }
    }
    /**
     * Event that deletes selected customer by user.
     *<p>
     *     LAMBDA EXPRESSION----
     *     I included the lambda function in this section of code.
     *     It helps to make the code more concise and avoid making another variable
     *     to hold the user's response.
     *</p>
     * @param event
     * @throws IOException
     */

    @FXML
    void onDeleteCustomerBtn(ActionEvent event) throws IOException {
        selectedCustomer = menuTableView.getSelectionModel().getSelectedItem();
        ObservableList<Integer> allCustomerId = AppointmentDB.getAllCustomerIDs();

        if(selectedCustomer == null) {
            displayAlert(2);
        } else if ((allCustomerId.contains(selectedCustomer.getCustomerId()))) {
            displayAlert(3);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alert");
            alert.setContentText("Are you sure you want to delete the selected customer?");
            //LAMBDA
            alert.showAndWait().ifPresent(result -> {
                if(result == ButtonType.OK){
                    CustomersDB.deleteCustomer(selectedCustomer.getCustomerId());
                    confirmedDeletion = "The following customer with ID: " + selectedCustomer.getCustomerId() + " was deleted";

                    Alert alert1 = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                    alert.setContentText(confirmedDeletion);
                    alert.showAndWait();

                    Parent parent = null;
                    try {
                        parent = FXMLLoader.load(getClass().getResource("/view/ViewCustomers.fxml"));
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
     * Method that display an alert.
     * @param alertType
     */
    private void displayAlert(int alertType) {
        if(alertType == 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, select customer you wish to modify");
            alert.showAndWait();
        }
        if(alertType == 2) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Please, select customer you wish to delete");
            alert.showAndWait();
        }
        if(alertType == 3 ) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setContentText("Before deleting customer information, delete all scheduled appointments with selected customer");
            alert.showAndWait();
        }
    }
    /**
     * Initializes controller by populating table view with customer information.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        menuTableView.setItems(CustomersDB.getAllCustomers());
        IdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        postalCodeColumn.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        divisionIdColumn.setCellValueFactory(new PropertyValueFactory<>("divisionId"));
    }
}
