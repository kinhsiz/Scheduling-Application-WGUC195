package helper;

import Model.Customers;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * Class for database and customer controller interaction.
 *
 * @author Ruben Rios
 */
public class CustomersDB {
    public static ObservableList<Customers> getAllCustomers() {
        ObservableList<Customers> customerList = FXCollections.observableArrayList(); //list to return
        String sql = "SELECT * from customers"; //setup sql
        try {
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql); //prepare statement
            ResultSet rs = ps.executeQuery();
            while(rs.next()) { //pull db data out
                int id = rs.getInt("Customer_ID");
                String customerName = rs.getString("Customer_Name");
                String address = rs.getString("Address");
                String postalCode = rs.getString("Postal_Code");
                String phoneNumber = rs.getString("Phone");
                int divisionId = rs.getInt("Division_ID");

                Customers customers = new Customers(id,customerName,address,phoneNumber,postalCode,divisionId);
                customerList.add(customers);
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerList;
    }

    /**
     * Method that creates a new customer id.
     * @return new customer id.
     * @throws SQLException
     */
    public static Integer generateNewCustomerId() throws SQLException {
        Integer customerId = 1;
        try {
            String sql = "SELECT Customer_ID FROM customers ORDER BY Customer_ID";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while(rs.next()) {
                if(rs.getInt("Customer_ID") == customerId) {
                    customerId++;
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return customerId;
    }

    /**
     * Adds new customer to database.
     * @param customerId
     * @param name
     * @param address
     * @param phoneNumber
     * @param postalCode
     * @param divisionId
     * @throws SQLException
     */
    public static void addNewCustomer(int customerId, String name, String address, String phoneNumber, String postalCode, int divisionId) throws SQLException {
        Timestamp createNow = Timestamp.valueOf(LocalDateTime.now());
        Timestamp lastUpdateNow = Timestamp.valueOf(LocalDateTime.now());
        try {
            String sql = "INSERT INTO customers(Customer_ID, Customer_Name, Address, Postal_Code, Phone," +
                    "Create_Date, Created_By, Last_Update, Last_Updated_By, Division_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setInt(1, customerId);
            ps.setString(2, name);
            ps.setString(3, address);
            ps.setString(4, phoneNumber);
            ps.setString(5, postalCode);
            ps.setTimestamp(6, createNow);
            ps.setString(7, "");
            ps.setTimestamp(8, lastUpdateNow);
            ps.setString(9, "");
            ps.setInt(10,divisionId);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Customer successfully added");
    }

    /**
     * Updates selected customer to database.
     * @param customerId
     * @param name
     * @param address
     * @param phoneNumber
     * @param postalCode
     * @param divisionId
     * @throws SQLException
     */
    public static void updateCustomer(int customerId, String name, String address, String phoneNumber, String postalCode, int divisionId) throws SQLException {
        Timestamp createNow = Timestamp.valueOf(LocalDateTime.now());
        Timestamp lastUpdateNow = Timestamp.valueOf(LocalDateTime.now());
        try {
            String sql = "UPDATE customers SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?," +
                    "Create_Date=?, Created_By=?, Last_Update=?, Last_Updated_By=?, Division_ID=? WHERE Customer_ID=?";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, address);
            ps.setString(3, phoneNumber);
            ps.setString(4, postalCode);
            ps.setTimestamp(5, createNow);
            ps.setString(6, "");
            ps.setTimestamp(7, lastUpdateNow);
            ps.setString(8, "");
            ps.setInt(9,divisionId);
            ps.setInt(10, customerId);

            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Customer successfully updated");
    }

    /**
     * Method that deletes a selected customer from database.
     * @param customerId
     */
    public static void deleteCustomer(int customerId){
        try {
            String sql = "DELETE FROM customers WHERE Customer_ID= '"+customerId+"'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
