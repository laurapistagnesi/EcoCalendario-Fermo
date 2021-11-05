package com.example.ecocalendariofermo;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText usrLogin, pswLogin;
    private Button loginButton;
    private TextInputEditText usrReg, pswReg, pswReg2;
    private Button registerButton;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton loginGoogleButton;
    private SignInButton signinGoogleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        usrLogin = findViewById(R.id.editTextEmailLog);
        pswLogin = findViewById(R.id.editTextPasswordLog);
        loginButton = findViewById(R.id.login_button);
        usrReg = findViewById(R.id.editTextEmailReg);
        pswReg = findViewById(R.id.editTextPasswordReg);
        pswReg2 = findViewById(R.id.editTextPasswordReg2);
        registerButton = findViewById(R.id.register_button);
        loginGoogleButton = findViewById(R.id.bt_log_in_google);
        signinGoogleButton = findViewById(R.id.bt_sign_in_google);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN) //opzioni per il login tramite Google
                .requestIdToken("1019300904917-ltmcmij1g8dgplaeje9sj0gkg612hjlm.apps.googleusercontent.com") //è richiesto un token ID ed è definito l’ID client del server che verificherà l’integrità del token
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

        //Il button di login tramite Google avvia l'intent di login
        loginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);
            }
        });

        //Nella card di registrazione la funzione del button è la stessa di quella nella card di login
        signinGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);
            }
        });

        //Login tramite email e password
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = usrLogin.getText().toString().trim();
                final String password = pswLogin.getText().toString().trim();

                if (email.isEmpty()) {
                    usrLogin.setError(getString(R.string.email_richiesta));
                    usrLogin.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    usrLogin.setError(getString(R.string.email_non_valida));
                    usrLogin.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    pswLogin.setError(getString(R.string.psw_richiesta));
                    pswLogin.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    pswLogin.setError(getString(R.string.psw_non_valida));
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
                            Toast.makeText(getApplicationContext(), R.string.login_effettuato, Toast.LENGTH_SHORT).show();
                        } else {
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(), R.string.psw_errata, Toast.LENGTH_SHORT).show();
                            }
                            if(task.getException() instanceof FirebaseAuthInvalidUserException){
                                Toast.makeText(getApplicationContext(), R.string.errore_utente, Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), R.string.psw_errata,Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });

        //Registrazione con email e password
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = usrReg.getText().toString();
                String password = pswReg.getText().toString();
                final String password2 = pswReg2.getText().toString().trim();

                if (email.isEmpty()) {
                    usrReg.setError(getString(R.string.email_richiesta));
                    usrReg.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    usrReg.setError(getString(R.string.email_non_valida));
                    usrReg.requestFocus();
                    return;
                }

                if (password.isEmpty()) {
                    pswReg.setError(getString(R.string.psw_richiesta));
                    pswReg.requestFocus();
                    return;
                }

                if (password.length() < 6) {
                    pswReg.setError(getString(R.string.caratteri_minimi));
                    pswReg.requestFocus();
                    return;
                }

                if (!password.equals(password2) || password2.isEmpty()) {
                    pswReg2.setError(getString(R.string.non_corrisponde));
                    pswReg2.requestFocus();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            startActivity(new Intent(LoginActivity.this, CalendarActivity.class));
                            Toast.makeText(getApplicationContext(), R.string.reg_log_effettuati, Toast.LENGTH_SHORT).show();
                        }else{
                            if(task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), R.string.email_in_uso, Toast.LENGTH_SHORT).show();
                            }
                            else {
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

    //Mostra la card di registrazione
    public void viewRegisterLayout(View v){
        CardView loginCard = (CardView) findViewById(R.id.layout_login);
        loginCard.setVisibility(View.INVISIBLE);
        CardView registerCard = (CardView) findViewById(R.id.activity_register);
        registerCard.setVisibility(View.VISIBLE);
    }

    //Mostra la card di login
    public void viewLoginLayout(View v){
        CardView registerCard = (CardView) findViewById(R.id.activity_register);
        registerCard.setVisibility(View.INVISIBLE);
        CardView loginCard = (CardView) findViewById(R.id.layout_login);
        loginCard.setVisibility(View.VISIBLE);
    }

    //Inoltra al metodo firebaseAuthWithGoogle l’account ottenuto durante il processo di login avviato tramite l’Intent
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            firebaseAuthWithGoogle(account);
                        } catch (ApiException e) {
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null); //si ottiene le credenziali attraverso il metodo statico getCredential dell’oggetto GoogleAuthProvider
        // che prende in input l’id del token (ottenuto durante l’autenticazione e che deve essere verificato dal server)
        // e restituisce un oggetto AuthCredential
        mAuth.signInWithCredential(credential) //accesso a Google tramite il metodo signInWithCredential passando in input le credenziali appena recuperate
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        if(authResult.getAdditionalUserInfo().isNewUser()){
                            Toast.makeText(getApplicationContext(), getString(R.string.reg_log_effettuati), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), getString(R.string.login_effettuato), Toast.LENGTH_SHORT).show();
                        }

                        Intent calendarIntent = new Intent(LoginActivity.this, CalendarActivity.class);
                        startActivity(calendarIntent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}