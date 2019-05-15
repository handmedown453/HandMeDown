package android.project.handmedown.Splash_Screen;

import android.app.Application;
import android.content.Intent;
import android.project.handmedown.Testing.FirstActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();


        if(firebaseUser != null){

            Intent i=new Intent(SplashActivity.this, FirstActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);

        }
    }
}
