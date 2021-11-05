package com.example.ecocalendariofermo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.Objects;

public class RifiutarioActivity extends AppCompatActivity{

    FirebaseFirestore db;
    EditText searchEdit;
    ListView listView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rifiutario);
        getSupportActionBar().setTitle(R.string.rifiutario);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#166318")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        searchEdit = findViewById(R.id.editSearch);
        listView = findViewById(R.id.rifiutiList);
        db = FirebaseFirestore.getInstance();
        setElenco();
        search();
    }

    private void setElenco() {
        //Prende dal database tutta la raccola Rifiuti con una query
        //Ogni risultato della query viene aggiunto all'ArrayList e visualizzato nella ListView
        Query firebaseSearchQuery = db.collection("Rifiuti");
        ArrayList<Rifiuto> rifiuti = new ArrayList<>();
        firebaseSearchQuery.get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                rifiuti.add(new Rifiuto(document.getString("name"), document.getString("type"), document.getString("info")));
                            }
                            RifiutarioAdapter rifiutarioAdapter = new RifiutarioAdapter(getApplicationContext(), R.layout.list_items, rifiuti);
                            listView.setAdapter(rifiutarioAdapter);
                        } else {
                            Toast.makeText(getApplicationContext(), "Errore", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Al click visualizza le informazioni di ogni rifiuto attraverso l'activity InfoRifiuto
                Intent intent = new Intent(getApplicationContext(), InfoRifiuto.class);
                intent.putExtra("Name", rifiuti.get(position).getName());
                intent.putExtra("Type", rifiuti.get(position).getType());
                if(rifiuti.get(position).getInfo() == null){
                    intent.putExtra("Info", "null");
                } else{
                    intent.putExtra("Info", rifiuti.get(position).getInfo());
                }
                startActivity(intent);
            }
        });
    }

    public void search(){
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Prende dal database tutta la raccola Rifiuti con una query
                Query firebaseSearchQuery = db.collection("Rifiuti");
                firebaseSearchQuery.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    ArrayList<Rifiuto> datifiltrati = new ArrayList<>();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        //Se il risultato contiene il testo inserito nell'EditText, viene aggiunto all'ArrayList e visualizzato nella ListView
                                        if (Objects.requireNonNull(document.getString("name").toLowerCase()).contains(s.toString().toLowerCase())){
                                            datifiltrati.add(new Rifiuto(document.getString("name"), document.getString("type"), document.getString("info")));
                                        }
                                    }
                                    RifiutarioAdapter rifiutarioAdapter = new RifiutarioAdapter(getApplicationContext(), R.layout.list_items, datifiltrati);
                                    listView.setAdapter(rifiutarioAdapter);
                                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        @Override
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                            Intent intent = new Intent(getApplicationContext(), InfoRifiuto.class);
                                            intent.putExtra("Name", datifiltrati.get(position).getName());
                                            intent.putExtra("Type", datifiltrati.get(position).getType());
                                            if(datifiltrati.get(position).getInfo() == null){
                                                intent.putExtra("Info", "null");
                                            } else{
                                                intent.putExtra("Info", datifiltrati.get(position).getInfo());
                                            }
                                            startActivity(intent);
                                        }
                                    });
                                } else {
                                    Log.d("TAG", "Error getting documents: ", task.getException());
                                }
                            }
                        });
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(RifiutarioActivity.this, CalendarActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}