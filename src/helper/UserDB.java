package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * A Class that interacts with database and controllers.
 *
 * @author Ruben Rios.
 */
public class UserDB {
    /**
     * A method that checks for username and password credentials.
     * @param username
     * @param password
     * @return boolean.
     */
    public static Boolean signIn(String username,String password) {
        try {
            String sql = "SELECT * FROM users WHERE User_Name ='" + username + "' AND Password ='" + password + "'";
            PreparedStatement ps = JDBC.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                if (rs.getString("User_Name").equals(username) &&
                        rs.getString("Password").equals(password)) {
                    return true;
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }
}
