package com.example.agriculturalapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.agriculturalapp.supplier.SupplierActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText name, password, email, phone, religion;
    TextView login;
    Button signUp;
    private FirebaseAuth auth;
    FirebaseFirestore db;
    String userName;
    String userEmail;
    String userPass;
    String userPhone;

    private RadioGroup roleRadioGroup;
    private String selectedRole = "";
    private String selectUserType = "";
    ProgressDialog progressDialog;
    String SAVE_USER_DATA_INFO = "User_Profile";
    ImageView showPass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        progressDialog = new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Loading");
        // currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (auth.getCurrentUser() != null) {
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        name = findViewById(R.id.edUserName_rg);
        email = findViewById(R.id.edEmail_rg);
        password = findViewById(R.id.edPassword_rg);
        phone = findViewById(R.id.edPhone_rg);
        religion = findViewById(R.id.edreligion_rg);
        religion.setVisibility(View.GONE);
        login = findViewById(R.id.tv_login);
        signUp = findViewById(R.id.signup_Btn);
        roleRadioGroup = findViewById(R.id.role_radio_group);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        roleRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Handle radio button selection
                RadioButton radioButton = findViewById(checkedId);
                selectedRole = radioButton.getText().toString();
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = name.getText().toString();
                userEmail = email.getText().toString();
                userPass = password.getText().toString();
                userPhone = phone.getText().toString();
                //userReligion = religion.getText().toString();

                if (TextUtils.isEmpty(userName)) {
                    Toast.makeText(getApplicationContext(), "Enter Name", Toast.LENGTH_SHORT).show();
                    name.setError("Enter Name");
                } else if (TextUtils.isEmpty(userEmail)) {

                    Toast.makeText(getApplicationContext(), "Enter Email", Toast.LENGTH_SHORT).show();
                    email.setError("Enter Email");
                } else if (TextUtils.isEmpty(userPass)) {
                    Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                    password.setError("Enter Password");
                } else if (userPass.length() < 6) {

                    Toast.makeText(getApplicationContext(), "Enter minimum 6 digit pass", Toast.LENGTH_SHORT).show();
                    password.setError("Enter 6 digit Password");
                } else if (TextUtils.isEmpty(userPass)) {
                    Toast.makeText(getApplicationContext(), "Enter Phone No", Toast.LENGTH_SHORT).show();
                    password.setError("Enter Phone No");
                } else if (selectedRole.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please Select Role", Toast.LENGTH_SHORT).show();

                } else {
                    progressDialog.show();
                    auth.createUserWithEmailAndPassword(userEmail, userPass)
                            .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        // User registration successful
                                        addUserDataToRealtimeDatabase(); // Add user data to Realtime Database
                                    } else {
                                        // User registration failed
                                        progressDialog.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Registration failed. Please try again.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });



                }

            }
        });
    }


    private void checkIfUserExistsAndProceed() {
        // Check if the user already exists in Realtime Database
        Query query = mDatabase.child("userdata").orderByChild("email").equalTo(userEmail);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // User already exists, show toast
                    progressDialog.dismiss();
                    Toast.makeText(RegisterActivity.this, "User already registered.", Toast.LENGTH_SHORT).show();
                } else {
                    // User does not exist, proceed with adding user data
                    addUserDataToRealtimeDatabase();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Error occurred while checking user existence
                progressDialog.dismiss();
                Log.e(TAG, "Error checking user existence: " + error.getMessage());
                Toast.makeText(RegisterActivity.this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
            }
        });
    }



    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

    private void addUserDataToRealtimeDatabase() {
        // Create a unique key for the user data in Realtime Database
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = mDatabase.child("userdata").child(userId);

        // Create a map of user data
        Map<String, Object> userData = new HashMap<>();
        userData.put("firstname", userName);
        userData.put("email", userEmail);
        userData.put("password", userPass);
        userData.put("phone", userPhone);
        userData.put("meritalstatus", ""); // Note the typo, should be "maritalstatus"?
        userData.put("userId", userId);
        userData.put("id", "");
        userData.put("selectedRole", selectedRole);

        // Set user data in "userdata" node
        userRef.setValue(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Data successfully inserted into Realtime Database
                        progressDialog.dismiss();
                        SharedPref sharedPreferencesManager = new SharedPref(getApplicationContext());
                        if (selectUserType.equals("")) {
                            //Register as Rider
                            if (selectedRole.equals("Register as Supplier")) {
                                sharedPreferencesManager.setUserType(SharedPref.USER_TYPE_RIDER);
                            } else {
                                sharedPreferencesManager.setUserType(SharedPref.USER_TYPE_USER);
                            }
                            String userType = sharedPreferencesManager.getUserType();

                            if (userType != null) {
                                if (userType.equals(SharedPref.USER_TYPE_USER)) {
                                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else if (userType.equals(SharedPref.USER_TYPE_RIDER)) {
                                    Intent intent = new Intent(RegisterActivity.this, SupplierActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        } else {
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }

                        Toast.makeText(RegisterActivity.this, "Registration successful!!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.e(TAG, "Error adding user data to Realtime Database: " + e.getMessage());
                        Toast.makeText(RegisterActivity.this, "An error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

}