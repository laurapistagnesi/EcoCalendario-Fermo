package com.example.ecocalendariofermo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import java.util.ArrayList;

public class MemoAdapter extends ArrayAdapter<Memo> {
    private Context mContext;
    private int mResource;

    public MemoAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Memo> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);

        convertView = layoutInflater.inflate(mResource, parent, false);
        ImageView imageView = convertView.findViewById(R.id.image);
        TextView title = convertView.findViewById(R.id.title);
        TextView subtitle = convertView.findViewById(R.id.subtitle);

        imageView.setImageResource(getItem(position).getImage());
        title.setText(getItem(position).getTitle());
        subtitle.setText(getItem(position).getSubtitle());
        return convertView;
    }

}
