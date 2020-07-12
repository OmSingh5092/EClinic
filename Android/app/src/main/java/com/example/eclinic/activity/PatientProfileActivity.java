package com.example.eclinic.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;

import com.example.eclinic.R;
import com.example.eclinic.apiControllers.PatientProfileController;
import com.example.eclinic.apiModel.Patient;
import com.example.eclinic.apiModel.PatientGetResponseModel;
import com.example.eclinic.databinding.ActivityPatientProfileBinding;
import com.example.eclinic.handlers.FormHandler;
import com.example.eclinic.handlers.PatientFormHandler;
import com.example.eclinic.utils.ImageDownloader;
import com.example.eclinic.utils.PermissionHandler;
import com.example.eclinic.utils.SharedPrefs;
import com.yalantis.ucrop.UCrop;

public class PatientProfileActivity extends AppCompatActivity implements FormHandler {

    ActivityPatientProfileBinding binding;
    SharedPrefs prefs;
    Patient patient;
    PatientFormHandler handler;

    int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        prefs = new SharedPrefs(this);

        new PermissionHandler(this).askStoragePermissions();

        //Initializing form handling
        handler = new PatientFormHandler(this,binding.patientForm);
        handler.initialize();

        loadData();
    }

    void loadData(){
        PatientProfileController.getProfile(prefs.getToken(), new Callback<PatientGetResponseModel>() {
            @Override
            public void onResponse(Call<PatientGetResponseModel> call, Response<PatientGetResponseModel> response) {
                if(response.isSuccessful()){
                    patient = response.body().getList().get(0);
                    setUpData();
                }
            }

            @Override
            public void onFailure(Call<PatientGetResponseModel> call, Throwable t) {

            }
        });
    }

    void setUpData(){
        new ImageDownloader(binding.patientForm.image,patient.getPhotoPath(),this);
        binding.patientForm.name.setText(patient.getPatientName());
        binding.patientForm.blood.setText(patient.getBlood());
        binding.patientForm.gender.setText(patient.getGender());
        binding.patientForm.phone.setText(patient.getPhoneNumber());
        binding.patientForm.weight.setText(String.valueOf(patient.getWeight()));

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