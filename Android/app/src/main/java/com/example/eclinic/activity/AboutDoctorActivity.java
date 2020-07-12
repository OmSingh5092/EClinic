package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.eclinic.R;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityAboutDoctorBinding;
import com.example.eclinic.utils.ImageDownloader;
import com.example.eclinic.utils.SharedPrefs;

public class AboutDoctorActivity extends AppCompatActivity {
    ActivityAboutDoctorBinding binding;

    Doctor doctor;
    int doctorIndex;

    SharedPrefs prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding =ActivityAboutDoctorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        doctorIndex = getIntent().getIntExtra("doctorIndex",0);
        doctor = GeneralData.getDoctors().get(doctorIndex);

        initializeText();
        new ImageDownloader(binding.image,doctor.getPhotoPath(),this);

        prefs = new SharedPrefs(this);

        binding.book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AboutDoctorActivity.this,BookAppointmentActivity.class);
                i.putExtra("doctorIndex",doctorIndex);
                startActivity(i);
            }
        });
    }

    void initializeText(){
        binding.name.setText(doctor.getDoctorName());
        binding.category.setText(doctor.getCategory()+"Specialist");

        binding.about.setText(doctor.getAbout());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}