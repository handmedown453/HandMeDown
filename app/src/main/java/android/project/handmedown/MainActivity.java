package android.project.handmedown;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class MainActivity extends AppCompatActivity {

    private Button Gotofeedback;
    private Button getdata;
    private Button donate;
    private Button faq;

    String TAG="This is a Tag";
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("message");

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gotofeedback = (Button) findViewById(R.id.gotofeedback);
        faq = (Button) findViewById(R.id.faq);
        donate = (Button) findViewById(R.id.gotodonate);
        Gotofeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect = new Intent(v.getContext(), Feedback.class);
                startActivityForResult(redirect, 0);
            }
        });

        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect = new Intent(v.getContext(), WebViewFAQ.class);
                startActivityForResult(redirect, 0);
            }
        });
        donate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent donation = new Intent(v.getContext(), Donate.class);
                startActivityForResult(donation,0);
            }
        });

        getdata = (Button) findViewById(R.id.getfeedback);
        getdata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        String value = dataSnapshot.getValue(String.class);

                        Toast.makeText(getApplicationContext(),value, Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Toast.makeText(getApplicationContext(),"Error Reading Feedback", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Toast.makeText(MainActivity.this, "This is Home Page.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_add:
                        Intent donate = new Intent(MainActivity.this, Donate.class);
                        startActivityForResult(donate, 0);
                        break;
                    case R.id.action_account:
                        Intent redirect = new Intent(MainActivity.this, Feedback.class);
                        startActivityForResult(redirect, 0);
                        break;

                }
                return true; }
        });

    }
}
