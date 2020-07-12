package com.example.eclinic.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.eclinic.R;
import com.example.eclinic.apiControllers.AppointmentController;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.AppointmentAddResponseModel;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityBookAppointmentBinding;
import com.example.eclinic.utils.ImageDownloader;
import com.example.eclinic.utils.SharedPrefs;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

public class BookAppointmentActivity extends AppCompatActivity {
    ActivityBookAppointmentBinding binding;

    Doctor doctor;
    int doctorIndex;
    Appointment appointment;

    String text,pdfPath,interaction;

    SharedPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        doctorIndex = getIntent().getIntExtra("doctorIndex",0);
        doctor = GeneralData.getDoctors().get(doctorIndex);
        initializeText();
        new ImageDownloader(binding.image,doctor.getPhotoPath(),this);
        appointment = new Appointment();
        prefs = new SharedPrefs(this);

        binding.upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("application/pdf");
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i,100);
            }
        });

        String[] method = new String[]{"voice","video"};
        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.voice){
                    interaction = method[0];
                }else{
                    interaction = method[1];
                }
            }
        });

        binding.register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubmit();
            }
        });
    }

    void initializeText(){
        binding.name.setText(doctor.getDoctorName());
        binding.category.setText(doctor.getCategory()+"Specialist");
    }

    void onSubmit(){
        setText();
        appointment.setDoctorId(doctor.getDoctorId());
        appointment.setText(text);
        appointment.setInteractionMethod(interaction);
        appointment.setPrescriptionPath(pdfPath);

        AppointmentController.addAppointment(appointment, prefs.getToken(), new Callback<AppointmentAddResponseModel>() {
            @Override
            public void onResponse(Call<AppointmentAddResponseModel> call, Response<AppointmentAddResponseModel> response) {
                if(response.isSuccessful()){
                    Toast.makeText(BookAppointmentActivity.this, "Appointment added", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<AppointmentAddResponseModel> call, Throwable t) {

            }
        });
    }

    void setText(){
        text = binding.text.getText().toString();
    }

    void uploadPdf(Uri uri){
        Snackbar snackbar = Snackbar.make(binding.getRoot(),"Uploading..",Snackbar.LENGTH_INDEFINITE);
        snackbar.show();
        FirebaseStorage storage = FirebaseStorage.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        storage.getReference().child("prescription").child(auth.getUid())
                .child("prescription.pdf")
                .putFile(uri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        snackbar.dismiss();
                        Toast.makeText(BookAppointmentActivity.this, "Prescription Uploaded", Toast.LENGTH_SHORT).show();
                        pdfPath = "prescription/"+auth.getUid()+"/prescription.pdf";
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 100){
            uploadPdf(data.getData());
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}