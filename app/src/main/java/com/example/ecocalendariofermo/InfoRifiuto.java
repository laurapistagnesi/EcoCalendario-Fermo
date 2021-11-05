package com.example.ecocalendariofermo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class InfoRifiuto extends AppCompatActivity {
    TextView name;
    TextView type;
    TextView info;
    RelativeLayout layInfo;
    ImageView icon;
    TextView map;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_rifiuto);
        getSupportActionBar().setTitle(R.string.informazioni);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#166318")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = findViewById(R.id.nome);
        type = findViewById(R.id.tipo);
        info = findViewById(R.id.info);
        layInfo = findViewById(R.id.cardInfo);
        icon = findViewById(R.id.logo_info);
        map = findViewById(R.id.to_map);
        getInfo();
    }

    private void getInfo() {
        //In base al rifiuto selezionato nel Rifiutario prende le informazioni dall'intent e le mostra
        name.setText(getIntent().getStringExtra("Name"));
        type.setText(getIntent().getStringExtra("Type"));
        if(getIntent().getStringExtra("Info").equals("null")){
            layInfo.setVisibility(View.INVISIBLE);
        }else {
            info.setText(getIntent().getStringExtra("Info"));
        }
        switch (getIntent().getStringExtra("Type")){
            case "Carta e Cartone":
                icon.setImageResource(R.drawable.ic_carta_b);
                break;
            case "Indifferenziata":
                icon.setImageResource(R.drawable.ic_indif_b);
                break;
            case "Plastica":
                icon.setImageResource(R.drawable.ic_plastic_b);
                break;
            case "Alluminio":
                icon.setImageResource(R.drawable.ic_alluminio);
                map.setText("Clicca per trovare l'isola ecologica piu' vicina");
                map.setPaintFlags(map.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(InfoRifiuto.this, MappaActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                break;
            case "Raccolta abiti HUMANA o Indifferenziata":
                icon.setImageResource(R.drawable.tshirt);
                map.setText("Clicca per trovare la raccolta abiti HUMANA piu' vicina");
                map.setPaintFlags(map.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(InfoRifiuto.this, MappaActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                break;
            case "Rivenditori o Ecocentro comunale":
            case "Ecocentro comunale":
                icon.setImageResource(R.drawable.ic_recycle);
                map.setText("Clicca per trovare l'Ecocentro piu' vicino");
                map.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);
                map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(InfoRifiuto.this, MappaActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                break;
            case "Vetro":
                icon.setImageResource(R.drawable.ic_vetro);
                map.setText("Clicca per trovare l'isola ecologica piu' vicina");
                map.setPaintFlags(map.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(InfoRifiuto.this, MappaActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                break;
            case "Organico":
                icon.setImageResource(R.drawable.ic_organico);
                map.setText("Clicca per trovare l'isola ecologica piu' vicina");
                map.setPaintFlags(map.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
                map.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i = new Intent(InfoRifiuto.this, MappaActivity.class);
                        startActivity(i);
                        finish();
                    }
                });
                break;
            default: icon.setImageResource(R.drawable.ic_logo);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(InfoRifiuto.this, RifiutarioActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}