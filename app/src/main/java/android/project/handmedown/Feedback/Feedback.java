package android.project.handmedown.Feedback;

import android.content.Context;
import android.project.handmedown.HomeActivity;
import android.project.handmedown.Navigationdrawer.NavigationDrawerActivity;
import android.project.handmedown.R;
import android.os.Bundle;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Feedback extends NavigationDrawerActivity {

    private Button Feedback_Submit;
    private TextView Feedback_EmailAddress,Feedback_Text;
    private long maxid = 0;
    private DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_feedback);*/
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_feedback, null, false);
        drawer.addView(contentView, 0);
        reff = FirebaseDatabase.getInstance().getReference("feedback");
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
                String Feedback_Text_String = Feedback_Text.getText().toString();
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists())
                            maxid = (dataSnapshot.getChildrenCount());
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                reff.child(String.valueOf(maxid + 1)).setValue(Feedback_Text_String);

                Toast.makeText(getApplicationContext(), "Thank you for your Feedback.", Toast.LENGTH_LONG).show();
                Intent Home = new Intent(v.getContext(), HomeActivity.class);
                startActivityForResult(Home, 0);
            }
        });


    }
}