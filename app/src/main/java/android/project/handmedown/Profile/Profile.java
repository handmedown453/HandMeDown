package android.project.handmedown.Profile;

import android.content.Context;
import android.project.handmedown.Navigationdrawer.NavigationDrawerActivity;
import android.project.handmedown.R;
import android.project.handmedown.userdetails.User;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.view.LayoutInflater;
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

public class Profile extends NavigationDrawerActivity {
    Button B1, B2, B3;
    TextView textView1, textView2, textView3;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_profile);*/
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_profile, null, false);
        drawer.addView(contentView, 0);
        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
        textView1 = findViewById(R.id.username);
        textView2 = findViewById(R.id.phoneNo);
        textView3 = findViewById(R.id.userEmail);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                if (user1.getEmail() != null) {
                    textView3.setText(user1.getEmail());
                    textView1.setText(user1.getFirstname());
                    textView2.setText(user1.getPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
