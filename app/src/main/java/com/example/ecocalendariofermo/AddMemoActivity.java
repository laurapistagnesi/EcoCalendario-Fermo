package com.example.ecocalendariofermo;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Calendar;


public class AddMemoActivity extends AppCompatActivity {

    TimePicker picker;
    ToggleButton lun, mar, mer, gio, ven, sab, dom;
    public static TextInputEditText titolo;
    public static final String titoloPromemoria = "Titolo promemoria";
    private SharedPreferences sharedPreferences;
    public static final String MyPREFERENCES = "Memo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);
        getSupportActionBar().setTitle(R.string.aggiungi_memo);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#166318")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titolo = findViewById(R.id.editTextTitolo);
        picker = findViewById(R.id.timePicker);
        picker.setIs24HourView(true);
        lun = findViewById(R.id.lun);
        mar = findViewById(R.id.mar);
        mer = findViewById(R.id.mer);
        gio = findViewById(R.id.gio);
        ven = findViewById(R.id.ven);
        sab = findViewById(R.id.sab);
        dom = findViewById(R.id.dom);
        checkAndAskForBatteryOptimization();
        sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(AddMemoActivity.this, CalendarActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void salvaMemo(View view) {
        String titoloMemo = String.valueOf(titolo.getText());
        if (titoloMemo.isEmpty()) {
            titolo.setError(getString(R.string.titolo_richiesto));
            titolo.requestFocus();
            return;
        }

        //Prende i giorni e l'ora selezionata
        int hour, minute;
        hour = picker.getHour();
        minute = picker.getMinute();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(titoloPromemoria, titoloMemo);
        editor.apply();

        //Per ogni giorno e ora selezionati attiva una notifica periodica
        //Il requestCode è diverso per ogni giorno in modo da consentire l'eliminazione di uno specifico promemoria
        if (lun.isChecked()) {
            Calendar calMonday = Calendar.getInstance();
            calMonday.set(Calendar.HOUR_OF_DAY, hour);
            calMonday.set(Calendar.MINUTE, minute);
            calMonday.set(Calendar.SECOND, 0);
            calMonday.set(Calendar.MILLISECOND, 0);
            calMonday.set(Calendar.DAY_OF_WEEK, 2);
            editor.putLong("Sveglia lunedi", calMonday.getTimeInMillis());
            editor.apply();
            scheduleAlarm(calMonday, 1);
        }
        if (mar.isChecked()) {
            Calendar calTuesday = Calendar.getInstance();
            calTuesday.set(Calendar.HOUR_OF_DAY, hour);
            calTuesday.set(Calendar.MINUTE, minute);
            calTuesday.set(Calendar.SECOND, 0);
            calTuesday.set(Calendar.MILLISECOND, 0);
            calTuesday.set(Calendar.DAY_OF_WEEK, 3);
            editor.putLong("Sveglia martedi", calTuesday.getTimeInMillis());
            editor.apply();
            scheduleAlarm(calTuesday, 2);
        }
        if (mer.isChecked()) {
            Calendar calWednestay = Calendar.getInstance();
            calWednestay.set(Calendar.HOUR_OF_DAY, hour);
            calWednestay.set(Calendar.MINUTE, minute);
            calWednestay.set(Calendar.SECOND, 0);
            calWednestay.set(Calendar.MILLISECOND, 0);
            calWednestay.set(Calendar.DAY_OF_WEEK, 4);
            editor.putLong("Sveglia mercoledi", calWednestay.getTimeInMillis());
            editor.apply();
            scheduleAlarm(calWednestay, 3);
        }
        if (gio.isChecked()) {
            Calendar calThursday = Calendar.getInstance();
            calThursday.set(Calendar.HOUR_OF_DAY, hour);
            calThursday.set(Calendar.MINUTE, minute);
            calThursday.set(Calendar.SECOND, 0);
            calThursday.set(Calendar.MILLISECOND, 0);
            calThursday.set(Calendar.DAY_OF_WEEK, 5);
            editor.putLong("Sveglia giovedi", calThursday.getTimeInMillis());
            editor.apply();
            scheduleAlarm(calThursday, 4);
        }
        if (ven.isChecked()) {
            Calendar calFriday = Calendar.getInstance();
            calFriday.set(Calendar.HOUR_OF_DAY, hour);
            calFriday.set(Calendar.MINUTE, minute);
            calFriday.set(Calendar.SECOND, 0);
            calFriday.set(Calendar.MILLISECOND, 0);
            calFriday.set(Calendar.DAY_OF_WEEK, 6);
            editor.putLong("Sveglia venerdi", calFriday.getTimeInMillis());
            editor.apply();
            scheduleAlarm(calFriday, 5);
        }
        if (sab.isChecked()) {
            Calendar calSaturday = Calendar.getInstance();
            calSaturday.set(Calendar.HOUR_OF_DAY, hour);
            calSaturday.set(Calendar.MINUTE, minute);
            calSaturday.set(Calendar.SECOND, 0);
            calSaturday.set(Calendar.MILLISECOND, 0);
            calSaturday.set(Calendar.DAY_OF_WEEK, 7);
            editor.putLong("Sveglia sabato", calSaturday.getTimeInMillis());
            editor.apply();
            scheduleAlarm(calSaturday,6);
        }
        if (dom.isChecked()) {
            Calendar calSunday = Calendar.getInstance();
            calSunday.set(Calendar.HOUR_OF_DAY, hour);
            calSunday.set(Calendar.MINUTE, minute);
            calSunday.set(Calendar.SECOND, 0);
            calSunday.set(Calendar.MILLISECOND, 0);
            calSunday.set(Calendar.DAY_OF_WEEK, 1);
            editor.putLong("Sveglia domenica", calSunday.getTimeInMillis());
            editor.apply();
            scheduleAlarm(calSunday, 7);
        }
    }

    private void checkAndAskForBatteryOptimization() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PowerManager powerManager = (PowerManager) getApplicationContext().getSystemService(POWER_SERVICE);
            if (!powerManager.isIgnoringBatteryOptimizations(getPackageName())) {
                new AlertDialog.Builder(this)
                        .setCancelable(false)
                        .setTitle(R.string.disattiva_ottimizzazione)
                        .setMessage(R.string.messaggio_ottimizzazione)
                        .setPositiveButton(getString(R.string.si), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Intent intent = new Intent();
                                    String packageName = getPackageName();
                                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                                    intent.setData(Uri.parse("package:" + packageName));
                                    startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getApplicationContext(), R.string.errore_ottimizzazione, Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton(getString(R.string.no), null)
                        .show();
            }
        }
    }

    // Imposta una notifica ogni sette giorni
    public void scheduleAlarm(Calendar calendar, int requestCode) {
        String titoloMemo = String.valueOf(titolo.getText());
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("titoloMemo", titoloMemo);
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, requestCode,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Imposta una notifica periodica a partire dal millisecondo 'firstmillis' selezionato
        long firstMillis = calendar.getTimeInMillis();
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_DAY*7, pIntent);
        Intent i = new Intent(this, AlarmService.class);
        i.putExtra("foo", "bar");
        //Inizia il service in background
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(i);
        }
        else {
            this.startService(i);
        }
        Toast.makeText(this, getString(R.string.memo_impostato), Toast.LENGTH_SHORT).show();
        Intent hintent = new Intent(AddMemoActivity.this, CalendarActivity.class);
        hintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(hintent);
        finish();
    }
}
