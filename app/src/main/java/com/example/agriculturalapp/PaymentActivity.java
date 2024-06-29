package com.example.agriculturalapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PaymentActivity extends AppCompatActivity {
String SupplierID,details,price,UserOrder;
int A;
    TextView text1,text2,text3;
    Button button;
    private FirebaseAuth auth;
    EditText Account,Date,CCV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        auth = FirebaseAuth.getInstance();
        Bundle bundle = getIntent().getExtras();

        details = bundle.getString("Details");
        price = bundle.getString("price");
        SupplierID = bundle.getString("userId");

        text1=findViewById(R.id.idDetext);
        text1.setText(details);
        text2=findViewById(R.id.idPrtext);
        text2.setText(price);
        text3=findViewById(R.id.idUserID);
        text3.setText(SupplierID);

        Account=findViewById(R.id.id_Account);



        Date=findViewById(R.id.id_Date);

        CCV=findViewById(R.id.id_CVV);


        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        UserOrder=auth.getCurrentUser().getEmail();
        button=findViewById(R.id.but_Pay);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Initialize Firebase in your onCreate method
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseRef = database.getReference("All_Orders"); // Replace with your node name
                String a=Account.getText().toString();
                String  b=Date.getText().toString();
                String c=CCV.getText().toString();
                Order newData = new Order(details,price,SupplierID,a,b,c,UserOrder);

              // Generate a new key for the new data
                String key = databaseRef.push().getKey();

                 // Insert data into Firebase
                databaseRef.child(key).setValue(newData)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // Data successfully inserted
                                Toast.makeText(getApplicationContext(), "Done", Toast.LENGTH_SHORT).show();
                            finish();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Failed to insert data
                                Toast.makeText(getApplicationContext(), "Failed to insert database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}