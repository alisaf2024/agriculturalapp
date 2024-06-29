package com.example.agriculturalapp;

import android.annotation.SuppressLint;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

//import kotlin.io.LineReader;


public class homeFragment extends Fragment {
    private FirebaseAuth auth;
    private List<UserDataModel> UserList;
    private ListView gridView;
    String  statues = "Register as Supplier";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        gridView = view.findViewById(R.id.gridView);
        auth = FirebaseAuth.getInstance();
        UserList = new ArrayList<>();
        // Retrieve data from the "All Supplier" collection


       Query usersRef = FirebaseDatabase.getInstance().getReference("userdata").orderByChild("selectedRole").equalTo(statues);;
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<UserDataModel> userList = new ArrayList<>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    UserDataModel user = snapshot.getValue(UserDataModel.class);
                    if (user != null) {
                        // Optionally set the ID if you need it (Firebase Realtime Database does not provide document IDs like Firestore)
                        user.setId(snapshot.getKey());
                        userList.add(user);
                    }
                }

                // Set up the adapter and populate the GridView
                ListAdapter adapter = new ListAdapter(homeFragment.this, userList);
                gridView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle potential errors
                Log.e("TAG", "Error getting data", databaseError.toException());
            }
        });


        return view;
    }

    void setListners(LinearLayout linearLayout, Class classname){

        for (int i = 0; i < linearLayout.getChildCount(); i++) {
            View child = linearLayout.getChildAt(i);
            child.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (auth.getCurrentUser() != null) {
                        Intent intent = new Intent(getContext(), classname);
                        startActivity(intent);
                    }else{
                        Toast.makeText(getContext(), "You must login !!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
    public class ListAdapter extends BaseAdapter {

        private homeFragment context;
        private List<UserDataModel> UserList;

        public ListAdapter(homeFragment context, List<UserDataModel> userList) {
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
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.showallsupplierlist, null);
            }

            // Bind data to the view
            TextView tvData1 = view.findViewById(R.id.tvData1);
            TextView tvData2 = view.findViewById(R.id.tvData2);
            //  TextView userEmailTextView = view.findViewById(R.id.userEmailTextView);
            UserDataModel User = UserList.get(position);
            tvData1.setText(User.getFirstname());
            tvData2.setText(User.getPhone());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (auth.getCurrentUser() != null) {
                        // Create an intent to start the target activity
                        Intent intent = new Intent(getContext(), ListAllCatogryActivity.class);
                        // Put all data from the booking object into the intent
                        intent.putExtra("userName", User.getFirstname());
                        intent.putExtra("userEmail", User.getEmail());
                        intent.putExtra("userPhone", User.getPhone());
                        intent.putExtra("userId", User.getUserId());
                        context.startActivity(intent);
                    }else{
                        Toast.makeText(getContext(), "You must login !!", Toast.LENGTH_SHORT).show();
                    }

                }
            });


            return view;
        }

    }

}