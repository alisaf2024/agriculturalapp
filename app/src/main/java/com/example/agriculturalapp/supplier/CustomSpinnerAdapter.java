package com.example.agriculturalapp.supplier;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomSpinnerAdapter extends ArrayAdapter<String> {
    private Context mContext;
    private String[] mData;
    private int mResource;

    public CustomSpinnerAdapter(Context context, int resource, String[] data) {
        super(context, resource, data);
        mContext = context;
        mResource = resource;
        mData = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(mResource, parent, false);

        TextView textView = row.findViewById(android.R.id.text1);
        textView.setText(mData[position]);
        textView.setTextColor(Color.BLACK); // Set your desired text color here

        return row;
    }
}

