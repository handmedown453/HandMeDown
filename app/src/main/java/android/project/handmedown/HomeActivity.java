package android.project.handmedown;

import android.content.Context;
import android.project.handmedown.Fooddata.Food_data;
import android.project.handmedown.Adapters.FooddataAdapter;
import android.project.handmedown.Navigationdrawer.NavigationDrawerActivity;
import android.project.handmedown.R;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class HomeActivity extends NavigationDrawerActivity {

    private RecyclerView mrecyclerview;
    private List<Food_data> food_data;
    private FooddataAdapter adapter;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Date date;
    String userid, days,city1,date1;

    DatabaseReference reff;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home, null, false);
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

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches= metrics.heightPixels/metrics.ydpi;
        float xInches= metrics.widthPixels/metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches*xInches + yInches*yInches);
        reff = FirebaseDatabase.getInstance().getReference("food");
        mrecyclerview = findViewById(R.id.home_view);
        mrecyclerview.setHasFixedSize(true);
         if (diagonalInches>=6.5){
            mrecyclerview.setLayoutManager(new GridLayoutManager(this,3));
        }else{
            mrecyclerview.setLayoutManager(new LinearLayoutManager(this));
        }

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
                       if(food.getUserid().equals(userid)){
                           //chiill
                       }
                       else
                       {
                           if(food.getTag().equals("true")){
                               if(food.getCity().equals(city1)) {
                                   date1 = food.getDate();
                                   days = food.getBesttime();
                                   int day = Integer.parseInt(days);
                                   SimpleDateFormat formatter = new SimpleDateFormat(", MM/dd/yyyy");
                                   try {
                                       date = formatter.parse(date1);
                                       System.out.println(date);
                                       System.out.println(formatter.format(date));

                                   } catch (ParseException e) {
                                       e.printStackTrace();
                                   }
                                   Date c = Calendar.getInstance().getTime();
                                   System.out.println("Current time => " + c);
                                   SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                                   String formattedDate = df.format(c);
                                   int dateDifference = (int) getDateDiff(new SimpleDateFormat("MM/dd/yyyy"), date1, formattedDate);
                                   if(day <= dateDifference){
                                       reff = FirebaseDatabase.getInstance().getReference("food");
                                        reff.child(String.valueOf(food.getId())).child("tag").setValue("false");
                                   }
                                   else
                                   {
                                       food_data.add(food);
                                   }
                               }
                           }

                       }
                }
                adapter.notifyDataSetChanged();
            }
        }
        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
    public static long getDateDiff(SimpleDateFormat format, String oldDate, String newDate) {
        try {
            return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - format.parse(oldDate).getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
