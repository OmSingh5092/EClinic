package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

import com.example.eclinic.R;
import com.example.eclinic.adapters.PendingAppointmentRecyclerAdapter;
import com.example.eclinic.apiControllers.AppointmentController;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.AppointmentGetResponseModel;
import com.example.eclinic.databinding.ActivityPendingAppointmentsBinding;
import com.example.eclinic.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class PendingAppointments extends AppCompatActivity {
    ActivityPendingAppointmentsBinding binding;
    List<Appointment> appointmentList = new ArrayList<>();

    PendingAppointmentRecyclerAdapter adapter ;

    SharedPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPendingAppointmentsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        prefs = new SharedPrefs(this);

        loadData();


    }

    void loadData(){
        AppointmentController.getAppointment(prefs.getToken(), new Callback<AppointmentGetResponseModel>() {
            @Override
            public void onResponse(Call<AppointmentGetResponseModel> call, Response<AppointmentGetResponseModel> response) {
                if(response.isSuccessful()){
                    appointmentList = response.body().getAppointments();
                    initializeRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<AppointmentGetResponseModel> call, Throwable t) {

            }
        });
    }

    void initializeRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PendingAppointmentRecyclerAdapter(appointmentList,this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}