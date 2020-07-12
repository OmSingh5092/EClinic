package com.example.eclinic.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.eclinic.activity.AboutDoctorActivity;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.databinding.RecyclerDoctorsBinding;
import com.example.eclinic.utils.ImageDownloader;

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
        holder.name.setText(data.get(position).getDoctorName());
        holder.category.setText(data.get(position).getCategory()+"Specialist");

        new ImageDownloader(holder.imageView,data.get(position).getPhotoPath(),context);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name,category;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = binding.image;
            name = binding.doctorName;
            category = binding.doctorCategory;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(context, AboutDoctorActivity.class);
                    i.putExtra("doctorIndex",getAdapterPosition());
                    context.startActivity(i);
                }
            });

        }
    }
}
