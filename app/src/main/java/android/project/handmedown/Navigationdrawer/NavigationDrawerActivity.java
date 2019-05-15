package android.project.handmedown.Navigationdrawer;

import android.content.Intent;
import android.os.Bundle;
import android.project.handmedown.Feedback.Feedback;
import android.project.handmedown.Fooddata.AcceptRejectActivity;
import android.project.handmedown.Fooddata.DonateActivity;
import android.project.handmedown.Map.MapsActivity;
import android.project.handmedown.Profile.Profile;
import android.project.handmedown.R;
import android.project.handmedown.Signin.LoginActivity;
import android.project.handmedown.HomeActivity;
import android.project.handmedown.Webview;
import android.project.handmedown.userdetails.User;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class NavigationDrawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawer;
    TextView textView1, textView2;
    FirebaseAuth firebaseAuth;
    DatabaseReference reff;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LayoutInflater inflater = LayoutInflater.from(NavigationDrawerActivity.this);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        final View v = inflater.inflate(R.layout.nav_header_navigation_drawer, null);


        textView1 = (TextView) header.findViewById(R.id.textView11);
        textView2 = (TextView) header.findViewById(R.id.textView22);

        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
        drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                if (user1.getEmail() != null) {
                    textView1.setText(user1.getEmail());
                    textView2.setText(user1.getFirstname());
                } else {
                    // start login screen
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }*/

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the SplashActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            Intent i = new Intent(this, HomeActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.your_listing) {
            Intent i = new Intent(this, AcceptRejectActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.loaction) {
            Intent i = new Intent(this, MapsActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.my_profile) {
            Intent i = new Intent(this, Profile.class);
            startActivity(i);
            finish();
        } else if (id == R.id.feedback) {
            Intent i = new Intent(this, Feedback.class);
            startActivity(i);
            finish();
        } else if (id == R.id.about_us) {
            Intent intent = new Intent(this, Webview.class);
            intent.putExtra("id", "aboutus");
            startActivity(intent);
            finish();
        } else if (id == R.id.sign_out) {
            firebaseAuth.getInstance().signOut();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            finish();
        } else if (id == R.id.contact_us) {
            Intent intent = new Intent(this, Webview.class);
            intent.putExtra("id", "contactus");
            startActivity(intent);
            finish();
        } else if (id == R.id.faqs) {
            Intent intent = new Intent(this, Webview.class);
            intent.putExtra("id", "faqs");
            startActivity(intent);
            finish();
        } else if (id == R.id.tnc) {
            Intent intent = new Intent(this, Webview.class);
            intent.putExtra("id", "tnc");
            startActivity(intent);
            finish();
        } else if (id == R.id.donate) {
            Intent intent = new Intent(this, DonateActivity.class);
            startActivity(intent);
            finish();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
