package com.example.eclinic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.eclinic.R;
import com.example.eclinic.databinding.ActivityDoctorHomeBinding;
import com.example.eclinic.databinding.ActivityDoctorRegisterBinding;
import com.example.eclinic.utils.SharedPrefs;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class DoctorHomeActivity extends AppCompatActivity {

    ActivityDoctorHomeBinding binding;
    SharedPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        prefs = new SharedPrefs(this);
        handleDrawerMenu();
    }

    void handleDrawerMenu(){
        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.logout){
                    prefs.clearData();
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(DoctorHomeActivity.this,GetStartedActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        binding.getRoot().openDrawer(Gravity.LEFT);
        return super.onSupportNavigateUp();
    }
}