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

/**
 * Activity to handle password reset flow.
 */
public class ForgetPasswordActivity extends AppCompatActivity {
    private EditText Email;
    private FirebaseAuth mauth;
    private Button resetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize UI layout
        setContentView(R.layout.activity_forget_password);

        // get reference to inputs
        resetpassword = findViewById(R.id.ForgetPassword_reset_button);
        Email = findViewById(R.id.ForgetPassword_email_edittext);

        // get instance to Firebase authentication service
        mauth = FirebaseAuth.getInstance();

        // set click handler for resetpassword button
        resetpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get email input value
                final String email = Email.getText().toString();

                // if email was not entered
                if (email.isEmpty()) {
                    // show error message
                    showMessage("please enter  valid email id");

                    // else if email input is not valid email format
                } else if (!isEmailValid(email)) {
                    // show error message
                    showMessage("please enter  valid email id");

                } else { // else, email is valid
                    // user Firebase authentication service to send password reset email
                    mauth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            // if password reset email was sent successfully
                            if (task.isSuccessful()) {
                                // show success message
                                showMessage("Check your email for instructions");
                                // switch back to signin activity
                                Intent i = new Intent(ForgetPasswordActivity.this, LoginActivity.class);
                                startActivity(i);

                            } else { // else, password reset email was not sent
                                // show error message
                                showMessage(task.getException().getMessage());
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * Convenience method.
     *
     * Wrapper around {@link Toast#makeText} with default values and provided message.
     *
     * @param message the message to show in the toast.
     */
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    /**
     * Checks if the provided string is an acceptable email address.
     *
     * @param email the string to check for email address format
     * @return whether the given string adheres to acceptable email address format
     */
    public boolean isEmailValid(String email) {
        // email regex definition
        String regExpn = "\\b[A-Z0-9._%-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}\\b";

        // user a Matcher to check if the provided string matches the email regex
        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        // return result of match
        return matcher.matches();
    }
}
