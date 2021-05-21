package com.hrhera.cahdcaht.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hrhera.cahdcaht.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MSpinnerAdapter extends ArrayAdapter<Integer> {
    LayoutInflater flater;

    public MSpinnerAdapter(@NonNull Activity context, int resource, List<Integer> list) {
        super(context, resource, list);
        flater = context.getLayoutInflater();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        int rowItem = getItem(position);
        @SuppressLint("ViewHolder") View rowview = flater.inflate(R.layout.row_one_image,parent,false);
        ImageView imageView = rowview.findViewById(R.id.image_to_select);
        Picasso.get().load(rowItem).fit().centerCrop().into(imageView);
        return rowview;
    }


    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int rowItem = getItem(position);
        @SuppressLint("ViewHolder") View rowview = flater.inflate(R.layout.row_one_image,parent,false);
        ImageView imageView = rowview.findViewById(R.id.image_to_select);
        Picasso.get().load(rowItem).fit().centerCrop().into(imageView);
        return rowview;
    }
}
