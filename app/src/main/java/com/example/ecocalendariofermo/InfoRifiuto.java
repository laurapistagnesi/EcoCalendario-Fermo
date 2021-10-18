package com.example.ecocalendariofermo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_rifiuto);
        getSupportActionBar().setTitle("Informazioni");
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#166318")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name = findViewById(R.id.nome);
        type = findViewById(R.id.tipo);
        info = findViewById(R.id.info);
        layInfo = findViewById(R.id.cardInfo);
        icon = findViewById(R.id.logo_info);
        getInfo();
    }

    private void getInfo() {
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
                break;
            case "Raccolta abiti HUMANA o Indifferenziata":
                icon.setImageResource(R.drawable.tshirt);
                break;
            case "Rivenditori o Ecocentro comunale":
                icon.setImageResource(R.drawable.ic_recycle);
                break;
            case "Ecocentro comunale":
                icon.setImageResource(R.drawable.ic_recycle);
                break;
            case "Vetro":
                icon.setImageResource(R.drawable.ic_vetro);
                break;
            case "Organico":
                icon.setImageResource(R.drawable.ic_organico);
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