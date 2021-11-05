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
    public TextView titolo;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_memo);
        getSupportActionBar().setTitle(R.string.elimina_memo);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#166318")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
        titolo = findViewById(R.id.titolo1);
        listView = findViewById(R.id.memoList);
        ArrayList<Memo> arrayList = new ArrayList<>();
        String t2 = sharedPreferences.getString(titoloPromemoria, null);
        titolo.setText(t2);

        //Prende gli alarm aggiunti nell' AddMemoActivity dalle Shared Preferences e li visualizza
        if(sharedPreferences.contains("Sveglia lunedi")){
            String m1 = getDate(sharedPreferences.getLong("Sveglia lunedi", 0), "HH:mm");
            arrayList.add(new Memo(getString(R.string.lunedi), m1, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia martedi")){
            String m2 = getDate(sharedPreferences.getLong("Sveglia martedi", 0), "HH:mm");
            arrayList.add(new Memo(getString(R.string.martedi), m2, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia mercoledi")){
            String m3 = getDate(sharedPreferences.getLong("Sveglia mercoledi", 0), "HH:mm");
            arrayList.add(new Memo(getString(R.string.mercoledi), m3, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia giovedi")){
            String m4 = getDate(sharedPreferences.getLong("Sveglia giovedi", 0), "HH:mm");
            arrayList.add(new Memo(getString(R.string.giovedi), m4, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia venerdi")){
            String m5 = getDate(sharedPreferences.getLong("Sveglia venerdi", 0), "HH:mm");
            arrayList.add(new Memo(getString(R.string.venerdi), m5, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia sabato")){
            String m6 = getDate(sharedPreferences.getLong("Sveglia sabato", 0), "HH:mm");
            arrayList.add(new Memo(getString(R.string.sabato), m6, R.drawable.ic_vetro));
        }
        if(sharedPreferences.contains("Sveglia domenica")){
            String m7 = getDate(sharedPreferences.getLong("Sveglia domenica", 0), "HH:mm");
            arrayList.add(new Memo(getString(R.string.domenica), m7, R.drawable.ic_vetro));
        }

        MemoAdapter memoAdapter = new MemoAdapter(this, R.layout.list_row, arrayList);
        listView.setAdapter(memoAdapter);

        //In base all'elemento selezionato elimina l'alarm e aggiorna l'activity
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
                        if(arrayList.get(position).getTitle().equals(getString(R.string.lunedi))){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    1, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia lunedi").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals(getString(R.string.martedi))){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    2, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia martedi").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals(getString(R.string.mercoledi))){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    3, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia mercoledi").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals(getString(R.string.giovedi))){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    4, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia giovedi").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals(getString(R.string.venerdi))){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    5, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia venerdi").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals(getString(R.string.sabato))){
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                                    6, intent,
                                    PendingIntent.FLAG_UPDATE_CURRENT);
                            AlarmManager alarm = (AlarmManager) getApplicationContext().getSystemService(Context.ALARM_SERVICE);
                            alarm.cancel(pendingIntent);
                            sharedPreferences.edit().remove("Sveglia sabato").apply();
                            arrayList.remove(item);
                            memoAdapter.notifyDataSetChanged();
                        }
                        if(arrayList.get(position).getTitle().equals(getString(R.string.domenica))){
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
        //Crea un oggetto DateFormatter per visualizzare la data nel formato specificato
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);
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