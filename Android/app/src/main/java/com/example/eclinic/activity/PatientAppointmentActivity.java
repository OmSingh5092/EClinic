package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eclinic.R;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityPatientAppointmentBinding;
import com.example.eclinic.utils.DateFormatter;
import com.example.eclinic.utils.ImageDownloader;

import java.text.ParseException;

public class PatientAppointmentActivity extends AppCompatActivity {
    ActivityPatientAppointmentBinding binding;

    int appointmentIndex;
    Appointment appointment;
    Doctor doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        appointmentIndex = getIntent().getIntExtra("appointmentIndex",0);
        appointment = GeneralData.getAppointments().get(appointmentIndex);

        doctor = findDoctor(appointment.getDoctorId());

        setFields();
    }

    void setFields(){

        if(appointment.isPaymentStatus()){
            binding.pay.setVisibility(View.GONE);
        }

        binding.name.setText(doctor.getDoctorName());
        binding.category.setText(doctor.getCategory());

        new ImageDownloader(binding.image,doctor.getPhotoPath(),this);

        binding.description.setText(appointment.getText());
        binding.interaction.setText(appointment.getInteractionMethod());
        try {
            binding.time.setText(new DateFormatter(appointment.getDate()).getFullDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    void seePdf(){

    }

    Doctor findDoctor(int id){
        for(Doctor doctor:GeneralData.getDoctors()){
            if(doctor.getDoctorId() == id){
                return doctor;
            }
        }

        return null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}