package com.example.ecocalendariofermo;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class InfoViewHolder extends RecyclerView.ViewHolder {

    public TextView titolo;
    public ImageView immagine;

    public InfoViewHolder(@NonNull View itemView) {
        super(itemView);
        titolo = itemView.findViewById(R.id.titolo_info);
        immagine = itemView.findViewById(R.id.image_info);
    }
}
