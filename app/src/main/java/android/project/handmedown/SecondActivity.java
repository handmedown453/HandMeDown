package android.project.handmedown;

import android.content.Intent;
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

public class SecondActivity extends AppCompatActivity {
    TextView t1,t2;
    Button B1,B2,B3;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference reff;
    private ValueEventListener queryListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        t1=findViewById(R.id.textView_2);
        t2=findViewById(R.id.textView2_2);
        firebaseAuth=firebaseAuth.getInstance();
        firebaseUser=firebaseAuth.getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                newuser user1 =dataSnapshot.getValue(newuser.class);
                t1.setText(user1.getEmail());
                t2.setText(user1.getFirstname());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        B2=findViewById(R.id.butto_2);
        B2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent( SecondActivity.this,FirstActivity.class);
                startActivity(i);

            }
        });
    }
}
