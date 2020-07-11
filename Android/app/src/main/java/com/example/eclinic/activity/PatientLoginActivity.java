package com.example.eclinic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eclinic.MainActivity;
import com.example.eclinic.R;
import com.example.eclinic.apiModel.TokenResponseModel;
import com.example.eclinic.databinding.ActivityPatientLoginBinding;
import com.example.eclinic.retrofit.RetrofitClient;
import com.example.eclinic.utils.SharedPrefs;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.HashMap;
import java.util.Map;

public class PatientLoginActivity extends AppCompatActivity implements  GoogleApiClient.OnConnectionFailedListener {

    ActivityPatientLoginBinding binding;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser user;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth auth;
    private SharedPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_login);

        binding = ActivityPatientLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        prefs = new SharedPrefs(this);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(user != null) Log.i("AUTH", "Logged In");
                else Log.i("AUTH", "NOT Logged In");
            }
        };

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))//you can also use R.string.default_web_client_id
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        binding.googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent, GOOGLE_SIGN_IN_REQUEST_CODE);
            }
        });
    }



    private static final int GOOGLE_SIGN_IN_REQUEST_CODE = 707;




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == GOOGLE_SIGN_IN_REQUEST_CODE){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        if(result.isSuccess()){
            GoogleSignInAccount account = result.getSignInAccount();
            Log.i("Google OAuth", account.getIdToken());
            // you can store user data to SharedPreference
            Map<String,String>body = new HashMap<>();
            body.put("idToken",account.getIdToken());
            Call<TokenResponseModel> call = RetrofitClient.getClient().getPatientToken(body);
            call.enqueue(new Callback<TokenResponseModel>() {
                @Override
                public void onResponse(Call<TokenResponseModel> call, Response<TokenResponseModel> response) {
                    if(response.isSuccessful() && response.code() == 200){
                        TokenResponseModel tokenModel = response.body();
                        if(tokenModel.isSuccess()) {
                            prefs.saveEmail(account.getEmail());
                            prefs.saveName(account.getDisplayName());
                            prefs.saveToken(tokenModel.getAuthToken());
                            prefs.saveNewUser(tokenModel.isNewUser());
                            AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
                            firebaseAuthWithGoogle(credential);
                        }else{
                            Toast.makeText(PatientLoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onFailure(Call<TokenResponseModel> call, Throwable t) {
                    Toast.makeText(PatientLoginActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                    Log.e("Login", t.getMessage());
                    t.printStackTrace();
                }
            });

        }else{
            // Google Sign In failed, update UI appropriately
            Log.e("LOGIN ATTEMPT", "Login Unsuccessful. "+result.getStatus());
            Log.e("LOGIN ATTEMPT", "Login Unsuccessful. "+result.getSignInAccount());
            Log.e("LOGIN ATTEMPT", "Login Unsuccessful. "+result.toString());

            Toast.makeText(this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(AuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d("Login Attempt", "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(task.isSuccessful()){

                            Toast.makeText(PatientLoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                            if(prefs.getNewUser()){
                                goToOnboarding();
                            }else {
                                gotoHome();
                            }
                        }else{
                            Log.w("Login Attempt", "signInWithCredential" + task.getException().getMessage());
                            task.getException().printStackTrace();
                            Toast.makeText(PatientLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    private void gotoHome() {
        Intent intent = new Intent(PatientLoginActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void goToOnboarding() {
        Intent intent = new Intent(PatientLoginActivity.this, PatientRegisterActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (authStateListener != null){
            FirebaseAuth.getInstance().signOut();
        }
        auth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (authStateListener != null){
            auth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
    }
}