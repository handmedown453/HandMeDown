package android.project.handmedown;

import android.content.Intent;
import android.project.handmedown.Signin.LoginActivity;
import android.project.handmedown.userdetails.User;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * The first activity that is run when a user signs in.
 */
public class FirstActivity extends AppCompatActivity {
    TextView t1, t2;
    Button B1, B2, B3;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reff;
    private ValueEventListener queryListener;
    private static final String TAG = "FirstActivity";

    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // initialize ui layout
        setContentView(R.layout.activity_first);

        // get references to input views
        t1 = findViewById(R.id.textView);
        t2 = findViewById(R.id.textView2);

        // get references to Firebase services
        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        // get reference to current Firebase user
        reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());

        // add listener to detect changes in user details
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // get User object from DataSnapshot
                User user1 = dataSnapshot.getValue(User.class);

                // if email is not null
                if (user1.getEmail() != null) {
                    // update UI views with email and first name
                    t1.setText(user1.getEmail());
                    t2.setText(user1.getFirstname());
                } else {
                    // start login screen
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // initialize/configure logout button
        B1 = findViewById(R.id.button1);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Firebase sign out
                firebaseAuth.getInstance().signOut();
                // switch to login activity
                Intent i = new Intent(FirstActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });

        // initialize button2
        B2 = findViewById(R.id.button2);
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // switch to MapsActivity
                Intent i = new Intent(FirstActivity.this, MapsActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
}