package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.eclinic.MainActivity;
import com.example.eclinic.R;
import com.example.eclinic.utils.SharedPrefs;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {

    SharedPrefs prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        prefs = new SharedPrefs(this);

        final FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        new Handler().postDelayed(
                new Runnable() {
                    @Override
                    public void run() {
                        if(currentUser == null){
                            Log.i("SPLASH SCREEN INTENT", "Not logged in");
                            Intent login = new Intent(SplashActivity.this, GetStartedActivity.class);
                            startActivity(login);
                            finish();
                        }else{
                            if(prefs.getUserType()){
                                Log.i("SPLASH SCREEN INTENT", "Logged in");
                                Intent main = new Intent(SplashActivity.this, PatientHomeActivity.class);
                                startActivity(main);
                                finish();
                            }else{
                                Log.i("SPLASH SCREEN INTENT", "Logged in");
                                Intent main = new Intent(SplashActivity.this, DoctorHomeActivity.class);
                                startActivity(main);
                                finish();
                            }

                        }
                    }
                },
                2000
        );
    }
}