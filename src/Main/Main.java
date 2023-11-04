package Main;

import com.sun.javafx.runtime.VersionInfo;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
/**
 * A class that initiates the application.
 *
 * @author Ruben Rios.
 */
public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader =  new FXMLLoader(Main.class.getResource("/view/LoginForm.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Appointment Scheduler");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        JDBC.openConnection(); //established a connection with DB
        launch(args);
        JDBC.closeConnection();// close to free up resources
    }
}