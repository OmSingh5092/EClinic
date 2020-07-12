package com.example.eclinic.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eclinic.R;
import com.example.eclinic.adapters.PendingAppointmentRecyclerAdapter;
import com.example.eclinic.apiControllers.AppointmentController;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.AppointmentGetResponseModel;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityPendingAppointmentsBinding;
import com.example.eclinic.utils.SharedPrefs;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PendingAppointments extends AppCompatActivity  {
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
                    GeneralData.setAppointments(new ArrayList<>());
                    GeneralData.setAppointments(response.body().getAppointments());
                    for(Appointment appointment:response.body().getAppointments()){
                        if(!appointment.isStatus()){
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}