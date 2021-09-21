package com.example.ecocalendariofermo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
    }

    public void viewRegisterLayout(View v){
        CardView loginCard = (CardView) findViewById(R.id.layout_login);
        loginCard.setVisibility(View.INVISIBLE);
        CardView registerCard = (CardView) findViewById(R.id.activity_register);
        registerCard.setVisibility(View.VISIBLE);

    }
}