package com.example.eclinic.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.eclinic.R;
import com.example.eclinic.adapters.CategoryRecyclerAdapter;
import com.example.eclinic.adapters.DoctorRecyclerAdapter;
import com.example.eclinic.apiControllers.DoctorProfileController;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.apiModel.DoctorAllGetResponseModel;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityPatientHomeBinding;
import com.example.eclinic.databinding.RecyclerDoctorsBinding;
import com.example.eclinic.utils.SharedPrefs;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class PatientHomeActivity extends AppCompatActivity {

    ActivityPatientHomeBinding binding;
    CategoryRecyclerAdapter categoryRecyclerAdapter;
    DoctorRecyclerAdapter doctorRecyclerAdapter;

    List<Doctor> filteredList = new ArrayList<>();

    SharedPrefs prefs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        prefs = new SharedPrefs(this);

        setSupportActionBar(binding.toolbar);

        loadData();
        setUpCategoryRecyclerView();
        handleDrawerMenu();

        setUpSearch();


    }

    void loadData(){

        DoctorProfileController.getAllDoctors(prefs.getToken(), new Callback<DoctorAllGetResponseModel>() {
            @Override
            public void onResponse(Call<DoctorAllGetResponseModel> call, Response<DoctorAllGetResponseModel> response) {
                if(response.isSuccessful()){
                    Log.i("Data loaded",response.body().getData().toString());
                    filteredList = response.body().getData();
                    GeneralData.setDoctors(response.body().getData());
                    setUpDoctorRecyclerView();
                }else{
                    Log.i("Data loaded",response.message());
                }
            }

            @Override
            public void onFailure(Call<DoctorAllGetResponseModel> call, Throwable t) {
                Log.e("Error",t.getMessage());
            }
        });


    }
    void setUpCategoryRecyclerView(){
        List<String> categoryList = new ArrayList<>();
        for(String item: getResources().getStringArray(R.array.doctor_category)){
            categoryList.add(item);
        }

        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        categoryRecyclerAdapter = new CategoryRecyclerAdapter(categoryList,this);
        binding.recyclerCategories.setAdapter(categoryRecyclerAdapter);
    }

    void setUpDoctorRecyclerView(){
        binding.recyclerDoctor.setLayoutManager(new LinearLayoutManager(this));
        doctorRecyclerAdapter = new DoctorRecyclerAdapter(filteredList,this);
        binding.recyclerDoctor.setAdapter(doctorRecyclerAdapter);
    }

    void handleDrawerMenu(){
        binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.logout){
                    prefs.clearData();
                    FirebaseAuth.getInstance().signOut();
                    Intent i = new Intent(PatientHomeActivity.this,GetStartedActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                }else if(item.getItemId() == R.id.profile){
                    Intent i = new Intent(PatientHomeActivity.this,PatientProfileActivity.class);
                    startActivity(i);
                }
                return false;
            }
        });
    }

    void setUpSearch(){
        binding.search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = editable.toString().toLowerCase();
                filteredList = new ArrayList<>();
                for(Doctor doctor: GeneralData.getDoctors()){
                    if(doctor.getDoctorName().contains(text)){
                        filteredList.add(doctor);
                    }
                }

                doctorRecyclerAdapter = new DoctorRecyclerAdapter(filteredList,PatientHomeActivity.this);
                binding.recyclerDoctor.setAdapter(doctorRecyclerAdapter);
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        binding.getRoot().openDrawer(Gravity.LEFT);
        return super.onSupportNavigateUp();
    }


}