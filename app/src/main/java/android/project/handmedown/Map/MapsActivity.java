package android.project.handmedown.Map;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.project.handmedown.HomeActivity;
import android.project.handmedown.R;
import android.project.handmedown.userdetails.User;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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
    String lat, log, city;
    double lat1, log1;
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private Boolean mLocationPermissionsGranted = false;

    private User user;
    private Button Select_location;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private TextView textView1;
    private PlacesClient placesClient;
    private List<Place.Field> placeFileds = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.ADDRESS);
    private  AutocompleteSupportFragment places_fragment;
    private Marker mCenterMarker;
    private LatLng My_location1,My_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        getLocationPermission();
        initplaces();
        setupPlaceAutoCompete();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Select_location = findViewById(R.id.Maps_Selectlocation_button);
        textView1 = findViewById(R.id.Current_Location);
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDeviceLocation();
            }
        });
        Select_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("TAG", city);

                user = new User();
                firebaseAuth = firebaseAuth.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();
                reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
                user.setCity(city);
                user.setLat(lat);
                user.setLog(log);
                reff.child("lat").setValue(lat);
                reff.child("log").setValue(log);
                reff.child("city").setValue(city);
                Intent i = new Intent(MapsActivity.this, HomeActivity.class);
                startActivity(i);
            }
        });


    }
    //places api for auoto complete
    private void setupPlaceAutoCompete() {
        places_fragment = (AutocompleteSupportFragment) getSupportFragmentManager()
                .findFragmentById(R.id.place_autocomplete);
        places_fragment.setPlaceFields(placeFileds);
        places_fragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Geocoder geocoder = new Geocoder(MapsActivity.this, Locale.getDefault());
                try {
                    List<Address> addresses = geocoder.getFromLocationName(place.getName(), 1);
                    String address = addresses.get(0).getAddressLine(0);
                    lat1 = addresses.get(0).getLatitude();
                    log1 = addresses.get(0).getLongitude();
                    city = addresses.get(0).getLocality();
                    Log.d("mylog", "Complete Address: " + addresses.toString());
                    Log.d("mylog", "Address: " + address);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (mCenterMarker != null) {
                    mCenterMarker.remove();
                }
                My_location = new LatLng(lat1, log1);
                mCenterMarker = mMap.addMarker(new MarkerOptions().position(My_location).title("my location").draggable(true));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(My_location));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
                Toast.makeText(MapsActivity.this, "" + place.getName(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(@NonNull Status status) {
                Toast.makeText(MapsActivity.this, "" + status.getStatusMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
        private void initplaces() {
            Places.initialize(this, getString(R.string.google_maps_key));
            placesClient = Places.createClient(this);

    }
        public void onMapReady(GoogleMap googleMap) {
            mMap = googleMap;
            mMap.setOnCameraIdleListener(this);
            /*mMap.setOnCameraMoveStartedListener(this);*/

            mMap.setOnCameraMoveListener(this);
            /*mMap.setOnCameraMoveCanceledListener(this);*/


    }


         private void getDeviceLocation() {
            Log.d(TAG, "getDeviceLocation: getting the devices current location");
            mMap.clear();
            if (mCenterMarker != null) {
                  mCenterMarker = null;
            }
            mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

            try {
                if (mLocationPermissionsGranted = true) {
                    final Task location = mFusedLocationProviderClient.getLastLocation();
                    location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            My_location = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                            mCenterMarker = mMap.addMarker(new MarkerOptions().position(My_location).title("my location").draggable(true));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(My_location));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(13.0f));
                            lat = String.valueOf(currentLocation.getLatitude());
                            log = String.valueOf(currentLocation.getLongitude());
                        }


                    }
                });
                } else {
                    Toast.makeText(MapsActivity.this, "Please enable location permission for this app", Toast.LENGTH_SHORT).show();
                }
            }
            catch (SecurityException e) {
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage());
            }
        }


    @Override
    public void onCameraIdle() {
        CameraPosition test1 = mMap.getCameraPosition();
        //Assign mCenterMarker reference:
        /*new MarkerOptions().position(mMap.getCameraPosition().target).anchor(0.5f, .05f).title("Test1");*/

        lat = String.valueOf(test1.target.latitude);
        log = String.valueOf(test1.target.longitude);

        My_location1 = new LatLng(test1.target.latitude, test1.target.longitude);
        getCityName(My_location1);
        Log.d(TAG, "Map Coordinate1: " + lat + "  " + log);

    }

    @Override
    public void onCameraMoveCanceled() {
        Toast.makeText(this, "The camera is move cancel.",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCameraMove() {
        if (mCenterMarker != null) {
            mCenterMarker.remove();
        }
        CameraPosition test = mMap.getCameraPosition();
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

    private void getCityName(LatLng myCoordinates) {

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(myCoordinates.latitude, myCoordinates.longitude, 1);
            if (addresses.size() > 0) {


                String address = addresses.get(0).getAddressLine(0);
                Double lat = addresses.get(0).getLatitude();
                Double log = addresses.get(0).getLatitude();
                city = addresses.get(0).getLocality();
                Log.d("mylog", "Complete Address: " + addresses.toString());
                Log.d("mylog", "Address: " + address);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void getLocationPermission() {
        Log.d(TAG, "getLocationPermission: getting location permissions");
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionsGranted = true;
                /*  initMap();*/
            } else {
                ActivityCompat.requestPermissions(this,
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(this,
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    public boolean isLocationServiceEnabled() {
        LocationManager locationManager = null;
        boolean gps_enabled = false, network_enabled = false;

        if (locationManager == null)
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
            //do nothing...
        }


        return gps_enabled;

    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionsGranted = true;

                } else {
                    Toast.makeText(MapsActivity.this, "please grant permission for better experience", Toast.LENGTH_LONG).show();
                }
                return;
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent setIntent = new Intent(this, HomeActivity.class);
        startActivity(setIntent);
        finish();
    }

}
