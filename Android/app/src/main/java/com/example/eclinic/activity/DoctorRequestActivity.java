package com.example.eclinic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.eclinic.R;
import com.example.eclinic.adapters.DoctorAppointmentRecyclerAdapter;
import com.example.eclinic.apiControllers.AppointmentController;
import com.example.eclinic.apiControllers.PatientProfileController;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.AppointmentGetResponseModel;
import com.example.eclinic.apiModel.PatientAllGetResponseModel;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityDoctorRequestBinding;
import com.example.eclinic.utils.SharedPrefs;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DoctorRequestActivity extends AppCompatActivity {
    ActivityDoctorRequestBinding binding;

    SharedPrefs prefs;

    DoctorAppointmentRecyclerAdapter adapter;

    List<Appointment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorRequestBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        prefs = new SharedPrefs(this);

        loadData();
    }

    void loadData(){
        AppointmentController.getAppointmentDoctor(prefs.getToken(), new Callback<AppointmentGetResponseModel>() {
            @Override
            public void onResponse(Call<AppointmentGetResponseModel> call, Response<AppointmentGetResponseModel> response) {
                if(response.isSuccessful()){
                    list = new ArrayList<>();
                    for(Appointment appointment:response.body().getAppointments()){
                        if(!appointment.isStatus()){
                            list.add(appointment);
                            initializeRecyclerView();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<AppointmentGetResponseModel> call, Throwable t) {

            }
        });
    }

    void initializeRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorAppointmentRecyclerAdapter(list,this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();;
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }
}