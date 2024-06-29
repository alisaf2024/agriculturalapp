package com.example.agriculturalapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.agriculturalapp.supplier.DataModal;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GallaryAdapter extends ArrayAdapter<DataModal> {

        // constructor for our list view adapter.
        public GallaryAdapter(@NonNull Context context, ArrayList<DataModal> dataModalArrayList) {
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

                                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                                builder.setMessage("Are you sure you want to pay?")
                                        .setCancelable(false)
                                        .setPositiveButton("Pay", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                        Intent intent = new Intent(getContext(), PaymentActivity.class);
                                                        // Put all data from the booking object into the intent
                                                        intent.putExtra("Details",dataModal.getDetails());
                                                        intent.putExtra("price",dataModal.getPrice());
                                                        intent.putExtra("userId",dataModal.getUserId());
                                                        getContext().startActivity(intent);
                                                }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int id) {
                                                        dialog.cancel();
                                                }
                                        });
                                AlertDialog alert = builder.create();
                                alert.show();
                                        Toast.makeText(getContext(), "Item clicked is : " + dataModal.getDetails(), Toast.LENGTH_SHORT).show();

                        }
                });


                return listItemView;
        }
}

