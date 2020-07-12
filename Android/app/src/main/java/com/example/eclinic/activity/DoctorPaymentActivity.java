package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.eclinic.R;
import com.example.eclinic.apiControllers.DoctorProfileController;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.apiModel.DoctorGetResponseModel;
import com.example.eclinic.apiModel.DoctorUpdateResponseModel;
import com.example.eclinic.databinding.ActivityDoctorPaymentBinding;
import com.example.eclinic.utils.SharedPrefs;

public class DoctorPaymentActivity extends AppCompatActivity {
    ActivityDoctorPaymentBinding binding;
    SharedPrefs prefs;
    Doctor doctor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorPaymentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        prefs = new SharedPrefs(this);
        loadData();

    }

    void loadData(){
        DoctorProfileController.getDoctor(prefs.getToken(), new Callback<DoctorGetResponseModel>() {
            @Override
            public void onResponse(Call<DoctorGetResponseModel> call, Response<DoctorGetResponseModel> response) {
                if(response.isSuccessful()){
                    doctor = response.body().getProfile().get(0);
                    try{
                        initialize();
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onFailure(Call<DoctorGetResponseModel> call, Throwable t) {

            }
        });
    }

    void initialize() throws NullPointerException{
        binding.upi.setText(doctor.getUpiId());
        binding.fees.setText(doctor.getFeesGeneral());

        binding.submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });
    }

    void onSubmit(){
        if(!isFilled()){
            return;
        }
        doctor.setUpiId(binding.upi.getText().toString());
        doctor.setFeesGeneral(binding.fees.getText().toString());
        DoctorProfileController.updateDoctor(doctor, prefs.getToken(), new Callback<DoctorUpdateResponseModel>() {
            @Override
            public void onResponse(Call<DoctorUpdateResponseModel> call, Response<DoctorUpdateResponseModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(DoctorPaymentActivity.this, "Details updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DoctorUpdateResponseModel> call, Throwable t) {

            }
        });
    }

    boolean isFilled(){
        if(binding.fees.getText().toString().length() == 0){
            Toast.makeText(this, "Enter a fees", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(binding.upi.getText().toString().length() == 0){
            Toast.makeText(this, "Enter Upi Id", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}