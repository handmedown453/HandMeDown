package android.project.handmedown.Fooddata;

import android.content.Context;
import android.content.Intent;
import android.project.handmedown.HomeActivity;
import android.project.handmedown.Navigationdrawer.NavigationDrawerActivity;
import android.project.handmedown.R;
import android.project.handmedown.Adapters.RequestdataAdpter;
import android.project.handmedown.userdetails.User;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AcceptRejectActivity extends NavigationDrawerActivity {
    private RecyclerView mrecyclerview;
    private List<Food_data> food_data;
    private RequestdataAdpter adapter;
    FirebaseAuth firebaseAuth;
      FirebaseUser firebaseUser;
    StorageReference storageReference;
    int i=1;
    long maxid;
    Date date;
    String userid, image1, days, time, tag = "", city1, city2, date1,rqstuser,reqstdist;

    DatabaseReference reff;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_fourth);*/
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_fourth, null, false);
        drawer.addView(contentView, 0);
        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        userid=firebaseAuth.getUid();

        reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                city1 = user1.getCity();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        reff = FirebaseDatabase.getInstance().getReference("food");
        mrecyclerview = findViewById(R.id.request_view);
        mrecyclerview.setHasFixedSize(true);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);

        if (diagonalInches>=6.5){
            mrecyclerview.setLayoutManager(new GridLayoutManager(this,2));
        }else{
            mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        }
        food_data =new ArrayList<>();
        adapter = new RequestdataAdpter(this,food_data);
        mrecyclerview.setAdapter(adapter);

        reff.addListenerForSingleValueEvent(valueEventListener);



    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            food_data.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food_data food= snapshot.getValue(Food_data.class);

                        if(food.getReqstTag().equals("yes")) {
                            if(food.getUserid().equals(firebaseAuth.getUid()))
                           food_data.add(food);
                        }
                }
                adapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent setIntent = new Intent(this, HomeActivity.class);
        startActivity(setIntent);
        finish();
    }
}
