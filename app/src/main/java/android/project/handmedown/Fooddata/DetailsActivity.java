package android.project.handmedown.Fooddata;

import android.content.Intent;
import android.project.handmedown.HomeActivity;
import android.project.handmedown.R;
import android.project.handmedown.userdetails.User;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    FirebaseAuth firebaseAuth;
    Marker mCenterMarker;
    LatLng My_location1;
    FirebaseUser firebaseUser;
    StorageReference storageReference;
    int i = 1;
    float result1 = 0;
    Button button;
    long maxid;
    double lat1, lat2, log1, log2;
    String userid, image1, title, disc, days, time, lag1, long1, lag2, long2, tag = "", city1, city2, date1, date;
    ;
    ImageView img;
    TextView textView1, textView2, textView3, textView4, textView5;

    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final Intent intent = getIntent();
        final String id = intent.getStringExtra("id");
        img = findViewById(R.id.Details_image);
        button = findViewById(R.id.Details_request_button);
        textView1 = findViewById(R.id.Details_Title);
        textView2 = findViewById(R.id.Details_time);
        textView3 = findViewById(R.id.Details_disc);

        textView5 = findViewById(R.id.Details_date);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map1);
        mapFragment.getMapAsync(this);


        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                city1 = user1.getCity();
                lag1 = user1.getLat();
                long1 = user1.getLog();
                lat1 = Double.parseDouble(lag1);
                log1 = Double.parseDouble(long1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        reff = FirebaseDatabase.getInstance().getReference("food").child(id);

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Food_data food = dataSnapshot.getValue(Food_data.class);
                image1 = food.getFilepath();
                time = food.getTime();
                title = food.getTitle();
                disc = food.getDisc();
                date = food.getDate();
                lag2 = food.getLat();
                long2 = food.getLog();
                lat2 = Double.parseDouble(lag2);
                log2 = Double.parseDouble(long2);
                My_location1 = new LatLng(lat2,log2);
                mCenterMarker = mMap.addMarker(new MarkerOptions().position(My_location1).title("my location").draggable(true));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(My_location1));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));



                Picasso.get().load(image1).fit().centerCrop().into(img);
                textView1.setText(title);
                textView2.setText("Pick Up time:" + time);
                textView3.setText(disc);
                textView5.setText("posted Date:" + date);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reff = FirebaseDatabase.getInstance().getReference("food").child(id);


                reff.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Food_data food = dataSnapshot.getValue(Food_data.class);
                        food.setReqstTag("yes");
                        food.setTag("false");
                        food.setReqstuser(firebaseAuth.getUid());
                        food.setReqstdist(String.valueOf(result1));
                        reff.setValue(food);
                        Intent i = new Intent(DetailsActivity.this, HomeActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent setIntent = new Intent(this,HomeActivity.class);
        startActivity(setIntent);
        finish();
    }
}
