package com.example.eclinic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.databinding.RecyclerDoctorsBinding;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorRecyclerAdapter extends RecyclerView.Adapter<DoctorRecyclerAdapter.ViewHolder> {
    RecyclerDoctorsBinding binding;
    List<Doctor> data;
    Context context;

    public DoctorRecyclerAdapter(List<Doctor> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerDoctorsBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
