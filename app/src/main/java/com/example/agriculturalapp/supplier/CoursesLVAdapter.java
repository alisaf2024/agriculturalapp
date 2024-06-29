package com.example.agriculturalapp.supplier;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.agriculturalapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CoursesLVAdapter extends ArrayAdapter<DataModal> {

        // constructor for our list view adapter.
        public CoursesLVAdapter(@NonNull Context context, ArrayList<DataModal> dataModalArrayList) {
                super(context, 0, dataModalArrayList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // below line is use to inflate the
                // layout for our item of list view.
                View listItemView = convertView;
                if (listItemView == null) {
                        listItemView = LayoutInflater.from(getContext()).inflate(R.layout.image_lv_item, parent, false);
                }

                // after inflating an item of listview item
                // we are getting data from array list inside
                // our modal class.
                DataModal dataModal = getItem(position);

                // initializing our UI components of list view item.
                TextView details = listItemView.findViewById(R.id.idDetext);
                TextView price = listItemView.findViewById(R.id.idPrtext);
                TextView note = listItemView.findViewById(R.id.idNotext);
                TextView A = listItemView.findViewById(R.id.id);

                ImageView courseIV = listItemView.findViewById(R.id.idIVimage);
                // after initializing our items we are
                // setting data to our view.
                // below line is use to set data to our text view.
                details.setText(dataModal.getDetails());
                price.setText(dataModal.getPrice());
                note.setText(dataModal.getRemarks());
                A.setText(dataModal.getRemarks());
                // in below line we are using Picasso to
                // load image from URL in our Image VIew.
                Picasso.get().load(dataModal.getImageURL()).into(courseIV);

                // below line is use to add item click listener
                // for our item of list view.
                listItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                                // Get the key of the item to delete
                                // Assuming DataModal has a getKey() method that returns the Firebase key
                               String key = A.getText().toString();

                                if (key != null && !key.isEmpty()) {
                                        // Remove item from Firebase Realtime Database
                                        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("all_Image_Folder"); // Replace with your database node name
                                        databaseReference.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                        // Item removed successfully from Firebase, now update your local data and UI
                                                        remove(dataModal); // Remove from the local dataset
                                                        notifyDataSetChanged(); // Refresh list view
                                                        Toast.makeText(getContext(), "Item deleted", Toast.LENGTH_SHORT).show();
                                                }
                                        }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                        // Handle failure
                                                        Toast.makeText(getContext(), "Failed to delete item: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                        });
                                        Toast.makeText(getContext(), "Item clicked is : " + dataModal.getDetails(), Toast.LENGTH_SHORT).show();
                                }
                        }
                });


                return listItemView;
        }
}

