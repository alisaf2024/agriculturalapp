package com.example.agriculturalapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DeleteUserAdminActivity extends AppCompatActivity {
    TextView tv_userName, tv_userEmail, tv_userPhoneNumber,D;
    String  documentId,  getuserName, getuserEmail, getuserPhoneNum;
    DocumentReference productIdRef;

    private FirebaseFirestore db;
    ImageView backBtn, deleteRecord;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delete_user_admin);
        Intent intent = getIntent();
        GetListUserModel myGroupsModel = intent.getParcelableExtra("MyList");


        tv_userName = findViewById(R.id.tv_userName);
        tv_userEmail = findViewById(R.id.tv_userEmail);
        tv_userPhoneNumber = findViewById(R.id.tv_userPhoneNumber);
        D = findViewById(R.id.dec);

        db = FirebaseFirestore.getInstance();
        Bundle bundle = getIntent().getExtras();

        getuserName = bundle.getString("userName");
        getuserEmail = bundle.getString("userEmail");
        getuserPhoneNum = bundle.getString("userPhone");
        documentId = bundle.getString("userId");

        tv_userName.setText("User Name: " + getuserName);
        tv_userEmail.setText("User Email: " + getuserEmail);
        tv_userPhoneNumber.setText("Phone No: " + getuserPhoneNum);
        D.setText("documentId: " + documentId);
        backBtn = findViewById(R.id.backBtn);
        deleteRecord = findViewById(R.id.deleteRecord);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        deleteRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteItem();
            }
        });
    }

    public void deleteItem() {
        new AlertDialog.Builder(DeleteUserAdminActivity.this)
                .setTitle("Are you sure to delete?")
                .setMessage("Click yes to delete movie")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("AlertDialog", "Positive");
                        productIdRef = db.collection("All users data")
                                .document(documentId);
                        productIdRef.delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        StorageReference Ref = FirebaseStorage.getInstance().getReferenceFromUrl(documentId);
                                        Ref.delete()
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        Toast.makeText(DeleteUserAdminActivity.this, "Deleted successfully!!", Toast.LENGTH_SHORT).show();
                                                        Intent intent = new Intent(DeleteUserAdminActivity.this, AdminMainActivity.class);
                                                        startActivity(intent);
                                                        finish();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Failed to delete the image
                                                        Log.e("FirebaseStorage", "Failed to delete image: " + e.getMessage());
                                                    }
                                                });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(DeleteUserAdminActivity.this, "Failed!!!", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("AlertDialog", "Negative");
                        dialog.dismiss();
                    }
                })
                .show();
    }
}