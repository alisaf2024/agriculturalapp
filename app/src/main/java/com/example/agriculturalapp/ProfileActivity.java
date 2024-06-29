package com.example.agriculturalapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "User_Profile";
    private FirebaseFirestore db;
    private Button updateProfileBtn;
    EditText edUsername, edReligion, edMeritalStatus;
    TextView edEmail, edPassword;
    String SAVE_USER_DATA_INFO = "User_Profile";
    String userName, userEmail, userPass;
    UserDataModel updateModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Log.i(TAG, "onComplete: CurrentUserId" + "\n" + currentuser);
        db = FirebaseFirestore.getInstance();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        edEmail = findViewById(R.id.ed_email_rg_profile);
        edUsername = findViewById(R.id.ed_name_rg_profile);
        edPassword = findViewById(R.id.ed_pass_rg_profile);
        edReligion = findViewById(R.id.ed_religion);
        edMeritalStatus = findViewById(R.id.ed_Merital);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        db.collection("userdata")
                .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .collection("userdata")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            UserDataModel updateModel = doc.toObject(UserDataModel.class);
                            updateModel.setId(doc.getId());


                            // Populate EditText fields with retrieved data
                            edEmail.setText(updateModel.email);
                            edUsername.setText(updateModel.firstname);
                            edPassword.setText(updateModel.password);
                            edReligion.setText(updateModel.religion);
                            edMeritalStatus.setText(updateModel.meritalstatus);

                            Log.i(TAG, "UserDataModel: " + updateModel.toString());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e(TAG, "Error fetching user data: " + e.getMessage());
                        // Handle failure gracefully
                    }
                });


        updateProfileBtn = findViewById(R.id.update_profile);
        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateModel.setFirstname(edUsername.getText().toString());
                updateModel.setReligion(edReligion.getText().toString());
                updateModel.setMeritalstatus(edMeritalStatus.getText().toString());
                db.collection("userdata")
                        .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .collection("User Data")
                        .document(updateModel.getId())
                        .set(updateModel, SetOptions.merge())
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ProfileActivity.this, "Details has been updated..", Toast.LENGTH_SHORT).show();

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {


                            }
                        });
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}