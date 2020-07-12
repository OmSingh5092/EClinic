package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.eclinic.R;
import com.example.eclinic.adapters.DoctorRecyclerAdapter;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityCategoryBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoryActivity extends AppCompatActivity {

    ActivityCategoryBinding binding;

    String category;
    List<Doctor> list = new ArrayList<>();

    DoctorRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        category = getIntent().getStringExtra("category");

        generateList();
        setUpRecyclerView();

        getSupportActionBar().setTitle(category);


    }

    void generateList(){
        for(Doctor doctor: GeneralData.getDoctors()){
            list.add(doctor);
        }
    }

    void setUpRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorRecyclerAdapter(list,this);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        Toast.makeText(this, "Back", Toast.LENGTH_SHORT).show();
        return super.onSupportNavigateUp();
    }
}