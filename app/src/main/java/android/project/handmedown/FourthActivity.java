package android.project.handmedown;

import android.project.handmedown.Fooddata.Food_data;
import android.project.handmedown.userdetails.User;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class FourthActivity extends AppCompatActivity {
    private RecyclerView mrecyclerview;
    private List<Food_data> food_data;
    private FooddataAdapter adapter;
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
        setContentView(R.layout.activity_fourth);
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
        mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        food_data =new ArrayList<>();
        adapter =new FooddataAdapter(this,food_data);
        mrecyclerview.setAdapter(adapter);
        Query query = FirebaseDatabase.getInstance().getReference("food");
        query.addListenerForSingleValueEvent(valueEventListener);


    }
    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            food_data.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Food_data food= snapshot.getValue(Food_data.class);

                        if(food.getReqstTag().equals("true")) {
                            rqstuser = food.getReqstuser();
                            reqstdist = food.getReqstdist();
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
}
