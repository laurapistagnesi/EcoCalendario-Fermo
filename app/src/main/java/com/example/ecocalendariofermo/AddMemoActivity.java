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
        getSupportActionBar().setTitle("Aggiungi Promemoria");
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
                titolo.setError("Titolo Richiesto");
                titolo.requestFocus();
                return;
            }

            int hour, minute;
            hour = picker.getHour();
            minute = picker.getMinute();
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(titoloPromemoria, titoloMemo);
            editor.apply();

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
                        .setTitle("Disattivare ottimizzazione")
                        .setMessage("Per consentire l'invio di notifiche, occorre disattivare l'ottimizzazione della batteria!")
                        .setPositiveButton("SI", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    Intent intent = new Intent();
                                    String packageName = getPackageName();
                                    intent.setAction(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS);
                                    intent.setData(Uri.parse("package:" + packageName));
                                    startActivity(intent);
                                } catch (ActivityNotFoundException e) {
                                    Toast.makeText(getApplicationContext(), "Unable to open battery optimisation screen. Please add it manually", Toast.LENGTH_LONG).show();
                                }
                            }
                        })
                        .setNegativeButton("NO", null)
                        .show();
            }
        }
    }

    // Setup a recurring alarm every half hour
    public void scheduleAlarm(Calendar calendar, int requestCode) {
        String titoloMemo = String.valueOf(titolo.getText());
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        intent.putExtra("titoloMemo", titoloMemo);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, requestCode,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        // Setup periodic alarm every every half hour from this point onwards
        long firstMillis = calendar.getTimeInMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        alarm.setInexactRepeating(AlarmManager.RTC_WAKEUP, firstMillis,
                AlarmManager.INTERVAL_DAY * 7, pIntent);
        Intent i = new Intent(this, AlarmService.class);
        i.putExtra("foo", "bar");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            this.startForegroundService(i);
        }
        else {
            this.startService(i);
        }
        Toast.makeText(this, "Promemoria Impostato", Toast.LENGTH_SHORT).show();
    }
}
