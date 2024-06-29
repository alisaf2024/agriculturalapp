package com.example.agriculturalapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ListAllCatogryActivity extends AppCompatActivity {
    ImageView backBtn;
    Button bu1,bu2,bu3;
    String SupplierID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_catogry);

        Bundle bundle = getIntent().getExtras();
        SupplierID = bundle.getString("userId");


        bu1= findViewById(R.id.but_indoor);
        bu2= findViewById(R.id.but_Outdoor);
        bu3= findViewById(R.id.but_tools);

        bu1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListAllCatogryActivity.this, GalleryActivity.class);
                // Put all data from the booking object into the intent
                intent.putExtra("userId",SupplierID);
                intent.putExtra("section","Indoor plants");
                startActivity(intent);
            }
            });
        bu2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListAllCatogryActivity.this, GalleryActivity.class);
                // Put all data from the booking object into the intent
                intent.putExtra("userId",SupplierID);
                intent.putExtra("section","Outdoor plants");
                startActivity(intent);

            }
        });
        bu3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ListAllCatogryActivity.this, GalleryActivity.class);
                // Put all data from the booking object into the intent
                intent.putExtra("userId",SupplierID);
                intent.putExtra("section","Agricultural tools");
                startActivity(intent);

            }
        });
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}