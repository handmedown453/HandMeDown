package android.project.handmedown;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.project.handmedown.Signin.LoginActivity;
import android.project.handmedown.Signup.SignupActivity;
import android.project.handmedown.userdetails.User;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleMap.OnCameraMoveStartedListener,
        GoogleMap.OnCameraMoveListener,
        GoogleMap.OnCameraIdleListener,
        GoogleMap.OnCameraMoveCanceledListener {
    private GoogleMap mMap;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    private DatabaseReference reff;
    protected LocationManager locationManager;
    private static final String TAG = "MainActivity";
    protected LocationListener locationListener;
    String lat,log;
    User user;
    Button Select_location;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    ;
    PlacesClient placesClient;
    List<Place.Field> placeFileds = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
    AutocompleteSupportFragment places_fragment;
    Marker mCenterMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        /*initplaces();
        setupPlaceAutoCompete();*/
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Select_location = findViewById(R.id.Maps_Selectlocation_button);

        Select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user =new User();
                firebaseAuth = firebaseAuth.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();
                reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
                user.setlAT(lat);
                user.setlOG(log);
                reff.child("lat").setValue(lat);
                reff.child("long").setValue(log);
                Intent i = new Intent(MapsActivity.this, SecondActivity.class);
                startActivity(i);
            }
        });


    }

   /* private void setupPlaceAutoCompete() {
        places_fragment = (AutocompleteSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.place_autocomplete);
        places_fragment.setPlaceFields(placeFileds);
        places_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {

                Toast.makeText(MapsActivity.this, "" + place.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(MapsActivity.this, "" + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    private void initplaces() {
        Places.initialize(this,getString(R.string.google_api_key));
        placesClient =Places.createClient(this);

    }*/


    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera

        mMap.setOnCameraIdleListener(this);
        /* mMap.setOnCameraMoveStartedListener(this);*/
        mMap.setOnCameraMoveListener(this);
        /* mMap.setOnCameraMoveCanceledListener(this);*/
        getDeviceLocation();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
    }


    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        try {


            final Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: found location!");
                        Location currentLocation = (Location) task.getResult();
                        LatLng My_location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                        mCenterMarker = mMap.addMarker(new MarkerOptions().position(My_location).title("my location").draggable(true));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(My_location));
                        lat = String.valueOf(currentLocation.getLatitude());
                        log= String.valueOf(currentLocation.getLongitude());


                    }
                }
            });

        } catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
        }
    }

    @Override
    public void onCameraIdle() {

        CameraPosition test1 = mMap.getCameraPosition();
        //Assign mCenterMarker reference:
         new MarkerOptions().position(mMap.getCameraPosition().target).anchor(0.5f, .05f).title("Test1");

        lat = String.valueOf(test1.target.latitude);
        log= String.valueOf(test1.target.latitude);
        Log.d(TAG, "Map Coordinate1: " + lat+"  "+log);

    }

    @Override
    public void onCameraMoveCanceled() {
        Toast.makeText(this, "The camera is move cancel.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMove() {


        //Remove previous center if it exists
        if (mCenterMarker != null) {
            mCenterMarker.remove();
        }

        CameraPosition test = mMap.getCameraPosition();
        //Assign mCenterMarker reference:
        mCenterMarker = mMap.addMarker(new MarkerOptions().position(mMap.getCameraPosition().target).anchor(0.5f, .05f).title("Test"));
        Log.d(TAG, "Map Coordinate: " + String.valueOf(test));
    }


    @Override
    public void onCameraMoveStarted(int reason) {
        if (reason == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
            Toast.makeText(this, "The user gestured on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_API_ANIMATION) {
            Toast.makeText(this, "The user tapped something on the map.",
                    Toast.LENGTH_SHORT).show();
        } else if (reason == GoogleMap.OnCameraMoveStartedListener
                .REASON_DEVELOPER_ANIMATION) {
            Toast.makeText(this, "The app moved the camera.",
                    Toast.LENGTH_SHORT).show();
        }

    }


}