package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.widget.Adapter;

import com.example.eclinic.R;
import com.example.eclinic.adapters.CategoryRecyclerAdapter;
import com.example.eclinic.adapters.DoctorRecyclerAdapter;
import com.example.eclinic.databinding.ActivityPatientHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class PatientHomeActivity extends AppCompatActivity {

    ActivityPatientHomeBinding binding;
    CategoryRecyclerAdapter categoryRecyclerAdapter;
    DoctorRecyclerAdapter doctorRecyclerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        loadData();
        setUpCategoryRecyclerView();
    }

    void loadData(){

    }
    void setUpCategoryRecyclerView(){
        List<String> categoryList = new ArrayList<>();

        binding.recyclerCategories.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    void setUpDoctorRecyclerView(){

    }

    @Override
    public boolean onSupportNavigateUp() {

        return super.onSupportNavigateUp();
    }
}