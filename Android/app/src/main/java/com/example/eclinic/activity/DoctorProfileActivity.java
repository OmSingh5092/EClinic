package com.example.eclinic.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;

import com.example.eclinic.R;
import com.example.eclinic.apiControllers.DoctorProfileController;
import com.example.eclinic.apiControllers.PatientProfileController;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.apiModel.DoctorGetResponseModel;
import com.example.eclinic.apiModel.Patient;
import com.example.eclinic.apiModel.PatientGetResponseModel;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityDoctorProfileBinding;
import com.example.eclinic.databinding.ActivityPatientProfileBinding;
import com.example.eclinic.handlers.DoctorFormHandler;
import com.example.eclinic.handlers.FormHandler;
import com.example.eclinic.handlers.PatientFormHandler;
import com.example.eclinic.utils.ImageDownloader;
import com.example.eclinic.utils.PermissionHandler;
import com.example.eclinic.utils.SharedPrefs;
import com.yalantis.ucrop.UCrop;

public class DoctorProfileActivity extends AppCompatActivity implements FormHandler {

    ActivityDoctorProfileBinding binding;
    SharedPrefs prefs;
    Doctor doctor;
    DoctorFormHandler handler;

    int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        prefs = new SharedPrefs(this);

        new PermissionHandler(this).askStoragePermissions();

        //Initializing form handling
        handler = new DoctorFormHandler(this,binding.doctorForm);
        handler.initialize();

        loadData();
    }

    void loadData(){
        DoctorProfileController.getDoctor(prefs.getToken(), new Callback<DoctorGetResponseModel>() {
            @Override
            public void onResponse(Call<DoctorGetResponseModel> call, Response<DoctorGetResponseModel> response) {
                if(response.isSuccessful()){
                    doctor = response.body().getProfile().get(0);
                    setUpData();
                }
            }

            @Override
            public void onFailure(Call<DoctorGetResponseModel> call, Throwable t) {

            }
        });
    }

    void setUpData(){

        new ImageDownloader(binding.doctorForm.image,doctor.getPhotoPath(),this);

        binding.doctorForm.name.setText(doctor.getDoctorName());
        binding.doctorForm.registrationNumber.setText(doctor.getRegistrationNumber());
        binding.doctorForm.yearOfRegistration.setText(doctor.getYearOfRegistration());
        binding.doctorForm.phone.setText(doctor.getPhoneNumber());
        binding.doctorForm.stateMedicalCouncil.setText(String.valueOf(doctor.getStateMedicalCouncil()));
        binding.doctorForm.category.setText(doctor.getCategory());
        binding.doctorForm.about.setText(doctor.getAbout());

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
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}