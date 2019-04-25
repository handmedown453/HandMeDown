package android.project.handmedown.Signin;

import android.content.Intent;
import android.project.handmedown.R;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText Email;
    boolean a;
    private FirebaseAuth mauth;
    private Button resetpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        resetpassword=findViewById(R.id.ForgetPassword_reset_button);
        mauth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.ForgetPassword_email_edittext);




            resetpassword.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final String email = Email.getText().toString();
                    if (email.isEmpty()) {
                        showMessage("please enter  valid email id");
                    } else if (a == isEmailValid(email)) {
                        showMessage("please enter  valid email id");
                    } else {
                        mauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    showMessage("Check your email for instructions");
                                    Intent i = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                    startActivity(i);
                                } else {
                                    showMessage(task.getException().getMessage());
                                }
                            }
                        });
                    }
                }
            });




    }

    private void showMessage(String message) {

        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }
    public boolean isEmailValid(String email) {
        String regExpn =
                "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }
}
