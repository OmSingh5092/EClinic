package com.example.eclinic.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
    List<Doctor> filteredList = new ArrayList<>();

    DoctorRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        category = getIntent().getStringExtra("category");
        binding.toolbar.setTitle(category);
        setSupportActionBar(binding.toolbar);

        generateList();
        setUpRecyclerView();

        setUpSearch();
    }

    void generateList(){
        for(Doctor doctor: GeneralData.getDoctors()){
            if(doctor.getCategory().equals(category)){
                list.add(doctor);
            }
        }
        filteredList = list;
    }

    void setUpRecyclerView(){
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new DoctorRecyclerAdapter(filteredList,this);
        binding.recyclerView.setAdapter(adapter);
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
                    if(doctor.getDoctorName().toLowerCase().contains(text)){
                        filteredList.add(doctor);
                    }
                }
                adapter = new DoctorRecyclerAdapter(filteredList,CategoryActivity.this);
                binding.recyclerView.setAdapter(adapter);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}