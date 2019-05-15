package android.project.handmedown.Fooddata;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.project.handmedown.HomeActivity;
import android.project.handmedown.Navigationdrawer.NavigationDrawerActivity;
import android.project.handmedown.R;
import android.project.handmedown.userdetails.User;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ARActivity extends NavigationDrawerActivity {
    FirebaseAuth firebaseAuth;
    Marker mCenterMarker;
    LatLng My_location1;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    int i = 1;
    float result1 = 0;
    Button button1,button2;
    long maxid;
    double lat1, lat2, log1, log2;
    String name,phone,email,image1;
    ;
    ImageView img;
    TextView textView1, textView2, textView3, textView4, textView5;

    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_ar, null, false);
        drawer.addView(contentView, 0);
        setContentView(R.layout.activity_ar);
        img = findViewById(R.id.Details_image1);
        button1 = findViewById(R.id.ar1_button);
        button2 = findViewById(R.id.ar2_button);
        textView1 = findViewById(R.id.Title_1);
        textView2 = findViewById(R.id.user);

        /*textView3.setEnabled(false);
        textView4.setEnabled(false);*/

        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        final String user = intent.getStringExtra("user");

        reff = FirebaseDatabase.getInstance().getReference(user);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                textView2.setText(" requset user: "+user1.getFirstname());
                phone=user1.getPhone();
                email=user1.getEmail();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reff = FirebaseDatabase.getInstance().getReference("food").child(id);
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Food_data food = dataSnapshot.getValue(Food_data.class);
                textView1.setText(food.getTitle());
                image1 = food.getFilepath();
                Picasso.get().load(image1).fit().centerCrop().into(img);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        reff = FirebaseDatabase.getInstance().getReference("food").child(id);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        reff.child("reqstTag").setValue("no");
                        reff.child("tag").setValue("true");
                        Intent i = new Intent(ARActivity.this,AcceptRejectActivity.class);
                        startActivity(i);
                        finish();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });



        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ARActivity.this,phone, Toast.LENGTH_LONG).show();

                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        reff.child("reqstTag").setValue("no");
                        reff.child("tag").setValue("false");
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                AlertDialog alertDialog = new AlertDialog.Builder(ARActivity.this).create();
                alertDialog.setTitle("Details");
                alertDialog.setMessage("Phone: "+phone);



                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                               /* Intent i = new Intent(Intent.ACTION_DIAL);
                                i.setData(Uri.parse("tel;"+phone));
                                startActivity(i);
                                finish();*/
                                Intent i = new Intent(ARActivity.this, HomeActivity.class);
                                startActivity(i);
                                finish();
                            }
                        });


                alertDialog.show();
            }


        });
    }
}
