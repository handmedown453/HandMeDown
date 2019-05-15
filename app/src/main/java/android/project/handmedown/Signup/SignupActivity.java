package android.project.handmedown.Signup;

import android.content.Intent;
import android.project.handmedown.Signin.LoginActivity;
import android.project.handmedown.R;
import android.project.handmedown.userdetails.User;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.project.handmedown.Utils.isEmailValid;

/**
 * Activity to handle user signup flow.
 */
public class SignupActivity extends AppCompatActivity {
    // references to input views
    private EditText Firstname, Lastname, Email, Phone, Password, ConfirmPassword;
    private Button Signup;
    private CheckBox checkBox;
    private FirebaseAuth mauth;
    int number = 0;
    private DatabaseReference reff;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize UI layout
        setContentView(R.layout.activity_signup);

        // get references to input views
        Firstname = findViewById(R.id.Signup_FirstName_editText);
        Lastname = findViewById(R.id.Signup_Lastname_editText);
        Email = findViewById(R.id.Signup_Email_editText);
        Phone = findViewById(R.id.Signup__phone_editText);
        Password = findViewById(R.id.Signup_password_editText);
        ConfirmPassword = findViewById(R.id.Signup_Confirmpassword_editText);
        Signup = findViewById(R.id.Signup_signup_Button);
        checkBox = findViewById(R.id.Singup_Terms_checkbox);

        // get reference to Firebase authentication service
        mauth = FirebaseAuth.getInstance();

        // set change listener for checkbox
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // if checkbox is checked
                if (isChecked) {
                    // enable signup button
                    Signup.setEnabled(true);

                } else { // else, checkbox is not checked
                    // disable signup button
                    Signup.setEnabled(false);
                }

            }
        });

        // initialize button for existing users to return to signin activity
        TextView T1 = findViewById(R.id.Signup_login_textview);
        String login = "Already a member? Sign in!";
        SpannableString s1 = new SpannableString(login);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View V) {
                // when button is clicked, go back to signin activity
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        };
        s1.setSpan(clickableSpan1, 18, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        T1.setText(s1);
        T1.setMovementMethod(LinkMovementMethod.getInstance());

        // initialize help button
        TextView T2 = findViewById(R.id.Signup_Contact_us_textview);
        String Contact_us = "Having a problem? Contact us.";
        SpannableString s2 = new SpannableString(Contact_us);
        ClickableSpan clickableSpan2 = new ClickableSpan() {
            @Override
            public void onClick(View V) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        };
        s2.setSpan(clickableSpan2, 18, 29, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        T2.setText(s2);
        T2.setMovementMethod(LinkMovementMethod.getInstance());

        // Show terms and conditions TextView
        TextView T3 = findViewById(R.id.Signup_Terms_textview);
        String terms = "By joining I agree to HandMeDown's term and Conditions,privacy Policy and end user licence agreement. I also consent to receive marketing emails from HandmeDown which I can opt out of at the bottom of each email.";
        SpannableString s3 = new SpannableString(terms);
        ClickableSpan clickableSpan3 = new ClickableSpan() {
            @Override
            public void onClick(View V) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        };
        s3.setSpan(clickableSpan3, 35, 54, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        T3.setText(s3);
        T3.setMovementMethod(LinkMovementMethod.getInstance());

        // set click handler for signup button
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // get input values
                final String firstname = Firstname.getText().toString();
                final String lastname = Lastname.getText().toString();

                final String email = Email.getText().toString();
                final String phone = Phone.getText().toString();
                final String password = Password.getText().toString();
                final String confirmPassword = ConfirmPassword.getText().toString();
                try {
                    // try to parse phone number to make sure input value is a number
                    number = Integer.parseInt(Phone.getText().toString());

                } catch (NumberFormatException e) {
                    // show error message if unable to parse
                    showMessage(e.toString());
                }

                // validate input values
                if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    showMessage("please enter  All  valid fields");

                } else if (firstname.isEmpty()) {
                    showMessage("please enter first name");

                } else if (lastname.isEmpty()) {
                    showMessage("please enter last name");

                } else if (email.isEmpty()) {
                    showMessage("please enter email id");

                } else if (!isEmailValid(email)) {
                    showMessage("please enter  valid email id");

                } else if (phone.isEmpty()) {
                    showMessage("please enter valid phone number");

                } else if (phone.length() < 10) {
                    showMessage("please enter valid phone number");

                } else if (password.isEmpty()) {
                    showMessage("please enter valid password of length 8");

                } else if (password.length() < 8) {
                    showMessage("please enter valid password of length 8");

                } else if (!password.equals(confirmPassword)) { // else if password and confirm password are not equal
                    Password.setText("");
                    ConfirmPassword.setText("");
                    showMessage("please enter both password same");

                } else { // else, all inputs are acceptable
                    // create new User object to store user details
                    user = new User();

                    // use Firebase authentication service to create new user
                    mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // if user created successfully
                            if (task.isSuccessful()) {
                                // show success message
                                showMessage("Sucessful Signup");

                                // get reference to new user in Firebase
                                reff = FirebaseDatabase.getInstance().getReference(mauth.getUid());
                                // pull user details from Firebase reference
                                user.setFirstname(Firstname.getText().toString());
                                user.setLastname(Lastname.getText().toString());
                                user.setPhone(number);
                                user.setEmail(Email.getText().toString());
                                user.setPassword(Password.getText().toString());
                                reff.setValue(user);
                                // return to login activity
                                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                                startActivity(i);

                            } else { // else, user not created successfully
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
}