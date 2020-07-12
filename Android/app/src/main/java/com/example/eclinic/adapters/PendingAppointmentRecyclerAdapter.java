package com.example.eclinic.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.eclinic.apiControllers.DoctorProfileController;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.apiModel.DoctorGetResponseModel;
import com.example.eclinic.databinding.RecyclerPendingAppointmentBinding;
import com.example.eclinic.utils.SharedPrefs;
import com.google.android.material.button.MaterialButton;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PendingAppointmentRecyclerAdapter extends RecyclerView.Adapter<PendingAppointmentRecyclerAdapter.ViewHolder> {

    RecyclerPendingAppointmentBinding binding;
    List<Appointment> data;
    Context context;

    SharedPrefs prefs;

    public PendingAppointmentRecyclerAdapter(List<Appointment> data, Context context) {
        this.data = data;
        this.context = context;
        prefs = new SharedPrefs(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = RecyclerPendingAppointmentBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DoctorProfileController.getDoctorFromId(prefs.getToken(),data.get(position).getDoctorId(),new Callback<DoctorGetResponseModel>() {
            @Override
            public void onResponse(Call<DoctorGetResponseModel> call, Response<DoctorGetResponseModel> response) {
                if(response.isSuccessful()){
                    holder.name.setText(response.body().getProfile().get(0).getDoctorName());
                }
            }

            @Override
            public void onFailure(Call<DoctorGetResponseModel> call, Throwable t) {

            }
        });

        holder.cancel.setOnClickListener(new View.OnClickListener() {
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
        TextView name;
        MaterialButton cancel;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name= binding.name;
            cancel = binding.cancle;

        }
    }
}
