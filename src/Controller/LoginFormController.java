package Controller;
import Model.Appointments;
import helper.AppointmentDB;
import helper.Logger;
import helper.UserDB;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Controller for user login.
 *
 * @author Ruben Rios
 */
public class LoginFormController implements Initializable {
    @FXML private Label cityNameLabel;
    @FXML private Label passwordText;
    @FXML private PasswordField passwordTxtBox;
    @FXML private Button signInButton;
    @FXML private Label signInText;
    @FXML private Label locationLabel;
    @FXML private Label usernameText;
    @FXML private TextField usernameTxtBox;
    String errorTitle;
    String errorDialogue;
    String stageTitle;

    /**
     * Validates user credentials and displays errors if fail.
     * @param event
     * @throws IOException
     * @throws SQLException
     */
    @FXML
    void SignInButton(ActionEvent event) throws IOException, SQLException { //does not need try-catch, signIn methods catches errors
        String usernameInput = usernameTxtBox.getText().trim(); //trim() removes white space from username
        String passwordInput = passwordTxtBox.getText();
        boolean userAccess = UserDB.signIn(usernameInput, passwordInput);
            if(userAccess) {
                Logger.log(usernameInput, true, "User login attempt");
                Parent parent = FXMLLoader.load(getClass().getResource("/view/MainMenu.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setTitle(stageTitle);
                stage.setScene(scene);
                stage.show();

                Appointments appt = AppointmentDB.check15minuteOverlap();
                if(appt == null) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setContentText("There are not appointments within the next 15 minutes");
                    alert.showAndWait();
                } else {
                    int id = appt.getAptId();
                    Timestamp dateAndTime = appt.getStartDateTime();
                    String message = "The following appointment with ID: " + id + "starts " + dateAndTime +".";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Alert");
                    alert.setContentText(message);
                    alert.showAndWait();
                }
            } else {
                Logger.log(usernameInput, false, "User login attempt");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(errorTitle);
                alert.setContentText(errorDialogue);
                alert.showAndWait();
            }
    }

    /**
     * Initializes controller by choosing system default language.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //get default language and match language from languageLogin
       ResourceBundle rb = resourceBundle.getBundle("Languages/languageLogin", Locale.getDefault());

        //if-loop to check if getDefault value matches with available languages
        if(Locale.getDefault().getLanguage().equals("fr") ||
                Locale.getDefault().getLanguage().equals("en") ) {
                signInText.setText(rb.getString("Title"));
                passwordText.setText(rb.getString("Username"));
                usernameText.setText(rb.getString("Password"));
                signInButton.setText(rb.getString("SignIn"));
                errorTitle = rb.getString("errorTitle");
                errorDialogue = rb.getString("errorDialogue");
                stageTitle= rb.getString("stageTittle");

        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Language");
            alert.setContentText("Your default language is not supported in this application");
            alert.showAndWait();
        }
        ZoneId zone = ZoneId.systemDefault();
        cityNameLabel.setText(String.valueOf(zone));

    /*  LocalDateTime now = LocalDateTime.now();
        ZoneId zoneID = ZoneId.systemDefault();
        ZonedDateTime zdt = now.atZone((ZoneId.systemDefault()));
        ZonedDateTime localToUTC = zdt.withZoneSameInstant(ZoneId.of("UTC"));
        LocalDateTime localUTCtoLDT = zdt.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
     */
    }
}

/*
Notes:
equals() compares two string objects. == compares two strings.
https://careerkarma.com/blog/java-compare-strings/

How to get OS default language
https://wgu.hosted.panopto.com/Panopto/Pages/Viewer.aspx?id=5251c286-743e-410d-b7c9-ab4901120742

 */