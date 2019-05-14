package android.project.handmedown;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Feedback extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    private EditText Feedback_Name;
    //private EditText Feedback_Email;
    private EditText Feedback_Text;
    private Button Feedback_Submit;
    private TextView Feedback_EmailAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        //Feedback_Name = (EditText) findViewById(R.id.Feedback_Name_EditText);
        //Feedback_Email = (EditText) findViewById(R.id.Feedback_Email_EditText);
        Feedback_Text = (EditText) findViewById(R.id.Feedback_description_EditText);
        Feedback_Submit = (Button) findViewById(R.id.Feedback_Submit_Button);
        Feedback_EmailAddress = (TextView) findViewById(R.id.Feedback_GotoMail_TextView);

        Feedback_EmailAddress.setClickable(true);
        Feedback_EmailAddress.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "Or <a href='mailto:handmedown453@gmail.com.'>Mail Us</a>";
        Feedback_EmailAddress.setText(Html.fromHtml(text));

        Feedback_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Feedback_Name_String = Feedback_Name.getText().toString().trim();
      //          String Feedback_Email_String = Feedback_Email.getText().toString().trim();
                String Feedback_Text_String = Feedback_Text.getText().toString().trim();

                myRef.setValue(Feedback_Text_String);
                Toast.makeText(getApplicationContext(),"Thank you for your Feedback.", Toast.LENGTH_LONG).show();
                Intent Home = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(Home, 0);
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Intent main = new Intent(Feedback.this, MainActivity.class);
                        startActivityForResult(main, 0);
                        Toast.makeText(getApplicationContext(),"Directed to Home Page", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.action_add:
                        Intent donate = new Intent(Feedback.this, Donate.class);
                        startActivityForResult(donate, 0);
                        break;
                    case R.id.action_account:
                        break;

                }
                return true; }
        });
    }
}
