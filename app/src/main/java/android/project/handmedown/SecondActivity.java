package android.project.handmedown;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.project.handmedown.Fooddata.Food_data;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {
     EditText Title, Disc;
    Button b1,b2;
    private DatabaseReference reff;
    TextView textView;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    Food_data data;
    ImageView img;
    StorageReference storageReference;
    Uri imguri, uri, photoURI;
    StorageTask uploadtask;
    String mCurrentPhotoPath;
    int CAMERA_REQUEST_CODE=1;
    long maxid=0;
    String imageID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        storageReference= FirebaseStorage.getInstance().getReference("Images");
         textView =findViewById(R.id.Donate_gallery_textview);
        b1=findViewById(R.id.Donate_image_button);
        b2=findViewById(R.id.Donate_Submit_button);
        img=findViewById(R.id.imageView);
        Title=findViewById(R.id.Donate_title_edittext);
        Disc=findViewById(R.id.Donate_discp_edittext);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
              /* Filechoser();*/
               /* Intent intent =new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,1);*/
                dispatchTakePictureIntent();
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Filechoser();
            }
        });
        reff = FirebaseDatabase.getInstance().getReference("food");

        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    maxid = (dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (uploadtask != null && uploadtask.isInProgress()){
                    Toast.makeText(SecondActivity.this,"Image upload in progress", Toast.LENGTH_LONG).show();
                }
                Fileuploader();
            }
        });


    }
    private String getExtenstion(Uri uri){
        ContentResolver cr =getContentResolver();
        MimeTypeMap mimeTypeMap =MimeTypeMap.getSingleton();
        return  mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
    private void Fileuploader() {
        firebaseAuth = firebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        reff = FirebaseDatabase.getInstance().getReference(firebaseAuth.getUid());

        data =new Food_data();


        data.setTitle(Title.getText().toString());
        data.setDisc(Disc.getText().toString());
        data.setImageID(imageID);
        data.setUserid(String.valueOf(firebaseAuth.getUid()));
        reff = FirebaseDatabase.getInstance().getReference();


        reff.child("food").child(String.valueOf(maxid+1)).setValue(data);

        StorageReference Ref = storageReference.child(imageID);

        uploadtask= Ref.putFile(imguri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                       // Uri downloadUrl = taskSnapshot.getDownloadUrl();
                        Toast.makeText(SecondActivity.this,"Image upload suceesfully", Toast.LENGTH_LONG).show();
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

            }
            if(requestCode==2){
                imguri= data.getData();
                imageID=System.currentTimeMillis()+"."+getExtenstion(imguri);

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

}
