package com.example.eclinic.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.eclinic.R;
import com.example.eclinic.databinding.ActivityDoctorRegisterBinding;
import com.example.eclinic.handlers.DoctorFormHandler;
import com.example.eclinic.handlers.FormHandler;
import com.example.eclinic.handlers.PatientFormHandler;
import com.example.eclinic.utils.PermissionHandler;
import com.yalantis.ucrop.UCrop;

public class DoctorRegisterActivity extends AppCompatActivity implements FormHandler {

    ActivityDoctorRegisterBinding binding;
    int PICK_IMAGE =100;
    DoctorFormHandler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        new PermissionHandler(this).askStoragePermissions();

        //Initializing form handling
        handler = new DoctorFormHandler(this,binding.doctorForm);
        handler.initialize();
        //Setting up toolbar
        setSupportActionBar(binding.toolbar);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE){
            handler.handleOpenImage(data.getData());
        }
        else if (requestCode == UCrop.REQUEST_CROP && resultCode == RESULT_OK) {
            handler.handleCroppedImage(UCrop.getOutput(data));
        }
    }

    @Override
    public void onFormSubmitted() {
        Intent i = new Intent(this,DoctorHomeActivity.class);
        startActivity(i);

    }
}