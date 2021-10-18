package com.example.ecocalendariofermo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Collection;

public class RifiutarioActivity extends AppCompatActivity{

    FirebaseFirestore db;
    private RecyclerView recyclerView;
    FirestoreRecyclerAdapter adapter;
    EditText searchEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rifiutario);
        getSupportActionBar().setTitle("Rifiutario");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#166318")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        searchEdit = findViewById(R.id.editSearch);

        recyclerView = findViewById(R.id.rifiutario_recycler);
        db = FirebaseFirestore.getInstance();
        Query query1 = db.collection("Rifiuti");
        setElenco(query1);
        search();
    }

    private void setElenco(Query query) {
        FirestoreRecyclerOptions<Rifiuto> options = new FirestoreRecyclerOptions.Builder<Rifiuto>()
                .setQuery(query, Rifiuto.class)
                .build();
        Log.e("TAG", String.valueOf(query));
        adapter = new FirestoreRecyclerAdapter<Rifiuto, RifiutarioViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RifiutarioViewHolder viewHolder, int i, @NonNull Rifiuto rifiuto) {
                viewHolder.nomeRifiuto.setText(rifiuto.getName());
                viewHolder.nomeRifiuto.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), InfoRifiuto.class);
                        intent.putExtra("Name", rifiuto.getName());
                        intent.putExtra("Type", rifiuto.getType());
                        if(rifiuto.getInfo() == null){
                            intent.putExtra("Info", "null");
                        } else{
                            intent.putExtra("Info", rifiuto.getInfo());
                        }
                        startActivity(intent);
                    }
                });
            }

            @NonNull
            @Override
            public RifiutarioViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_items, parent,false);
                return new RifiutarioViewHolder(view);
            }
        };

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setNestedScrollingEnabled(false);
    }

    public void search(){
        searchEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s != null && s.length()>0){
                    char[] letters=s.toString().toCharArray();
                    String firstLetter = String.valueOf(letters[0]).toUpperCase();
                    String remainingLetters = s.toString().substring(1).toLowerCase();
                    s=firstLetter+remainingLetters;
                }

                Query firebaseSearchQuery = db.collection("Rifiuti").orderBy("name").startAt(s.toString())
                        .endAt(s.toString() + "uf8ff");
                firebaseSearchQuery.get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    setElenco(firebaseSearchQuery);
                                    adapter.startListening();
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("TAG", document.getId() + " => " + document.getData());
                                    }
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

    @Override
    protected void onStop(){
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart(){
        super.onStart();
        adapter.startListening();
    }
}