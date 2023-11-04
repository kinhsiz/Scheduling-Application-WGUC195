package helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * A Class that connects and disconnects database.
 */
public abstract class JDBC { // it cannot be instantiated.
    private static final String protocol = "jdbc";
    private static final String vendor = ":mysql:";
    private static final String location = "//localhost/";
    private static final String databaseName = "client_schedule";
    private static final String jdbcUrl = protocol + vendor + location + databaseName + "?connectionTimeZone = SERVER"; // LOCAL
    private static final String driver = "com.mysql.cj.jdbc.Driver"; // Driver reference
    private static final String userName = "sqlUser"; // Username
    private static String password = "Passw0rd!"; // Password
    public static Connection connection = null;  // Connection Interface

    /**
     * A method that establishes a connection with database.
     * @return connection.
     */
    public static Connection openConnection()
    {
        try {
            Class.forName(driver); // Locate Driver
            //jdbc:mysql://localhost//client_schedule
            connection = DriverManager.getConnection(jdbcUrl, userName, password); // Reference Connection object
            System.out.println("Connection successful!");
        } catch(SQLException e) { //An exception that provides information on a database access error
            e.printStackTrace(); //pinpoint exact line in which the method raised the exception.
            //System.out.println("Error:" + e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
    public static Connection getConnection() {
        return connection;
    }

    /**
     * A method that disconnects a connection to database.
     */
    public static void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed!");
        }
        catch(Exception e)
        {
            System.out.println("Error:" + e.getMessage());
        }
    }
}

/*Note:
Exception e (exceptional event): event that occurs during runtime that disrupts the normal flow of instructions.
Throwing an exception means creating an exception object and handing it to the runtime system
Catch exception takes care of the exception. Also called 'exception handler'.
If there is no exception handler, the program terminates.
Example:
    read_file {
        try {
            openTheFile;
            determine its size;
            allocate that much memory;
        } catch (fileOpenFailed) {
    	doSomething;
        } catch (sizeDeterminationFailed) {
            doSomething;
        } catch (memoryAllocationFailed) {
            doSomething;
        }
    }
 */
