package com.example.eclinic.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.eclinic.R;
import com.example.eclinic.apiControllers.AppointmentController;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.AppointmentUpdateResponseModel;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityDoctorAppointmentBinding;
import com.example.eclinic.utils.DateFormatter;
import com.example.eclinic.utils.PdfDownloader;
import com.example.eclinic.utils.PermissionHandler;
import com.example.eclinic.utils.SharedPrefs;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DoctorAppointmentActivity extends AppCompatActivity {
    ActivityDoctorAppointmentBinding binding;
    Appointment appointment;
    int appointmentIndex;

    String pdfPath;
    Calendar calendar;

    SharedPrefs prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDoctorAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);
        appointmentIndex = getIntent().getIntExtra("appointmentIndex",0);
        appointment = GeneralData.getAppointments().get(appointmentIndex);

        prefs = new SharedPrefs(this);

        new PermissionHandler(this).askStoragePermissions();

        calendar = Calendar.getInstance();

        try {
            setText();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        handleDateTimeClick();

        binding.newPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.setType("application/pdf");
                i.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(i,100);
            }
        });


        binding.prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new PdfDownloader(DoctorAppointmentActivity.this,appointment.getPrescriptionPath());
            }
        });

        binding.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAccept();
            }
        });

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
                        Toast.makeText(DoctorAppointmentActivity.this, "Prescription Uploaded", Toast.LENGTH_SHORT).show();
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

    void setText() throws NullPointerException, ParseException {
        binding.description.setText(appointment.getText());
        binding.mode.setText(appointment.getInteractionMethod());

        if(appointment.isStatus()){
            binding.date.setText(new DateFormatter(appointment.getDate()).getDateFormat());
            binding.time.setText(new DateFormatter(appointment.getDate()).getTimeFormat());
        }

    }

    void handleDateTimeClick(){
        TimePickerDialog dialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                calendar.set(Calendar.HOUR_OF_DAY,i);
                calendar.set(Calendar.MINUTE,i1);
                binding.time.setText(new SimpleDateFormat("hh:mm aa").format(new Date(calendar.getTimeInMillis())));
            }
        },12,0,false);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(Calendar.YEAR,i);
                calendar.set(Calendar.MONTH,i1);
                calendar.set(Calendar.DAY_OF_MONTH,i2);
                binding.date.setText(new SimpleDateFormat("dd-MM-yyyy EEE").format(new Date(calendar.getTimeInMillis())));
            }
        },2000,1,1);

        binding.time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.show();
            }
        });

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }


    void onAccept(){
        appointment.setStatus(true);
        appointment.setDate(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'").format(new Date(calendar.getTimeInMillis())));
        appointment.setNewPrescriptionPath(pdfPath);

        AppointmentController.updateAppointment(appointment, prefs.getToken(), new Callback<AppointmentUpdateResponseModel>() {
            @Override
            public void onResponse(Call<AppointmentUpdateResponseModel> call, Response<AppointmentUpdateResponseModel> response) {
                if(response.isSuccessful()){
                    GeneralData.getAppointments().set(appointmentIndex,appointment);
                    Toast.makeText(DoctorAppointmentActivity.this, "Appointment Accepted", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<AppointmentUpdateResponseModel> call, Throwable t) {

            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}