package com.example.eclinic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eclinic.activity.DoctorAppointmentActivity;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.Patient;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.RecyclerDoctorAppointmentBinding;
import com.example.eclinic.utils.DateFormatter;
import com.example.eclinic.utils.ImageDownloader;

import java.text.ParseException;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class DoctorAppointmentRecyclerAdapter extends RecyclerView.Adapter<DoctorAppointmentRecyclerAdapter.ViewHolder> {

    List<Appointment> data;
    Context context;

    RecyclerDoctorAppointmentBinding binding;

    public DoctorAppointmentRecyclerAdapter(List<Appointment> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerDoctorAppointmentBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Patient patient = findPatient(data.get(position).getPatientId());
        holder.name.setText(patient.getPatientName());
        try {
            holder.time.setText(new DateFormatter(data.get(position).getDate()).getDateFormat1());
        } catch (ParseException e) {
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }

        new ImageDownloader(holder.image,patient.getPhotoPath(),context);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name,time;
        ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = binding.name;
            time = binding.time;
            image = binding.image;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, DoctorAppointmentActivity.class);
                    i.putExtra("appointmentIndex",getAdapterPosition());
                    context.startActivity(i);
                }
            });
        }
    }

    Patient findPatient(int id){
        for(Patient patient: GeneralData.getPatients()){
            if(patient.getPatientId() == id){
                return patient;
            }
        }

        return null;
    }
}
