package android.project.handmedown.Fooddata;
import android.Manifest;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.project.handmedown.HomeActivity;
import android.project.handmedown.Navigationdrawer.NavigationDrawerActivity;
import android.project.handmedown.R;
import android.project.handmedown.userdetails.User;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DonateActivity extends NavigationDrawerActivity {
     EditText Title, Disc;
    Button b1,b2,b3;
    private DatabaseReference reff;
    TextView textView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Food_data data;
    private static final  int MY_PERMISSIONS_REQUEST_READ_STORAGE=100,MY_PERMISSIONS_REQUEST_CAMERA=200;
    ImageView img;
    StorageReference storageReference;
    Uri imguri, uri, photoURI;
    StorageTask uploadtask;
    String mCurrentPhotoPath;
    int CAMERA_REQUEST_CODE=1;
    long maxid=0;
    String imageID,city,days,date1,time,FilePath,lag,log;
    private TextView mDisplayDate,mDisplay;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private TimePickerDialog.OnTimeSetListener mTimesetListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_donate);*/
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_donate, null, false);
        drawer.addView(contentView, 0);
        storageReference = FirebaseStorage.getInstance().getReference("Images");
        b3 = findViewById(R.id.Donate_gallery_button);
        b1 = findViewById(R.id.Donate_image_button);
        b2 = findViewById(R.id.Donate_Submit_button);
        img = findViewById(R.id.Donate_image);
        Title = findViewById(R.id.Donate_title_edittext);
        Disc = findViewById(R.id.Donate_discp_edittext);
        mDisplay = (TextView) findViewById(R.id.Donate_time_textview);
        mDisplayDate = (TextView) findViewById(R.id.Donate_Date_textview);
        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User user1 = dataSnapshot.getValue(User.class);
                city =user1.getCity();
                lag =user1.getLat();
                log =user1.getLog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        mDisplay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int hour = cal.get(Calendar.HOUR);
                int minute = cal.get(Calendar.MINUTE);
                int AP = cal.get(Calendar.AM_PM);
                TimePickerDialog dialog = new TimePickerDialog(
                        DonateActivity.this, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                mDisplay.setText(hourOfDay + ":" + minute);
                                time=String.valueOf(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);
                dialog.show();


            }
        });

        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        DonateActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year, month, day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
                dialog.show();
            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = month + "/" + day + "/" + year;
                mDisplayDate.setText(date);
                date1=String.valueOf(date);
            }
        };

        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                /* Filechoser();*/
               /* Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);*/
                if (ContextCompat.checkSelfPermission(DonateActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(DonateActivity.this, "please upload image or grant camera permission to this app", Toast.LENGTH_LONG).show();
                    ActivityCompat.requestPermissions(DonateActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);

                }else{
                    dispatchTakePictureIntent();
                }

            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(DonateActivity.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(DonateActivity.this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_STORAGE);
                }
                else
                {
                    Filechoser();

                }
            }
        });
        reff = FirebaseDatabase.getInstance().getReference("food");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    maxid =(dataSnapshot.getChildrenCount());
        }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Title.getText().toString().isEmpty() || Disc.getText().toString().isEmpty() || mDisplayDate.getText().toString().isEmpty() || mDisplay.getText().toString().isEmpty() || imguri.toString().isEmpty()) {

                    Toast.makeText(DonateActivity.this, "enter all fileds valid or select image", Toast.LENGTH_LONG).show();

                } else {
                    if (uploadtask != null && uploadtask.isInProgress()) {
                        Toast.makeText(DonateActivity.this, "Image upload in progress", Toast.LENGTH_LONG).show();
                    }
                    Fileuploader();

                }
            }
        });
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        switch (position){
                            case 0:
                                days="0";
                            case 1:
                                days= "1";
                                break;
                            case 2:
                                days= "2";
                                break;
                            case 3:
                                days= "3";
                                break;
                            case 4:
                                days= "4";
                                break;
                            case 5:
                                days= "5";
                                break;

                        }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }
    private String getExtenstion(Uri uri){
        ContentResolver cr =getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void Fileuploader() {


        data =new Food_data();

        reff = FirebaseDatabase.getInstance().getReference();

        final StorageReference Ref = storageReference.child(imageID);

        uploadtask= Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                             FilePath = uri.toString();
                            data.setTitle(Title.getText().toString());
                            data.setDisc(Disc.getText().toString());
                            data.setImageID(imageID);
                            data.setUserid(String.valueOf(firebaseAuth.getUid()));
                            data.setCity(city);
                            data.setTime(time);
                            data.setId(String.valueOf(maxid+1));
                            data.setTag("true");
                            data.setReqstTag("null");
                            data.setReqstdist("null");
                            data.setReqstuser("null");
                            data.setLat(lag);
                            data.setLog(log);
                            data.setDate(String.valueOf(date1));
                            data.setBesttime(String.valueOf(days));
                            data.setFilepath(FilePath);
                            reff.child("food").child(String.valueOf(maxid+1)).setValue(data);
                            Intent i = new Intent(DonateActivity.this, HomeActivity.class);

                            startActivity(i);

                        }
                    });
                        // Get a URL to the uploaded content
                       // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(DonateActivity.this,"Image upload suceesfully", Toast.LENGTH_LONG).show();





                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                        // ...
                    }
                });
    }

    private void Filechoser() {
        Intent intent = new Intent();
        intent.setType("image/");
        intent.setAction(intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,2);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK ) {
            if (requestCode == CAMERA_REQUEST_CODE ) {
                imguri=photoURI;
                imageID=imguri.getLastPathSegment();

                Picasso.get().load(imguri).fit().centerCrop().into(img);

            }
            if(requestCode==2){
                imguri= data.getData();
                imageID=System.currentTimeMillis()+"."+getExtenstion(imguri);
                Picasso.get().load(imguri).fit().centerCrop().into(img);
            }

        }
    }

    private File createImageFile() throws IOException {
// Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
// Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File...
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                 photoURI = FileProvider.getUriForFile(this,
                        "android.project.handmedown.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {

            case MY_PERMISSIONS_REQUEST_READ_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                            Filechoser();
                } else {
                    Toast.makeText(DonateActivity.this,"please grant permission for better experience", Toast.LENGTH_LONG).show();
                }
                return;
            }
            case MY_PERMISSIONS_REQUEST_CAMERA : {
                if(grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    dispatchTakePictureIntent();
                } else{
                    Toast.makeText(DonateActivity.this, "please grant permission for better experience", Toast.LENGTH_LONG).show();
                }
                return;

                // other 'case' lines to check for other
            } // permissions this app might request.
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent setIntent = new Intent(this,HomeActivity.class);
        startActivity(setIntent);
        finish();
    }
}
