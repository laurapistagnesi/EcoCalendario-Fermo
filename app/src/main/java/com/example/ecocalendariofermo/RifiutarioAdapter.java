package com.example.ecocalendariofermo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RifiutarioAdapter extends ArrayAdapter<Rifiuto> {

    private Context mContext;
    private int mResource;

    public RifiutarioAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Rifiuto> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);
        TextView nome = convertView.findViewById(R.id.rifiuto);
        nome.setText(getItem(position).getName());
        return convertView;
    }
}
