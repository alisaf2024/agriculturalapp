package com.example.agriculturalapp.supplier;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.agriculturalapp.R;

public class SupplierActivity extends AppCompatActivity {
    ImageView backBtn;
    Button butAdd,buttView,buttOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);
        backBtn = findViewById(R.id.backBtn);
        butAdd= findViewById(R.id.butt_insert);
        buttView= findViewById(R.id.butt_View);
        buttOrder= findViewById(R.id.butt_ListOrder);

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierActivity.this, AddItemsActivity.class);
                startActivity(intent);
            }
        });
        buttView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierActivity.this, ViewItemsActivity.class);
                startActivity(intent);
            }
        });
        buttOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SupplierActivity.this, ListOrderSupplierActivity.class);
                startActivity(intent);
            }
        });



        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}