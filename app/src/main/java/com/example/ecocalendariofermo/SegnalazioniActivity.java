package com.example.ecocalendariofermo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SegnalazioniActivity extends AppCompatActivity {

    private EditText eSubject;
    private EditText eMsg;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segnalazioni);
        getSupportActionBar().setTitle(R.string.invia_segnalazione);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#166318")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eSubject = (EditText)findViewById(R.id.txtSub);
        eMsg = (EditText)findViewById(R.id.txtMsg);
        btn = (Button)findViewById(R.id.btnSend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });
    }

    private void sendEmail() {
        String subject = eSubject.getText().toString().trim();
        String message = eMsg.getText().toString().trim();

        if (subject.isEmpty()) {
            eSubject.setError(getString(R.string.inserire_oggetto));
            eSubject.requestFocus();
            return;
        }

        if (message.isEmpty()) {
            eMsg.setError(getString(R.string.inserire_messaggio));
            eMsg.requestFocus();
            return;
        }
        Intent it = new Intent(Intent.ACTION_SEND);
        it.putExtra(Intent.EXTRA_EMAIL, new String[]{"laura.pistagnesi@gmail.com"});
        it.putExtra(Intent.EXTRA_SUBJECT, eSubject.getText().toString());
        it.putExtra(Intent.EXTRA_TEXT, eMsg.getText());
        it.setType("message/rfc822");
        startActivity(Intent.createChooser(it,getString(R.string.scegli_app_email)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(SegnalazioniActivity.this, CalendarActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}