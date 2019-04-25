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

public class FirstActivity extends AppCompatActivity {
    TextView t1,t2;
    Button B1,B2,B3;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reff;
    private ValueEventListener queryListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        t1=findViewById(R.id.textView);
        t2=findViewById(R.id.textView2);
        firebaseAuth=firebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();

            reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 =dataSnapshot.getValue(User.class);
                t1.setText(user1.getEmail());
               t2.setText(user1.getFirstname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    B1=findViewById(R.id.button1);
        B1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.getInstance().signOut();
                Intent i = new Intent( FirstActivity.this, LoginActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
        B2=findViewById(R.id.button2);
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( FirstActivity.this,SecondActivity.class);
                startActivity(i);

            }
        });

    }
}
