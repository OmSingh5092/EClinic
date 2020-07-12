package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;

import com.example.eclinic.R;
import com.example.eclinic.adapters.ConfirmedAppointmentRecyclerAdapter;
import com.example.eclinic.adapters.PendingAppointmentRecyclerAdapter;
import com.example.eclinic.apiControllers.AppointmentController;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.AppointmentGetResponseModel;
import com.example.eclinic.databinding.ActivityConfirmedAppointmentBinding;
import com.example.eclinic.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class ConfirmedAppointmentActivity extends AppCompatActivity {
    ActivityConfirmedAppointmentBinding binding;
    List<Appointment> appointmentList = new ArrayList<>();

    ConfirmedAppointmentRecyclerAdapter adapter;

    SharedPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConfirmedAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        prefs = new SharedPrefs(this);

        loadData();


    }

    void initializeRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ConfirmedAppointmentRecyclerAdapter(appointmentList,this);
        binding.recyclerView.setAdapter(adapter);
    }

    void loadData(){
        AppointmentController.getAppointment(prefs.getToken(), new Callback<AppointmentGetResponseModel>() {
            @Override
            public void onResponse(Call<AppointmentGetResponseModel> call, Response<AppointmentGetResponseModel> response) {
                if(response.isSuccessful()){
                    for(Appointment appointment:response.body().getAppointments()){
                        if(appointment.isStatus()){
                            appointmentList.add(appointment);
                        }
                    } ;
                    initializeRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<AppointmentGetResponseModel> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}