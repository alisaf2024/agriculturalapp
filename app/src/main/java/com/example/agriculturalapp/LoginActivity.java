package com.example.agriculturalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agriculturalapp.supplier.SupplierActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    TextView signup, tv_forgotPass;
    Button signin_btn;
    EditText password, email;
    private FirebaseAuth auth;
    private static final String TAG = "Login";
    String SAVE_USER_DATA_INFO = "User_Profile";
    private FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);


        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        email = findViewById(R.id.edEmail);
        password = findViewById(R.id.edPassword);

        signin_btn = findViewById(R.id.loginBtn);
        signin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail = email.getText().toString();
                String userPass = password.getText().toString();

                if (TextUtils.isEmpty(userEmail)) {
                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    email.setError("Enter Email");
                    return;
                }
                if (TextUtils.isEmpty(userPass)) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    password.setError("Enter Password");
                    return;
                }
                if (userPass.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Enter minimum 6 digit pass", Toast.LENGTH_SHORT).show();
                    password.setError("Enter 6 digit Password");
                    return;
                }

                ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                progressDialog.setMessage("Loading");
                progressDialog.show();

                // Sign in with email and password
                auth.signInWithEmailAndPassword(userEmail, userPass)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressDialog.dismiss();
                                if (task.isSuccessful()) {
                                    // Sign in success
                                    // Check user's role and redirect accordingly
                                    checkUserRoleAndRedirect();
                                } else {
                                    // Sign in failed
                                    Toast.makeText(LoginActivity.this, "Email Password Not Register", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progressDialog.dismiss();
                                Log.e(TAG, "onFailure: OnFailure Login" + "\n" + e.getLocalizedMessage());
                            }
                        });
            }
        });

        signup = findViewById(R.id.tv_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        tv_forgotPass = findViewById(R.id.tv_forgotPass);
        tv_forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgotPassword.class);
                startActivity(intent);
            }
        });
    }

    private void checkUserRoleAndRedirect_() {
        // Check user role in Firestore and redirect accordingly
        if (auth.getCurrentUser() != null) {
            db.collection("All users data")
                    .document(auth.getCurrentUser().getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            SharedPref sharedPreferencesManager = new SharedPref(getApplicationContext());
                            if (task.isSuccessful()) {
                                if (email.getText().toString().equals("admin@gmail.com") || email.getText().toString().equals("admin@admin.com")) {
                                    sharedPreferencesManager.setUserType(SharedPref.USER_TYPE_ADMIN);
                                    startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                } else {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        String userType = document.getString("selectedRole");
                                        if (userType != null) {
                                            switch (userType) {
                                                case "Register as supplier":
                                                    sharedPreferencesManager.setUserType(SharedPref.USER_TYPE_RIDER);
                                                    startActivity(new Intent(LoginActivity.this, SupplierActivity.class));
                                                    break;
                                                case "Register as User":
                                                    sharedPreferencesManager.setUserType(SharedPref.USER_TYPE_USER);
                                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                                    break;
                                                // Add more cases for other user types if needed
                                                default:
                                                    // Handle default case
                                                    break;
                                            }
                                            finish(); // Finish login activity after redirection
                                        }
                                    } else {
                                        // User data not found
                                        Toast.makeText(LoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                // Error getting document
                                Toast.makeText(LoginActivity.this, "Error getting user data", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Error getting document: ", task.getException());
                            }
                        }
                    });
        }
    }
    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private void checkUserRoleAndRedirect() {
        // Check user role in Realtime Database and redirect accordingly
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            DatabaseReference userRef = mDatabase.child("userdata").child(currentUser.getUid());
            userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    SharedPref sharedPreferencesManager = new SharedPref(getApplicationContext());
                    if (snapshot.exists()) {
                        String userType = snapshot.child("selectedRole").getValue(String.class);
                        if (userType != null) {
                            switch (userType) {
                                case "Register as Supplier":
                                    sharedPreferencesManager.setUserType(SharedPref.USER_TYPE_RIDER);
                                    startActivity(new Intent(LoginActivity.this, SupplierActivity.class));
                                    break;
                                case "Register as User":
                                    sharedPreferencesManager.setUserType(SharedPref.USER_TYPE_USER);
                                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                                    break;
                                case "Register as admin":
                                    sharedPreferencesManager.setUserType(SharedPref.USER_TYPE_USER);
                                    startActivity(new Intent(LoginActivity.this, AdminMainActivity.class));
                                    break;
                                // Add more cases for other user types if needed
                                default:
                                    // Handle default case
                                    break;
                            }
                            finish(); // Finish login activity after redirection
                        } else {
                            // User type not found
                            Toast.makeText(LoginActivity.this, "User type not found", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // User data not found
                        Toast.makeText(LoginActivity.this, "User data not found", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    // Error handling
                    Toast.makeText(LoginActivity.this, "Error getting user data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error getting user data: " + error.getMessage());
                }
            });
        }
    }

}
