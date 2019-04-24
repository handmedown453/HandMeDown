package android.project.handmedown;

import android.content.Context;
import android.content.SharedPreferences;

public class User {

    Context context;
    SharedPreferences sharedPreferences;

    private String name;



    public  User(Context context)
    {
        this.context=context;
        sharedPreferences=context.getSharedPreferences("userinfo",context.MODE_PRIVATE);

    }

    public  void remove(){
        sharedPreferences.edit().clear().commit();
    }
    public String getName() {
        sharedPreferences.getString("userdata","");
        return name;
    }

    public void setName(String name) {
        this.name = name;
        sharedPreferences.edit().putString("userdata",name).commit();
    }
}
