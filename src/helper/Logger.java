package helper;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

public class Logger {
    private static final String FILENAME = "login_activity.txt"; //creates a new file

    public Logger() {}
    public static void log (String username, boolean attempt, String message) {
        try (FileWriter fw = new FileWriter(FILENAME, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter pw = new PrintWriter(bw)) {
            pw.println(username + (attempt? " Successful" : " Failed") + " " + message + " " + ZonedDateTime.now());
        } catch (IOException e) {
            System.out.println("Logger Error: " + e.getMessage());
        }
    }
}
