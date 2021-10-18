package com.example.ecocalendariofermo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.example.ecocalendariofermo.AddMemoActivity.titoloPromemoria;

public class DeleteMemoActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "Memo";
    public TextView titolo1;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_memo);
        getSupportActionBar().setTitle("Elimina Promemoria");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#166318")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        titolo1 = findViewById(R.id.titolo1);

        listView = findViewById(R.id.memoList);
        ArrayList<Memo> arrayList = new ArrayList<>();

        String t1 = sharedPreferences.getString("Email", null);
        Log.d(TAG, "email " +t1);

        String t2 = sharedPreferences.getString(titoloPromemoria, null);
        Log.d(TAG, "titolo "+t2);
        titolo1.setText(t2);

        if(sharedPreferences.contains("Sveglia lunedi")){
            String m1 = getDate(sharedPreferences.getLong("Sveglia lunedi", 0), "HH:mm");
            Log.d(TAG, "SVEGLIA LUNEDI ALLE ORE " + m1);
            arrayList.add(new Memo("Lunedi", m1, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia martedi")){
            String m2 = getDate(sharedPreferences.getLong("Sveglia martedi", 0), "HH:mm");
            Log.d(TAG, "SVEGLIA MARTEDI ALLE ORE " + m2);
            arrayList.add(new Memo("Martedi", m2, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia mercoledi")){
            String m3 = getDate(sharedPreferences.getLong("Sveglia mercoledi", 0), "HH:mm");
            Log.d(TAG, "SVEGLIA MERCOLEDI ALLE ORE " + m3);
            arrayList.add(new Memo("Mercoledi", m3, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia giovedi")){
            String m4 = getDate(sharedPreferences.getLong("Sveglia giovedi", 0), "HH:mm");
            Log.d(TAG, "SVEGLIA GIOVEDI ALLE ORE " + m4);
            arrayList.add(new Memo("Giovedi", m4, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia venerdi")){
            String m5 = getDate(sharedPreferences.getLong("Sveglia venerdi", 0), "HH:mm");
            Log.d(TAG, "SVEGLIA VENERDI ALLE ORE " + m5);
            arrayList.add(new Memo("Venerdi", m5, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia sabato")){
            String m6 = getDate(sharedPreferences.getLong("Sveglia sabato", 0), "HH:mm");
            Log.d(TAG, "SVEGLIA SABATO ALLE ORE " + m6);
            arrayList.add(new Memo("Sabato", m6, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia domenica")){
            String m7 = getDate(sharedPreferences.getLong("Sveglia domenica", 0), "HH:mm");
            Log.d(TAG, "SVEGLIA DOMENICA ALLE ORE " + m7);
            arrayList.add(new Memo("Domenica", m7, R.drawable.ic_vetro));
        }

        MemoAdapter memoAdapter = new MemoAdapter(this, R.layout.list_row, arrayList);
        listView.setAdapter(memoAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(DeleteMemoActivity.this);
                builder.setTitle("Elimina");
                builder.setMessage("Sei sicuro di voler eliminare il promemoria?");
                builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                        String item = arrayList.get(position).toString();
                        if(arrayList.get(position).getTitle().equals("Lunedi")){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    1, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia lunedi").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals("Martedi")){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    2, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia martedi").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals("Mercoledi")){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    3, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia mercoledi").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals("Giovedi")){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    4, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia giovedi").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals("Venerdi")){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    5, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia venerdi").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals("Sabato")){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    6, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia sabato").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals("Domenica")){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    7, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia domenica").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        finish();
                        startActivity(getIntent());
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(DeleteMemoActivity.this, CalendarActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;
            case R.id.refresh_button:
                finish();
                startActivity(getIntent());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_memo, menu);
        return super.onCreateOptionsMenu(menu);
    }
}