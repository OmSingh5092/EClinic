package com.example.eclinic.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.RecyclerConfirmedAppointmentBinding;
import com.example.eclinic.utils.DateFormatter;
import com.google.android.material.button.MaterialButton;

import java.text.ParseException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ConfirmedAppointmentRecyclerAdapter extends RecyclerView.Adapter<ConfirmedAppointmentRecyclerAdapter.ViewHolder> {

    List<Appointment> data;
    Context context;

    RecyclerConfirmedAppointmentBinding binding;

    public ConfirmedAppointmentRecyclerAdapter(List<Appointment> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerConfirmedAppointmentBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Doctor doctor = findDoctor(data.get(position).getDoctorId());

        holder.name.setText(doctor.getDoctorName());
        try {
            holder.time.setText(new DateFormatter(data.get(position).getDate()).getFullDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(data.get(position).isPaymentStatus()){
            holder.pay.setVisibility(View.GONE);
        }

        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,time;
        MaterialButton pay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = binding.name;
            time = binding.time;
            pay = binding.pay;
        }
    }

    Doctor findDoctor(int id){
        for(Doctor doctor:GeneralData.getDoctors()){
            if(doctor.getDoctorId() == id){
                return doctor;
            }
        }
        return null;
    }
}
