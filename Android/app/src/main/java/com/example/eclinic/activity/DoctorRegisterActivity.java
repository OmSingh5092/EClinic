package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eclinic.R;
import com.example.eclinic.databinding.ActivityDoctorRegisterBinding;

public class DoctorRegisterActivity extends AppCompatActivity {

    ActivityDoctorRegisterBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}