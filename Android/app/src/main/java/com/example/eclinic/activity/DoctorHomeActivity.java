package com.example.eclinic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;

import com.example.eclinic.R;
import com.example.eclinic.adapters.DoctorAppointmentRecyclerAdapter;
import com.example.eclinic.apiControllers.AppointmentController;
import com.example.eclinic.apiControllers.PatientProfileController;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.AppointmentGetResponseModel;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.apiModel.PatientAllGetResponseModel;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityDoctorHomeBinding;
import com.example.eclinic.databinding.ActivityDoctorRegisterBinding;
import com.example.eclinic.utils.SharedPrefs;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class DoctorHomeActivity extends AppCompatActivity {

    ActivityDoctorHomeBinding binding;
    SharedPrefs prefs;

    DoctorAppointmentRecyclerAdapter adapter;

    List<Appointment> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        prefs = new SharedPrefs(this);
        handleDrawerMenu();

        loadPatientData();
    }

    void loadPatientData(){
        PatientProfileController.getAllProfile(prefs.getToken(), new Callback<PatientAllGetResponseModel>() {
            @Override
            public void onResponse(Call<PatientAllGetResponseModel> call, Response<PatientAllGetResponseModel> response) {
                if(response.isSuccessful()){
                    GeneralData.setPatients(response.body().getPatients());
                    loadData();
                }
            }

            @Override
            public void onFailure(Call<PatientAllGetResponseModel> call, Throwable t) {

            }
        });


    }

    void loadData(){
        AppointmentController.getAppointmentDoctor(prefs.getToken(), new Callback<AppointmentGetResponseModel>() {
            @Override
            public void onResponse(Call<AppointmentGetResponseModel> call, Response<AppointmentGetResponseModel> response) {
                if(response.isSuccessful()){
                    GeneralData.setAppointments(new ArrayList<>());
                    GeneralData.setAppointments(response.body().getAppointments());
                    list = new ArrayList<>();
                    for(Appointment appointment:response.body().getAppointments()){
                        if(appointment.isStatus()){
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

    void handleDrawerMenu(){
        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.logout){
                    prefs.clearData();
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(DoctorHomeActivity.this,GetStartedActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else if(item.getItemId() == R.id.request){
                    Intent i = new Intent(DoctorHomeActivity.this,DoctorRequestActivity.class);
                    startActivity(i);
                }else if(item.getItemId() == R.id.profile){
                    Intent i = new Intent(DoctorHomeActivity.this,DoctorProfileActivity.class);
                    startActivity(i);
                }else if(item.getItemId() == R.id.payment){
                    Intent i = new Intent(DoctorHomeActivity.this,DoctorPaymentActivity.class);
                    startActivity(i);
                }
                return false;
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        binding.getRoot().openDrawer(Gravity.LEFT);
        return super.onSupportNavigateUp();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        loadData();
    }
}