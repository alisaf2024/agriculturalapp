package com.example.agriculturalapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ListUserAdminActivity extends AppCompatActivity {
    private List<UserDataModel> UserList;
    private GridView gridView;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_list_user_admin);
        gridView = findViewById(R.id.gridView);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference("userdata");
        UserList = new ArrayList<>();
        // Retrieve data from the "AllUsersBooking" collection
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserList.clear(); // Clear the list before adding new data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserDataModel user = snapshot.getValue(UserDataModel.class);
                    UserList.add(user);
                }

                // Set up the adapter and populate the GridView
                ListAdapter adapter = new ListAdapter(ListUserAdminActivity.this, UserList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("TAG", "Error getting data", databaseError.toException());
            }
        });

    }
    public class ListAdapter extends BaseAdapter {

        private Context context;
        private List<UserDataModel> UserList;

        public ListAdapter(Context context, List<UserDataModel> userList) {
            this.context = context;
            this.UserList = userList;
        }

        @Override
        public int getCount() {
            return UserList.size();
        }

        @Override
        public Object getItem(int position) {
            return UserList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;

            if (view == null) {
                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.showlistuser, null);
            }

            // Bind data to the view
            TextView tvData = view.findViewById(R.id.tvData);
            ImageView img = view.findViewById(R.id.img);

            //  TextView userEmailTextView = view.findViewById(R.id.userEmailTextView);

            UserDataModel User = UserList.get(position);
            tvData.setText(User.getFirstname());
            // userEmailTextView.setText(booking.getUserEmail());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create an intent to start the target activity
                    Intent intent = new Intent(context, DeleteUserAdminActivity.class);
                    // Put all data from the booking object into the intent
                    intent.putExtra("userName", User.getFirstname());
                    intent.putExtra("userEmail", User.getEmail());
                    intent.putExtra("userPhone", User.getPhone());
                    intent.putExtra("userId", User.getUserId());
                    context.startActivity(intent);
                }
            });

            return view;
        }
    }

}