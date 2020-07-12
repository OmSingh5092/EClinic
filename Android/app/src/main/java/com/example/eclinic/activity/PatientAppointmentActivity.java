package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eclinic.R;
import com.example.eclinic.databinding.ActivityPatientAppointmentBinding;

public class PatientAppointmentActivity extends AppCompatActivity {
    ActivityPatientAppointmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientAppointmentBinding.inflate(getLayoutInflater());

        setContentView(R.layout.activity_patient_appointment);
    }
}