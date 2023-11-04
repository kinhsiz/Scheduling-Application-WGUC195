package helper;

import Model.Appointments;
import Model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.*;

/**
 * Class used for database and controller interaction.
 *
 * @author Ruben Rios.
 */
public class AppointmentDB  {
    /**
     * Method to retrieve all appointments from database.
     * @return Appointment array.
     */
    public static ObservableList<Appointments> getAllApps() {
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList(); //list to return
        String sql = "SELECT * from appointments"; //setup sql
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //prepare statement
            ResultSet rs = ps.executeQuery();
            while(rs.next()) { //pull db data out
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                Timestamp createDate =rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments appointments = new Appointments(id,title,description,location,type,startDate,endDate,createDate,
                        createdBy,lastUpdated,lastUpdatedBy,customerID,userID,contactID); //create an object instance of Countries
                appointmentList.add(appointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Adds new appointment to databse.
     * @param aptID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param contact
     * @param customerId
     * @param userId
     * @param startDate
     * @param endDate
     * @throws SQLException
     */
    public static void addAppointment(int aptID, String title, String description, String location, String type, int contact,
                                      int customerId, int userId, LocalDateTime startDate, LocalDateTime endDate) throws SQLException {

        /*ZonedDateTime defaultStartLDT = startDate.atZone(ZoneId.systemDefault()); //will get zoned local date time ---2023-03-23T21:41:01.798831400-05:00[America/Chicago]
        ZonedDateTime defaultEndLDT = endDate.atZone(ZoneId.systemDefault());
        ZonedDateTime convertLocalStartToUTC = defaultStartLDT.withZoneSameInstant(ZoneId.of("UTC")); //Converts local date time to UTC  ---2023-03-24T02:41:01.798831400Z[UTC]
        ZonedDateTime convertLocalEndToUTC = defaultEndLDT.withZoneSameInstant(ZoneId.of("UTC"));

        LocalDateTime utcStartToLDT = convertLocalStartToUTC.toLocalDateTime(); //2023-03-24T02:41:01.798831400
        LocalDateTime utcEndToLDT = convertLocalEndToUTC.toLocalDateTime();*/

        Timestamp start = Timestamp.valueOf(startDate);
        Timestamp end = Timestamp.valueOf(endDate);
        Timestamp createNow = Timestamp.valueOf(LocalDateTime.now());
        Timestamp lastUpdateNow = Timestamp.valueOf(LocalDateTime.now());


        try {
            String sql = "INSERT INTO appointments(Appointment_ID, Title, Description, Location, Type, Contact_ID, Customer_ID, User_ID, Start, " +
                    "End,Create_Date, Created_By, Last_Update, Last_Updated_By) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, aptID);
            ps.setString(2, title);
            ps.setString(3, description);
            ps.setString(4, location);
            ps.setString(5, type);
            ps.setInt(6, contact);
            ps.setInt(7, customerId);
            ps.setInt(8,userId);
            ps.setTimestamp(9, start);
            ps.setTimestamp(10, end);
            ps.setTimestamp(11, createNow);
            ps.setString(12, "");
            ps.setTimestamp(13, lastUpdateNow);
            ps.setString(14, "");
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("appointment successfully added");
    }

    /**
     * Updates selected appointment to database.
     * @param aptID
     * @param title
     * @param description
     * @param location
     * @param type
     * @param contact
     * @param customerId
     * @param userId
     * @param startDate
     * @param endDate
     */
    public static void updateAppointment(int aptID, String title, String description, String location, String type, int contact,
                                      int customerId, int userId, LocalDateTime startDate, LocalDateTime endDate) {
      /*  ZonedDateTime defaultStartLDT = startDate.atZone(ZoneId.systemDefault()); //will get zoned local date time ---2023-03-23T21:41:01.798831400-05:00[America/Chicago]
        ZonedDateTime defaultEndLDT = endDate.atZone(ZoneId.systemDefault());
        ZonedDateTime convertLocalStartToUTC = defaultStartLDT.withZoneSameInstant(ZoneId.of("UTC")); //Converts local date time to UTC  ---2023-03-24T02:41:01.798831400Z[UTC]
        ZonedDateTime convertLocalEndToUTC = defaultEndLDT.withZoneSameInstant(ZoneId.of("UTC"));

        LocalDateTime utcStartToLDT = convertLocalStartToUTC.toLocalDateTime(); //2023-03-24T02:41:01.798831400
        LocalDateTime utcEndToLDT = convertLocalEndToUTC.toLocalDateTime();*/

        Timestamp start = Timestamp.valueOf(startDate);
        Timestamp end = Timestamp.valueOf(endDate);
        Timestamp createNow = Timestamp.valueOf(LocalDateTime.now());
        Timestamp lastUpdateNow = Timestamp.valueOf(LocalDateTime.now());

        try {
            String sql = "UPDATE appointments SET Title=?, Description=?, Location=?, Type=?, Contact_ID=?, Customer_ID=?, User_ID=?, Start=?, " +
                    "End=?,Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=? WHERE Appointment_ID =?";

            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, title);
            ps.setString(2, description);
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setInt(5, contact);
            ps.setInt(6, customerId);
            ps.setInt(7,userId);
            ps.setTimestamp(8, start);
            ps.setTimestamp(9, end);
            ps.setTimestamp(10, createNow);
            ps.setString(11, " ");
            ps.setTimestamp(12, lastUpdateNow);
            ps.setString(13, "");
            ps.setInt(14, aptID);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("appointment successfully updated");
    }

    /**
     * Deletes a selected appointment from database.
     * @param aptID
     */
    public static void deleteAppointment(Integer aptID) {
        try {
            String sql = "DELETE FROM appointments WHERE Appointment_ID= '"+aptID+"'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * Method that gets all customer ids from database.
     * @return an array of customer ids.
     */
    public static ObservableList<Integer> getAllCustomerIDs(){
        ObservableList<Integer> allCustomerIDs = FXCollections.observableArrayList();
        String sql = "SELECT Customer_ID from appointments";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //prepare statement
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                int customerId = rs.getInt("Customer_ID");
                allCustomerIDs.add(customerId);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return allCustomerIDs;
    }

    /**
     * Retrieves appointments scheduled for the current month.
     * @return Appointment array.
     */
    public static ObservableList<Appointments> getAppsByMonth() {
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();
      //  LocalDate localDate = LocalDate.now();

        ObservableList<Appointments> appointmentListByMonth = FXCollections.observableArrayList(); //list to return
        String sql = "SELECT * from appointments " +
                "WHERE monthname(start) = + '" + currentMonth +"'";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //prepare statement
            ResultSet rs = ps.executeQuery();
            while(rs.next()) { //pull db data out
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                Timestamp createDate =rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments appointmentsByMonth = new Appointments(id,title,description,location,type,startDate,endDate,createDate,
                        createdBy,lastUpdated,lastUpdatedBy,customerID,userID,contactID); //create an object instance of Countries
                appointmentListByMonth.add(appointmentsByMonth);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentListByMonth;
    }

    /**
     * Retrieves a list of appointments for the current week.
     * @return Appointment array.
     */
    public static ObservableList<Appointments> getAppsByWeek() {
        LocalDate currentDate = LocalDate.now();
        LocalDate week = LocalDate.now().plusDays(7);

        ObservableList<Appointments> appointmentListByWeek= FXCollections.observableArrayList(); //list to return
        String sql = "SELECT * from appointments " +
                "WHERE start >= '"+ currentDate + "' AND start <= '"+ week +"'";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //prepare statement
            ResultSet rs = ps.executeQuery();
            while(rs.next()) { //pull db data out
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                Timestamp createDate =rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments appointmentsByWeek = new Appointments(id,title,description,location,type,startDate,endDate,createDate,
                        createdBy,lastUpdated,lastUpdatedBy,customerID,userID,contactID); //create an object instance of Countries
                appointmentListByWeek.add(appointmentsByWeek);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentListByWeek;
    }

    /**
     * Method that checks if there is an upcoming appointment for the next 15 minutes.
     * @return Appointment array.
     */
    public static Appointments check15minuteOverlap() {
        Appointments appt;
        LocalDateTime now = LocalDateTime.now(); //get current user time
        ZoneId systemZoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDT = now.atZone(systemZoneId);
        LocalDateTime localDT = zonedDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        LocalDateTime localDT15 = localDT.plusMinutes(15);

            String sql ="SELECT * from appointments " +
                    "WHERE start >= '"+ localDT + "' AND start <= '"+ localDT15 +"'";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //prepare statement
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                Timestamp createDate =rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments appointments = new Appointments(id,title,description,location,type,startDate,endDate,createDate,
                        createdBy,lastUpdated,lastUpdatedBy,customerID,userID,contactID); //create an object instance of Appointment
                return appointments;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    /**
     * Checks for any overlapped appointment when creating a new one.
     * @param id
     * @param start
     * @param contactId
     * @return boolean.
     */
    public static boolean checkOverlappedAppt(int id, LocalDateTime start, int contactId){
        ZoneId systemZoneId = ZoneId.systemDefault();
        ZonedDateTime zonedDT = start.atZone(systemZoneId);
        LocalDateTime localDT = zonedDT.withZoneSameInstant(ZoneId.of("UTC")).toLocalDateTime();
        String sql ="SELECT * from appointments " +
                "WHERE Customer_ID = '"+ id + "' AND Start='"+ localDT +"'";
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }    return true;
    }


    /**
     * Generates a new appointment id for a new appointment.
     * @return a new appointment id.
     * @throws SQLException
     */
    public static Integer generateNewAppId() throws SQLException {
        Integer appId = 1;
        try {
            String sql = "SELECT Appointment_ID FROM appointments ORDER BY Appointment_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getInt("Appointment_ID") == appId) {
                        appId++;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appId;
    }

    /**
     * Retrives all appointments with a specific contact id.
     * @param contactId
     * @return appointment array.
     */
    public static ObservableList<Appointments> getAppsByContact(int contactId) {
        ObservableList<Appointments> appointmentList = FXCollections.observableArrayList(); //list to return
        try {
            String sql = "SELECT * FROM appointments WHERE Contact_ID = '" +contactId+ "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Appointment_ID");
                String title = rs.getString("Title");
                String description = rs.getString("Description");
                String location = rs.getString("Location");
                String type = rs.getString("Type");
                Timestamp startDate = rs.getTimestamp("Start");
                Timestamp endDate = rs.getTimestamp("End");
                Timestamp createDate =rs.getTimestamp("Create_Date");
                String createdBy = rs.getString("Created_By");
                Timestamp lastUpdated = rs.getTimestamp("Last_Update");
                String lastUpdatedBy = rs.getString("Last_Updated_By");
                int customerID = rs.getInt("Customer_ID");
                int userID = rs.getInt("User_ID");
                int contactID = rs.getInt("Contact_ID");

                Appointments appointments = new Appointments(id,title,description,location,type,startDate,endDate,createDate,
                        createdBy,lastUpdated,lastUpdatedBy,customerID,userID,contactID);

                appointmentList.add(appointments);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return appointmentList;
    }

    /**
     * Retrieves a total number of appointment by specific appointment type.
     * @return report array.
     */
    public static ObservableList<Report> totalByType(){
        ObservableList<Report> reportList = FXCollections.observableArrayList(); //list to return
        try {
            String sql = "SELECT Type, MONTHNAME(start) as 'Month', COUNT(*) as 'Count'" + //
                    "FROM appointments GROUP BY Type, MONTHNAME(start)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String month = rs.getString("Month");
                String type = rs.getString("Type");
                int count = rs.getInt("Count");
                Report report = new Report(type, month, count);
                reportList.add(report);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return reportList;
    }

    /**
     * Retrieves total number of appointments by customer id.
     * @return report array.
     */
    public static ObservableList<Report> totalByCountry(){
        ObservableList<Report> reportList = FXCollections.observableArrayList(); //list to return
        try {
            String sql = "SELECT Customer_ID, COUNT(*) as 'Count'" + //
                    "FROM appointments GROUP BY Customer_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String customer = rs.getString("Customer_ID");
                int count = rs.getInt("Count");
                Report report = new Report(customer, "empty", count);
                reportList.add(report);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return reportList;
    }
}
