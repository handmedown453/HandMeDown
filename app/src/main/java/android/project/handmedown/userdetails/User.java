package android.project.handmedown.userdetails;

/**
 * POJO to store details of a User.
 */
public class User {

    private String Firstname, Lastname, Email, password, lAT, lOG;
    private int Phone;

    /**
     * Default constructor.
     */
    public User() {}

    /**
     * Gets the latitude of the user's location.
     * @return user's latitude
     */
    public String getlAT() {
        return lAT;
    }

    /**
     * Sets the latitude of the user's location.
     * @param lAT user's new latitude
     */
    public void setlAT(String lAT) {
        this.lAT = lAT;
    }

    /**
     * Gets the longitude of the user's location.
     * @return
     */
    public String getlOG() {
        return lOG;
    }

    /**
     * Sets longitude of the user.
     * @param lOG new longitude value
     */
    public void setlOG(String lOG) {
        this.lOG = lOG;
    }

    /**
     * Returns the phone number of the user.
     * @return user's phone number
     */
    public int getPhone() {
        return Phone;
    }

    /**
     * Sets the phone number of the user.
     * @param age new phone number value
     */
    public void setPhone(int age) {
        Phone = age;
    }

    /**
     * Gets the password of the user.
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user.
     * @param password the user's password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Gets the email address of the user.
     * @return user's email address
     */
    public String getEmail() {
        return Email;
    }

    /**
     * Sets the email address of the user.
     * @param email user's email address
     */
    public void setEmail(String email) {
        Email = email;
    }

    /**
     * Gets the last name of the user.
     * @return user's last name
     */
    public String getLastname() {
        return Lastname;
    }

    /**
     * Sets the last name value of the user.
     * @param lastname user's last name
     */
    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    /**
     * Gets the first name of the user.
     * @return user's first name
     */
    public String getFirstname() {
        return Firstname;
    }

    /**
     * Sets the first name of the user
     * @param firstname the value to set as the user's first name
     */
    public void setFirstname(String firstname) {
        Firstname = firstname;
    }
}
