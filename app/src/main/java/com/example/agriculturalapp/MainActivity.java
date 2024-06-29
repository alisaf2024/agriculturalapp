package com.example.agriculturalapp;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;
    NavigationView navigationView;
    FrameLayout frameLayout;
    ActionBarDrawerToggle toggle;
    ImageView imageView;
    TextView tvuserName, tvuserEmail;
    Toolbar toolbar;
    View header;

    private static final String TAG = "MainActivity";
    private FirebaseFirestore db;
    private Button updateProfileBtn;
    String SAVE_USER_DATA_INFO = "User_Profile";
    String userName, userEmail, userPass;
    UserDataModel updateModel;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth = FirebaseAuth.getInstance();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);

        frameLayout = (FrameLayout) findViewById(R.id.frame);

        try {

            String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();
            Log.i(TAG, "onComplete: CurrentUserId" + "\n" + currentuser);

        } catch (Exception e) {
            //   throw new RuntimeException(e);
        }
        db = FirebaseFirestore.getInstance();




        header = navigationView.getHeaderView(0);
        imageView = header.findViewById(R.id.imgView);
        tvuserName =  header.findViewById(R.id.userName);
        tvuserEmail =  header.findViewById(R.id.userEmail);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.closeDrawer(GravityCompat.START);
            }
        });
       /* userName.setText(userDataModel.getFirstname());
        userEmail.setText(userDataModel.getEmail());*/
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //set default fragment
      loadFragment(new homeFragment());


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {

        }else {
            db.collection("userdata")
                    .document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                    .collection("User Data")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                                UserDataModel updateModel = doc.toObject(UserDataModel.class);
                                updateModel.setId(doc.getId());

                                // Assuming you want to update UI elements with retrieved data
                                if (auth.getCurrentUser() != null) {
                                    // Example of updating UI elements (if needed)
                                    // tvuserEmail.setText(updateModel.getEmail());
                                    // tvuserName.setText(updateModel.getFirstname());

                                    Log.i(TAG, "UserDataModel: " + updateModel.toString());
                                }
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error fetching user data: " + e.getMessage());
                        }
                    });

        }

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();

                if (id == R.id.nav_account) {
                    Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                   startActivity(i);
                } else if (id == R.id.nav_login) {
                    if (auth.getCurrentUser() == null) {

                        Intent i = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(i);
                    }else{
                        if (currentUser != null) {
                            menuItem.setEnabled(false);
                        }
                    }

                } else if (id == R.id.nav_aboutUs) {
                    Intent i = new Intent(MainActivity.this, AboutUsActivity.class);
                    startActivity(i);
                } else if (id == R.id.nav_logout) {
                    logout();
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

    }

    private void logout() {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle("Are you sure you want to logout?")
                .setMessage("Click yes if you want to logout")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseAuth.getInstance().signOut();
                        SharedPref sharedPreferencesManager = new SharedPref(getApplicationContext());
                        sharedPreferencesManager.setUserType(SharedPref.USER_TYPE_USER_Logout);
                        Intent intet = new Intent(MainActivity.this, MainActivity.class);
                        intet.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//makesure user cant go back
                        startActivity(intet);
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.d("AlertDialog", "Negative");
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Are you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();

        }
    }

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame, fragment);
        transaction.commit();
        navigationView.getHeaderView(1);
    }

}