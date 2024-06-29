package com.example.agriculturalapp.supplier;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agriculturalapp.LoginActivity;
import com.example.agriculturalapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.Files;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.google.firebase.crashlytics.buildtools.reloc.com.google.common.io.MoreFiles.getFileExtension;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class AddItemsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    Button firstTimig_,choseFile_;
    ImageView backBtn;
    String getAppointmentTypee;
    ImageView imgView_;
    String selectedSection="";
    EditText edDetails_,edPrice_,edRemarks_;
    String selectedFoodImgUri="";
    Uri selectedFoodImgUriFromGallary;
    String[] section={"Indoor plants","Outdoor plants","Agricultural tools"};
    ProgressDialog progressBar;
    private static final int PICK_IMAGE_REQUEST = 1;
    String source = "";
    String sTDetails,sTPrice,sTRemarks,sTDataKey;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_items);

        backBtn = findViewById(R.id.backBtn);
        firstTimig_ = findViewById(R.id.ButtSave);
        choseFile_ = findViewById(R.id.choseFile);
        imgView_ = findViewById(R.id.imgView);
        edDetails_ = findViewById(R.id.edDetails);
        edPrice_ = findViewById(R.id.edPrice);
        edRemarks_ = findViewById(R.id.edRemarks);
        Bundle bundle = getIntent().getExtras();

        try {
            source = bundle.getString("Source");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        Spinner spin = (Spinner) findViewById(R.id.spinnerFood);
        spin.setOnItemSelectedListener(this);

        // Create the custom adapter instance
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(this, R.layout.custom_spinner_item, section);
        // Set the custom adapter for the Spinner
        spin.setAdapter(adapter);

        firstTimig_.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                sTDetails = edDetails_.getText().toString();
                sTPrice = edPrice_.getText().toString();
                sTRemarks = edRemarks_.getText().toString();

                if (TextUtils.isEmpty(sTDetails)) {
                    Toast.makeText(getApplicationContext(), "Enter Details", Toast.LENGTH_SHORT).show();
                    edDetails_.setError("Enter Details");
                } else if (TextUtils.isEmpty(sTPrice)) {

                    Toast.makeText(getApplicationContext(), "Enter Price", Toast.LENGTH_SHORT).show();
                    edPrice_.setError("Enter Price");
                } else if (TextUtils.isEmpty(sTRemarks)) {
                    Toast.makeText(getApplicationContext(), "Enter Remarks", Toast.LENGTH_SHORT).show();
                    edRemarks_.setError("Enter Remarks");
                }  else {

                    if (selectedFoodImgUriFromGallary != null) {
                        uploadImageToFirebaseStorage(selectedFoodImgUriFromGallary);
                    } else {
                        Toast.makeText(AddItemsActivity.this, "Please attach Image", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        choseFile_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImageFromGallery();
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position,long id) {
        // Toast.makeText(getApplicationContext(), foodNames[position], Toast.LENGTH_LONG).show();
        selectedSection = section[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
// TODO Auto-generated method stub

    }
    private void selectImageFromGallery() {

        ActivityCompat.requestPermissions(this,new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE
        },100);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            imgView_.setVisibility(View.VISIBLE);
            imgView_.setImageURI(imageUri);
            selectedFoodImgUriFromGallary = imageUri;
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void uploadImageToFirebaseStorage(Uri imageUri) {
        progressBar=new ProgressDialog(this);
        progressBar.setMessage("Uploading Image");
        progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressBar.setIndeterminate(true);
        progressBar.setProgress(0);
        progressBar.show();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("uploads");
        final StorageReference fileReference = storageReference.child(System.currentTimeMillis() + "." + getFileExtension(Paths.get(imageUri + "")));

        fileReference.putFile(imageUri)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                        progressBar.setProgress((int) progress);

                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get the download URL of the uploaded image
                        fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                progressBar.dismiss();
                                String imageURL = uri.toString();



                                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference userRef = mDatabase.child("all_Image_Folder").push();

                                // Create a map of user data
                                Map<String, Object> userData = new HashMap<>();
                                userData.put("selectedSection", selectedSection);
                                userData.put("imageURL", imageURL);
                                userData.put("source", source);
                                userData.put("Details", sTDetails);
                                userData.put("Price", sTPrice); // Note the typo, should be "maritalstatus"?
                                userData.put("userId", userId);
                                userData.put("id", "");
                                userData.put("Remarks", sTRemarks);
                                userData.put("DataKey", userRef.getKey());
                                // Set user data in "all Image Folder" node
                                userRef.setValue(userData)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(AddItemsActivity.this, "Save Data successful!!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }

                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar.dismiss();
                        // Handle unsuccessful uploads
                    }
                })
                .addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        progressBar.dismiss();
                    }
                });
    }


}