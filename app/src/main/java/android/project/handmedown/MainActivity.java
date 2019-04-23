package android.project.handmedown;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText userid, password;
    private Button Login;
    private FirebaseAuth mauth;
    public int counter = 3, flag = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userid = findViewById(R.id.Login_email_editText);
        password = findViewById(R.id.Login_password_editText);
        Login = findViewById(R.id.Login_login_button);
        mauth = FirebaseAuth.getInstance();
        TextView textview = findViewById(R.id.Login_signuplink_textview);
        String text = "Not a Member yet? Signup now";

        SpannableString ss = new SpannableString(text);

        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View V) {
                Intent i = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(i);
            }
        };

        ss.setSpan(clickableSpan1, 17, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText(ss);
        textview.setMovementMethod(LinkMovementMethod.getInstance());

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    String user = userid.getText().toString();
                    String pass = password.getText().toString();
                    if (user.isEmpty() || pass.isEmpty()) {
                        showMessage("please enter valid Email and password");
                    }
                    else{
                        mauth.signInWithEmailAndPassword(user, pass)
                                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {

                                        if (task.isSuccessful()) {
                                            showMessage("Cool");
                                        } else {
                                            showMessage("faild signInWithEmailAndPassword");
                                        }

                                    }


                                });


                    }


                }
            }


        });
    }
    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}