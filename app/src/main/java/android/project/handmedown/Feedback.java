package android.project.handmedown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class Feedback extends AppCompatActivity {


    private EditText Feedback_Name;
    private EditText Feedback_Email;
    private EditText Feedback_Text;
    private Button Feedback_Submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Feedback_Name = (EditText) findViewById(R.id.Feedback_EditText_UserName);
        Feedback_Email = (EditText) findViewById(R.id.Feedback_EditText_Email);
        Feedback_Text = (EditText) findViewById(R.id.Feedback_EditText_Text);
        Feedback_Submit = (Button) findViewById(R.id.Feedback_Button_Submit);

        Feedback_Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Feedback_String_Name = Feedback_Name.getText().toString().trim();
                String Feedback_String_Email = Feedback_Email.getText().toString().trim();
                String Feedback_String_Text = Feedback_Text.getText().toString().trim();

                Toast.makeText(getApplicationContext(),"Thank you for your Feedback.", Toast.LENGTH_LONG).show();
                Intent Home = new Intent(v.getContext(), MainActivity.class);
                startActivityForResult(Home, 0);
            }
        });


    }
}
