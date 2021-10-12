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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextInputEditText usrLogin, pswLogin;
    private Button loginButton;
    private TextInputEditText usrReg, pswReg;
    private Button registerButton;
    private GoogleSignInClient mGoogleSignInClient;
    private SignInButton loginGoogleButton;
    private SignInButton signinGoogleButton;
    private static final String TAG = "GOOGLE_SIGN_IN_TAG";

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
        loginGoogleButton = (SignInButton) findViewById(R.id.bt_log_in_google);
        signinGoogleButton = (SignInButton) findViewById(R.id.bt_sign_in_google);
        mAuth = FirebaseAuth.getInstance();
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(LoginActivity.this, gso);

        loginGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: begin Google signin");
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);
            }
        });

        signinGoogleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: begin Google signin");
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);
            }
        });

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
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException){
                                Toast.makeText(getApplicationContext(), "Password errata. Prova di nuovo o accedi con Google!", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Log.d(TAG, task.getException().getMessage());
                                Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            }
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
                                Toast.makeText(getApplicationContext(), "Account già esistente", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Log.d(TAG, task.getException().getMessage());
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

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Log.d(TAG, "onActivityResult: Google sign in intent result");
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        try {
                            GoogleSignInAccount account = task.getResult(ApiException.class);
                            firebaseAuthWithGoogle(account);
                        } catch (ApiException e) {
                            Log.d(TAG, "onActivityResult: "+e.getMessage());
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "firebaseAuthWithGoogle: begin firebase auth");
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.d(TAG, "onSuccess: Logged in");
                        Toast.makeText(getApplicationContext(), "Logged in" ,Toast.LENGTH_SHORT).show();
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        String email = firebaseUser.getEmail();
                        Log.d(TAG, "onSuccess: Email: "+email);
                        if(authResult.getAdditionalUserInfo().isNewUser()){
                            Toast.makeText(getApplicationContext(), "Account creato...\n"+email ,Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Account esistente" ,Toast.LENGTH_SHORT).show();
                        }

                        Intent calendarIntent = new Intent(LoginActivity.this, CalendarActivity.class);
                        startActivity(calendarIntent);
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Loggin failed "+e.getMessage());
                        Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
    }
}