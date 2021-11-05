package com.example.ecocalendariofermo;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class CalendarAdapter extends RecyclerView.Adapter<CalendarViewHolder>
{
    private final ArrayList<LocalDate> days;
    private final OnItemListener onItemListener;

    public CalendarAdapter(ArrayList<LocalDate> days, OnItemListener onItemListener)
    {
        this.days = days;
        this.onItemListener = onItemListener;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.calendar_cell, parent, false);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = (int) (parent.getHeight() * 0.166666666);
        return new CalendarViewHolder(view, onItemListener, days);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
        final LocalDate date = days.get(position);
        if(date == null)
        {
            holder.dayOfMonth.setText("");
        }
        else
        {
            holder.dayOfMonth.setText(String.valueOf(date.getDayOfMonth()));
            //In base al giorno inserisce l'icona
            if(date.equals(CalendarActivity.selectedDate) && (date.getDayOfWeek() != DayOfWeek.FRIDAY || date.getDayOfWeek() != DayOfWeek.THURSDAY || date.getDayOfWeek() != DayOfWeek.WEDNESDAY || date.getDayOfWeek() != DayOfWeek.MONDAY))
            {
                holder.parentView.setBackgroundColor(Color.parseColor("#C2EEBD"));
            }
            if (date.getDayOfWeek() == DayOfWeek.MONDAY || date.getDayOfWeek() == DayOfWeek.FRIDAY) {
                holder.parentView.setBackgroundResource(R.drawable.ic_indifferenziato);
            }
            if (date.equals(CalendarActivity.selectedDate) && (date.getDayOfWeek() == DayOfWeek.MONDAY || date.getDayOfWeek() == DayOfWeek.FRIDAY)) {
                holder.parentView.setBackgroundResource(R.drawable.ic_indiff_selected);
            }
            if (date.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                holder.parentView.setBackgroundResource(R.drawable.ic_plastic);
            }
            if (date.equals(CalendarActivity.selectedDate) && date.getDayOfWeek() == DayOfWeek.WEDNESDAY) {
                holder.parentView.setBackgroundResource(R.drawable.ic_plastic_selected);
            }
            if (date.getDayOfWeek() == DayOfWeek.THURSDAY) {
                holder.parentView.setBackgroundResource(R.drawable.ic_carta);
            }
            if (date.equals(CalendarActivity.selectedDate) && date.getDayOfWeek() == DayOfWeek.THURSDAY) {
                holder.parentView.setBackgroundResource(R.drawable.ic_carta_selected);
            }
        }
    }

    @Override
    public int getItemCount()
    {
        return days.size();
    }

    public interface OnItemListener
    {
        void onItemClick(int position, LocalDate date);
    }
}
