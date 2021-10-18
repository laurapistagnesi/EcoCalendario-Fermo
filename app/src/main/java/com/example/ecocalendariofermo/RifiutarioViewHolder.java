package com.example.ecocalendariofermo;

import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RifiutarioViewHolder extends RecyclerView.ViewHolder {

    public TextView nomeRifiuto;

    public RifiutarioViewHolder(@NonNull View itemView) {
        super(itemView);
        nomeRifiuto = itemView.findViewById(R.id.rifiuto);
    }
}
