package android.project.handmedown.Splash_Screen;

import android.content.Intent;
import android.os.Handler;
import android.project.handmedown.FirstActivity;
import android.project.handmedown.R;
import android.project.handmedown.Signin.LoginActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Splash screen activity.
 * First activity started when application is launched.
 */
public class MainActivity extends AppCompatActivity {
    private static int splash_time_out = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize UI layout
        setContentView(R.layout.activity_main);

        // use a Handler to wait for a fixed amount of time
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // get references to Firebase Auth and Firebase User
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

                // if there is a user signed in
                if (firebaseUser != null) {
                    // start FirstActivity
                    Intent i = new Intent(MainActivity.this, FirstActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                } else { // else, there is no user signed in
                    // start sign in activity
                    Intent i = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }

            }
        }, splash_time_out);
    }
}
