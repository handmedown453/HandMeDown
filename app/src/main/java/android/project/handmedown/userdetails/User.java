package android.project.handmedown.userdetails;

public class User {

   private String Firstname,Lastname,Email,password;
   private int Phone;


    public User(){

    }

    public int getPhone() {
        return Phone;
    }

    public void setPhone(int age) {
        Phone = age;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLastname() {
        return Lastname;
    }

    public void setLastname(String lastname) {
        Lastname = lastname;
    }

    public String getFirstname() {
        return Firstname;
    }

    public void setFirstname(String firstname) {
        Firstname = firstname;
    }
}
