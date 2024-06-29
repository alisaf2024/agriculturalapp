package com.example.agriculturalapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import com.example.agriculturalapp.supplier.DataModal;
import com.example.agriculturalapp.supplier.ViewItemsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class GalleryActivity extends AppCompatActivity {
    ListView coursesLV;
    ArrayList<DataModal> dataModalArrayList;
    FirebaseFirestore db;
    ImageView backBtn;
    String SupplierID,Section;
    TextView a;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        Bundle bundle = getIntent().getExtras();
        SupplierID = bundle.getString("userId");

        Section = bundle.getString("section");

        backBtn = findViewById(R.id.backBtn);
        // below line is use to initialize our variables
        coursesLV = findViewById(R.id.idLVCourses);
        dataModalArrayList = new ArrayList<>();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();

        // here we are calling a method
        // to load data in our list view.
        loadDataInListView();

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadDataInListView() {
        // Clear the existing dataModalArrayList before adding new data
        dataModalArrayList.clear();
        // Get a reference to your Firebase Realtime Database
        // Get a reference to your Firebase Realtime Database
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
// Assuming 'userId' is the key under which user data is stored in 'all_Image_Folder'
// Replace "userId" with the actual key used in your database structure
        Query query = databaseRef.child("all_Image_Folder").orderByChild("userId").equalTo(SupplierID);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                // Clear the existing list before adding new data
                dataModalArrayList.clear();

                // Check if there is any data available
                if (dataSnapshot.exists()) {
                    // Iterate through all children (items) in the "all_Image_Folder" node
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Convert each DataSnapshot to your DataModal object
                        DataModal dataModal = snapshot.getValue(DataModal.class);
                        if (dataModal != null) {
                            if (dataModal != null && dataModal.getSelectedSection().equals(Section)) {
                                dataModalArrayList.add(dataModal);
                            }else{
                                Toast.makeText(GalleryActivity.this, "No data in this section", Toast.LENGTH_SHORT).show();
                            }
                            // Add the converted object to your ArrayList

                        }
                    }

                    // Create an adapter with the updated dataModalArrayList
                    GallaryAdapter adapter = new GallaryAdapter(GalleryActivity.this, dataModalArrayList);
                    // Set the adapter to your ListView
                    coursesLV.setAdapter(adapter);
                } else {
                    // If no data is available, show a toast message
                    Toast.makeText(GalleryActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                Toast.makeText(GalleryActivity.this, "Failed to load data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}