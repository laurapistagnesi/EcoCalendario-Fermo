package com.example.ecocalendariofermo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CalendarActivity extends AppCompatActivity implements CalendarAdapter.OnItemListener {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private GoogleSignInClient mGoogleSignInClient;
    private TextView monthYearText;
    private RecyclerView calendarRecyclerView;
    public static LocalDate selectedDate;
    private FloatingActionButton fab;
    private FloatingActionButton fab1;
    private FloatingActionButton fab2;
    private TextView textViewfab2;
    private TextView textViewfab1;
    DrawerLayout drawerLayout;
    private boolean isFABOpen = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);
        getSupportActionBar().setTitle("Eco Calendario");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#166318")));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_baseline_format_list_bulleted_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        calendarRecyclerView = findViewById(R.id.calendar_recycler_view);
        monthYearText = findViewById(R.id.month_year_TV);
        selectedDate = LocalDate.now();
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        textViewfab2 = (TextView) findViewById(R.id.text_fab2);
        textViewfab1 = (TextView) findViewById(R.id.text_fab1);
        drawerLayout = findViewById(R.id.drawer_layout);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("1019300904917-ltmcmij1g8dgplaeje9sj0gkg612hjlm.apps.googleusercontent.com")
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setMonthView()
    {
        //Imposta la visualizzazione mensile del calendario a partire dall'attuale data
        monthYearText.setText(monthYearFromDate(selectedDate).substring(0, 1).toUpperCase() + monthYearFromDate(selectedDate).substring(1));
        ArrayList<LocalDate> daysInMonth = daysInMonthArray(selectedDate);
        CalendarAdapter calendarAdapter = new CalendarAdapter(daysInMonth, this);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 7);
        calendarRecyclerView.setLayoutManager(layoutManager);
        calendarRecyclerView.setAdapter(calendarAdapter);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private ArrayList<LocalDate> daysInMonthArray(LocalDate date)
    {
        //Restituisce i giorni del mese a partire dalla data selezionata
        ArrayList<LocalDate> daysInMonthArray = new ArrayList<>();
        YearMonth yearMonth = YearMonth.from(date);
        int daysInMonth = yearMonth.lengthOfMonth();
        LocalDate firstOfMonth = selectedDate.withDayOfMonth(1);
        int dayOfWeek = firstOfMonth.getDayOfWeek().getValue();
        for (int i=2; i<=42; i++)
        {
            if(i <= dayOfWeek || i > daysInMonth + dayOfWeek)
            {
                daysInMonthArray.add(null);
            }
            else
            {
                daysInMonthArray.add(LocalDate.of(selectedDate.getYear(), selectedDate.getMonth(), i - dayOfWeek));
            }
        }
        return daysInMonthArray;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String monthYearFromDate(LocalDate date)
    {
        //Restituisce il mese e l'anno in base alla data selezionata
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        return date.format(formatter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.actionbar_calendar, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout_button:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Logout");
                builder.setMessage(R.string.conferma_uscita);
                builder.setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth.signOut();
                        mGoogleSignInClient.signOut();
                        Intent calendarIntent = new Intent(CalendarActivity.this, LoginActivity.class);
                        startActivity(calendarIntent);
                        finish();
                    }
                });
                builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            case android.R.id.home:
                openDrawer(drawerLayout);
                return true;
            case R.id.info_button:
                showAdapterAlertDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void previousMonthAction(View v)
    {
        selectedDate = selectedDate.minusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void nextMonthAction(View v)
    {
        selectedDate = selectedDate.plusMonths(1);
        setMonthView();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onItemClick(int position, LocalDate date)
    {
        if(date!=null)
        {
            selectedDate = date;
            setMonthView();
        }

    }

    private void showAdapterAlertDialog()
    {
        //Ogni immagine nell'array verrà visualizzata all'inizio di ogni elemento
        int[] imageIdArr = {R.drawable.ic_indifferenziato, R.drawable.ic_plastic, R.drawable.ic_carta, R.drawable.ic_baseline_warning_24};
        //Testo di ogni elemento
        String[] listItemArr = {getString(R.string.indifferenziata), getString(R.string.plastica), getString(R.string.carta), getString(R.string.giorni_festivi)};

        //key di immagine e testo
        final String CUSTOM_ADAPTER_IMAGE = "image";
        final String CUSTOM_ADAPTER_TEXT = "text";

        // Crea un alert dialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(CalendarActivity.this);
        builder.setIcon(R.drawable.ic_logo);
        builder.setTitle("Info");

        //Crea lista con elementi
        List<Map<String, Object>> dialogItemList = new ArrayList<Map<String, Object>>();
        int listItemLen = listItemArr.length;
        for(int i=0;i<listItemLen;i++)
        {
            Map<String, Object> itemMap = new HashMap<String, Object>();
            itemMap.put(CUSTOM_ADAPTER_IMAGE, imageIdArr[i]);
            itemMap.put(CUSTOM_ADAPTER_TEXT, listItemArr[i]);

            dialogItemList.add(itemMap);
        }

        SimpleAdapter simpleAdapter = new SimpleAdapter(CalendarActivity.this, dialogItemList,
                R.layout.alert_dialog_info,
                new String[]{CUSTOM_ADAPTER_IMAGE, CUSTOM_ADAPTER_TEXT},
                new int[]{R.id.alertDialogItemImageView,R.id.alertDialogItemTextView});

        builder.setAdapter(simpleAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int itemIndex) {
            }
        });

        builder.setCancelable(true);
        builder.create();
        builder.show();

    }

    private void showFABMenu(){
        //Apre il menu Floating Action Button
        isFABOpen=true;
        fab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        textViewfab2.setVisibility(View.VISIBLE);
        textViewfab2.animate().translationY(-getResources().getDimension(R.dimen.standard_105));
        textViewfab1.setVisibility(View.VISIBLE);
        textViewfab1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
    }

    private void closeFABMenu(){
        //Chiude il menu Floating Action Button
        isFABOpen=false;
        fab1.animate().translationY(0);
        fab2.animate().translationY(0);
        textViewfab2.setVisibility(View.INVISIBLE);
        textViewfab1.setVisibility(View.INVISIBLE);
    }

    private static void openDrawer (DrawerLayout drawerLayout){
        //Apre il menu laterale
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    private static void closeDrawer (DrawerLayout drawerLayout){
        //Chiude il menu laterale
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        closeDrawer(drawerLayout);
    }

    public void goAddMemo(View view){
        Intent intent = new Intent(CalendarActivity.this, AddMemoActivity.class);
        startActivity(intent);
        finish();
    }

    public void goDeleteMemo(View view){
        Intent intent = new Intent(CalendarActivity.this, DeleteMemoActivity.class);
        startActivity(intent);
        finish();
    }

    public void goRifiutario(View view){
        Intent intent = new Intent(CalendarActivity.this, RifiutarioActivity.class);
        startActivity(intent);
        finish();
    }

    public void goMap(View v){
        Intent intent = new Intent(CalendarActivity.this, MappaActivity.class);
        startActivity(intent);
        finish();
    }

    public void goInfo(View v){
        Intent intent = new Intent(CalendarActivity.this, InfoActivity.class);
        startActivity(intent);
        finish();
    }

    public void goSegnalazioni(View v){
        Intent intent = new Intent(CalendarActivity.this, SegnalazioniActivity.class);
        startActivity(intent);
        finish();
    }
}