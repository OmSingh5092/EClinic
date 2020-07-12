package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.eclinic.R;
import com.example.eclinic.databinding.ActivityDoctorAppointmentBinding;

public class DoctorAppointmentActivity extends AppCompatActivity {
    ActivityDoctorAppointmentBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_appointment);
    }
}