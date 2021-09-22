package com.example.ecocalendariofermo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText usrLogin, pswLogin;
    private Button loginButton;
    private TextInputEditText usrReg, pswReg;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        usrLogin = (TextInputEditText) findViewById(R.id.editTextEmailLog);
        pswLogin = (TextInputEditText) findViewById(R.id.editTextPasswordLog);
        loginButton = (Button) findViewById(R.id.login_button);
        usrReg = (TextInputEditText) findViewById(R.id.editTextEmailReg);
        pswReg = (TextInputEditText) findViewById(R.id.editTextPasswordReg);
        registerButton = (Button) findViewById(R.id.register_button);
        mAuth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = usrLogin.getText().toString().trim();
                final String password = pswLogin.getText().toString().trim();

                if (email.isEmpty()) {
                    usrLogin.setError("Email Richiesta");
                    usrLogin.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    usrLogin.setError("Email non valida");
                    usrLogin.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    pswLogin.setError("Password Richiesta");
                    pswLogin.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    pswLogin.setError("Password non valida");
                    pswLogin.requestFocus();
                    return;
                }

                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(LoginActivity.this, CalendarActivity.class);
                            finish();
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = usrReg.getText().toString();
                String password = pswReg.getText().toString();

                //controllo se viene inserita un email e login nel caso che l'email vada bene
                if (email.isEmpty()) {
                    usrReg.setError("Email Richiesta");
                    usrReg.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    usrReg.setError("Email non valida");
                    usrReg.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    pswReg.setError("Password richiesta");
                    pswReg.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    pswReg.setError("Minimo 6 caratteri");
                    pswReg.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();;
                            startActivity(new Intent(LoginActivity.this, CalendarActivity.class));
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "Account già esistente",Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Controlla se lo user è già collegato
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent calendarIntent = new Intent(LoginActivity.this, CalendarActivity.class);
            startActivity(calendarIntent);
            finish();
        }
    }

    public void viewRegisterLayout(View v){
        CardView loginCard = (CardView) findViewById(R.id.layout_login);
        loginCard.setVisibility(View.INVISIBLE);
        CardView registerCard = (CardView) findViewById(R.id.activity_register);
        registerCard.setVisibility(View.VISIBLE);
    }

    public void viewLoginLayout(View v){
        CardView registerCard = (CardView) findViewById(R.id.activity_register);
        registerCard.setVisibility(View.INVISIBLE);
        CardView loginCard = (CardView) findViewById(R.id.layout_login);
        loginCard.setVisibility(View.VISIBLE);
    }
}