package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.os.Bundle;
import android.widget.Adapter;

import com.example.eclinic.R;
import com.example.eclinic.adapters.CategoryRecyclerAdapter;
import com.example.eclinic.adapters.DoctorRecyclerAdapter;
import com.example.eclinic.apiControllers.DoctorProfileController;
import com.example.eclinic.apiModel.DoctorAllGetResponseModel;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityPatientHomeBinding;
import com.example.eclinic.databinding.RecyclerDoctorsBinding;
import com.example.eclinic.utils.SharedPrefs;

import java.util.ArrayList;
import java.util.List;

public class PatientHomeActivity extends AppCompatActivity {

    ActivityPatientHomeBinding binding;
    CategoryRecyclerAdapter categoryRecyclerAdapter;
    DoctorRecyclerAdapter doctorRecyclerAdapter;

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
    }

    void loadData(){

        DoctorProfileController.getAllDoctors(prefs.getToken(), new Callback<DoctorAllGetResponseModel>() {
            @Override
            public void onResponse(Call<DoctorAllGetResponseModel> call, Response<DoctorAllGetResponseModel> response) {
                if(response.isSuccessful()){
                    GeneralData.setDoctors(response.body().getData());
                    setUpDoctorRecyclerView();
                }
            }

            @Override
            public void onFailure(Call<DoctorAllGetResponseModel> call, Throwable t) {

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
        doctorRecyclerAdapter = new DoctorRecyclerAdapter(GeneralData.getDoctors(),this);
        binding.recyclerDoctor.setAdapter(doctorRecyclerAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {

        return super.onSupportNavigateUp();
    }


}