package Model;

/**
 * A user class model.
 *
 * @author Ruben Rios.
 */
public class Users {
    private String userName;
    private String userPassword;

    /**
     * A constructor to create user object.
     * @param userName
     * @param userPassword
     */
    public Users(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    /**
     * A method to get username.
     * @return string.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * A method to get user password.
     * @return string.
     */
    public String getUserPassword() {
        return userPassword;
    }
}
