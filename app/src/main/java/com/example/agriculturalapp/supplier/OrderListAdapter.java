package com.example.agriculturalapp.supplier;


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

import com.example.agriculturalapp.Order;
import com.example.agriculturalapp.PaymentActivity;
import com.example.agriculturalapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderListAdapter extends ArrayAdapter<Order> {

        // constructor for our list view adapter.
        public OrderListAdapter(@NonNull Context context, ArrayList<Order> dataModalArrayList) {
                super(context, 0, dataModalArrayList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                // below line is use to inflate the
                // layout for our item of list view.
                View listItemView = convertView;
                if (listItemView == null) {
                        listItemView = LayoutInflater.from(getContext()).inflate(R.layout.orderlist_lv_item, parent, false);
                }

                // after inflating an item of listview item
                // we are getting data from array list inside
                // our modal class.
                Order dataModal = getItem(position);

                // initializing our UI components of list view item.
                TextView details = listItemView.findViewById(R.id.idDetext);
                TextView price = listItemView.findViewById(R.id.idPrtext);
                TextView note = listItemView.findViewById(R.id.idName);

                // after initializing our items we are
                // setting data to our view.
                // below line is use to set data to our text view.
                details.setText(dataModal.getDetails());
                price.setText(dataModal.getPrice());
                note.setText(dataModal.getOrder_UsdrID());



                // below line is use to add item click listener
                // for our item of list view.
                listItemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                                        Toast.makeText(getContext(), "Item clicked is : " + dataModal.getDetails(), Toast.LENGTH_SHORT).show();

                        }
                });


                return listItemView;
        }
}

