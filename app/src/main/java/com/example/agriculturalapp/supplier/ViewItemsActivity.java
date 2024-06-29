package com.example.agriculturalapp.supplier;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.agriculturalapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewItemsActivity extends AppCompatActivity {
    // creating a variable for our list view,
    // arraylist and firebase Firestore.
    ListView coursesLV;
    ArrayList<DataModal> dataModalArrayList;
    FirebaseFirestore db;
    ImageView backBtn;
    String userId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);


        backBtn = findViewById(R.id.backBtn);
// below line is use to initialize our variables
        coursesLV = findViewById(R.id.idLVCourses);
        dataModalArrayList = new ArrayList<>();

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance();
        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
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
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference();
        // Get a reference to your Firebase Realtime Database
        Query query = databaseRef.child("all_Image_Folder").orderByChild("userId").equalTo(userId);
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
                            // Add the converted object to your ArrayList
                            dataModalArrayList.add(dataModal);
                        }
                    }

                    // Create an adapter with the updated dataModalArrayList
                    CoursesLVAdapter adapter = new CoursesLVAdapter(ViewItemsActivity.this, dataModalArrayList);
                    // Set the adapter to your ListView
                    coursesLV.setAdapter(adapter);
                } else {
                    // If no data is available, show a toast message
                    Toast.makeText(ViewItemsActivity.this, "No data found in Database", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle any errors
                Toast.makeText(ViewItemsActivity.this, "Failed to load data: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}