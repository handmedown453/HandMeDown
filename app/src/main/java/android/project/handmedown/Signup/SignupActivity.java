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

public class SignupActivity extends AppCompatActivity {
    private EditText Firstname, Lastname, Email, Phone, Password, ConfirmPassword;
    private Button Signup;
    private TextView T1,T2,T3,T4;
    private CheckBox checkBox;
    private FirebaseAuth mauth;
    int number = 0;
    boolean a;
    private DatabaseReference reff;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Firstname = findViewById(R.id.Signup_FirstName_editText);
        Lastname = findViewById(R.id.Signup_Lastname_editText);
        Email = findViewById(R.id.Signup_Email_editText);
        Phone = findViewById(R.id.Signup__phone_editText);
        Password = findViewById(R.id.Signup_password_editText);
        ConfirmPassword = findViewById(R.id.Signup_Confirmpassword_editText);
        Signup = findViewById(R.id.Signup_signup_Button);
        mauth = FirebaseAuth.getInstance();
        checkBox=findViewById(R.id.Singup_Terms_checkbox);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    Signup.setEnabled(true);

                }else{
                    Signup.setEnabled(false);
                }

            }
        });
        TextView T1 = findViewById(R.id.Signup_login_textview);
        String login = "Already a member? Sign in!";
        SpannableString s1 = new SpannableString(login);
        ClickableSpan clickableSpan1 = new ClickableSpan() {
            @Override
            public void onClick(View V) {
                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(i);
            }
        };

        s1.setSpan(clickableSpan1, 18, 26, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        T1.setText(s1);
        T1.setMovementMethod(LinkMovementMethod.getInstance());

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





        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstname = Firstname.getText().toString();
                final String lastname = Lastname.getText().toString();

                final String email = Email.getText().toString();
                final String phone = Phone.getText().toString();
                final String password = Password.getText().toString();
                final String confirmPassword = ConfirmPassword.getText().toString();
                try {
                    number = Integer.parseInt(Phone.getText().toString());
                } catch (NumberFormatException e) {
                    showMessage(e.toString());
                }


                if (firstname.isEmpty() || lastname.isEmpty()  || email.isEmpty() || phone.isEmpty() || password.isEmpty()) {
                    showMessage("please enetr  All  valid fields");
                } else if (firstname.isEmpty()) {
                    showMessage("please enter first name");
                } else if (lastname.isEmpty()) {
                    showMessage("please enter last name");
                } else if (email.isEmpty()) {
                    showMessage("please enter email id");
                } else if (a == isEmailValid(email)) {
                    showMessage("please enter  valid email id");
                } else if (phone.isEmpty()) {
                    showMessage("please enter valid phone number");
                } else if (phone.length()<10) {
                    showMessage("please enter valid phone number");
                } else if (password.isEmpty()) {
                    showMessage("please enter valid password of length 8");
                } else if (password.length() < 8) {
                    showMessage("please enter valid password of length 8");
                } else if (!password.equals(confirmPassword)) {
                    Password.setText("");
                    ConfirmPassword.setText("");
                    showMessage("please enter both password same");
                } else {
                    user = new User();
                    mauth = FirebaseAuth.getInstance();

                    mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                showMessage("Sucessful Signup");
                                reff = FirebaseDatabase.getInstance().getReference(mauth.getUid());
                                user.setFirstname(Firstname.getText().toString());
                                user.setLastname(Lastname.getText().toString());
                                user.setPhone(number);
                                user.setEmail(Email.getText().toString());
                                user.setPassword(Password.getText().toString());
                                reff.setValue(user);
                                Intent i = new Intent(SignupActivity.this, LoginActivity.class);
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