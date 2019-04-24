package android.project.handmedown;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    Context context;
    SharedPreferences sharedPreferences;

    private String name;
    private String email;


    public  User(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("userinfo",context.MODE_PRIVATE);

    }

    public  void remove(){
        sharedPreferences.edit().clear().commit();
    }
    public String getName() {
        sharedPreferences.getString("username","");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("username",name).commit();
    }

    public String getEmail() {
        sharedPreferences.getString("useremail","");
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        sharedPreferences.edit().putString("useremail",email).commit();
    }
}
