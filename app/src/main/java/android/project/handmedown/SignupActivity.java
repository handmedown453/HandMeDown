package android.project.handmedown;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    private EditText Firstname, Lastname, Email, Age, Password, ConfirmPassword;
    private Button cancel, Signup;
    private FirebaseAuth mauth;
    int number = 0;
    boolean a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Firstname = findViewById(R.id.Signup_FirstName_editText);
        Lastname = findViewById(R.id.Signup_Lastname_editText);
        Email = findViewById(R.id.Signup_Email_editText);

        Age = findViewById(R.id.Signup_age_editText);
        Password = findViewById(R.id.Signup_password_editText);
        ConfirmPassword = findViewById(R.id.Signup_Confirmpassword_editText);
        cancel = findViewById(R.id.Signup_cancel_button);
        Signup = findViewById(R.id.Signup_signup_Button);
        mauth = FirebaseAuth.getInstance();

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignupActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String firstname = Firstname.getText().toString();
                final String lastname = Lastname.getText().toString();

                final String email = Email.getText().toString();
                String age = Age.getText().toString();
                final String password = Password.getText().toString();
                final String confirmPassword = ConfirmPassword.getText().toString();
                try {
                    number = Integer.parseInt(Age.getText().toString());
                } catch (NumberFormatException e) {
                    showMessage(e.toString());
                }


                if (firstname.isEmpty() || lastname.isEmpty()  || email.isEmpty() || age.isEmpty() || password.isEmpty()) {
                    showMessage("please enetr  All  valid fields");
                } else if (firstname.isEmpty()) {
                    showMessage("please enter first name");
                } else if (lastname.isEmpty()) {
                    showMessage("please enter last name");
                } else if (email.isEmpty()) {
                    showMessage("please enter email id");
                } else if (a == isEmailValid(email)) {
                    showMessage("please enter  valid email id");
                } else if (age.isEmpty()) {
                    showMessage("please enter valid age");
                } else if (number < 1) {
                    showMessage("please enter valid age between 1 to 99");
                } else if (number > 99) {
                    showMessage("please enter valid age between 1 to 99");
                } else if (password.isEmpty()) {
                    showMessage("please enter valid password of length 8");
                } else if (password.length() < 8) {
                    showMessage("please enter valid password of length 8");
                } else if (!password.equals(confirmPassword)) {
                    Password.setText("");
                    ConfirmPassword.setText("");
                    showMessage("please enter both password same");
                } else {
                    mauth = FirebaseAuth.getInstance();
                    mauth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {
                                showMessage("Sucessful Signup");
                                Intent i = new Intent(SignupActivity.this, MainActivity.class);
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