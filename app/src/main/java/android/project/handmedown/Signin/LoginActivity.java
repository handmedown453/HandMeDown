package android.project.handmedown.Signin;

import android.content.Intent;
import android.project.handmedown.FirstActivity;
import android.project.handmedown.R;
import android.project.handmedown.Signup.SignupActivity;
import android.support.annotation.NonNull;
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

/**
 * Activity to allow users to sign in, or begin the sign-up activity.
 */
public class LoginActivity extends AppCompatActivity {
    private EditText userid, password;
    private Button Login;
    private FirebaseAuth mauth;
    public int counter = 3, flag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize UI layout
        setContentView(R.layout.activity_login);

        // get references to input views
        userid = findViewById(R.id.Login_email_editText);
        password = findViewById(R.id.Login_password_editText);
        Login = findViewById(R.id.Login_login_button);

        // Get reference to Firebase Authentication service
        mauth = FirebaseAuth.getInstance();

        // get reference to sign up "button"
        TextView textview = findViewById(R.id.Login_signuplink_textview);
        String text = "Not a Member yet? Signup now";

        // make ClickableSpan to handle clicking of sign up "button"
        SpannableString ss = new SpannableString(text);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View V) {
                // start signup activity when signup button is pressed
                Intent i = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(i);
            }
        };
        ss.setSpan(clickableSpan1, 17, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview.setText(ss);
        textview.setMovementMethod(LinkMovementMethod.getInstance());

        // get reference to forgot password "button"
        TextView textview2 = findViewById(R.id.Login_forget_textview);
        String text1 = "Forget password?";

        // make ClickableSpan to handle clicks of forgot password "button"
        SpannableString s1 = new SpannableString(text1);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View V) {
                // start forgot password activity when button is pressed
                Intent i = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(i);
            }
        };
        s1.setSpan(clickableSpan2, 0, 16, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textview2.setText(s1);
        textview2.setMovementMethod(LinkMovementMethod.getInstance());

        // attach click listener to login button
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get username and password inpyut values
                final String user = userid.getText().toString();
                String pass = password.getText().toString();

                // if username or password was not entered
                if (user.isEmpty() || pass.isEmpty()) {
                    // show error message
                    showMessage("please enter valid Email and password");

                } else {
                    // attempt to sign in with Firebase auth service
                    mauth.signInWithEmailAndPassword(user, pass)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // if authentication succeeded
                                    if (task.isSuccessful()) {
                                        // start FirstActivity
                                        Intent i = new Intent(LoginActivity.this, FirstActivity.class);
                                        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(i);

                                    } else {

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
     * Wrapper around @{link Toast#makeText} with default values and provided message.
     *
     * @param message the message to show in the toast.
     */
    private void showMessage(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

}