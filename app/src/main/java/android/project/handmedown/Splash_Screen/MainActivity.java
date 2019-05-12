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

public class MainActivity extends AppCompatActivity {
    private static int splash_time_out = 1000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();
                FirebaseUser firebaseUser= firebaseAuth.getCurrentUser();
                if(firebaseUser != null){

                    Intent i=new Intent(MainActivity.this, FirstActivity.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);

                }
                else
                {
                    Intent i=new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(i);
                }

            }
        },splash_time_out);
    }
}
