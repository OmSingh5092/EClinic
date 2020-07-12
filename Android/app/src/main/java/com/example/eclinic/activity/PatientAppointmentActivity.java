package com.example.eclinic.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.eclinic.R;
import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.data.GeneralData;
import com.example.eclinic.databinding.ActivityPatientAppointmentBinding;
import com.example.eclinic.utils.DateFormatter;
import com.example.eclinic.utils.ImageDownloader;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;

public class PatientAppointmentActivity extends AppCompatActivity implements PaymentResultListener {
    ActivityPatientAppointmentBinding binding;

    int appointmentIndex;
    Appointment appointment;
    Doctor doctor;

    Checkout checkout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPatientAppointmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        appointmentIndex = getIntent().getIntExtra("appointmentIndex",0);
        appointment = GeneralData.getAppointments().get(appointmentIndex);

        doctor = findDoctor(appointment.getDoctorId());

        setFields();

        binding.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handlePayment();
            }
        });
    }

    void handlePayment(){
        checkout = new Checkout();
        checkout.setKeyID(getResources().getString(R.string.razorpay_key_id));
        JSONObject data = new JSONObject();
        try{
            data.put("amount", doctor.getFeesGeneral());
            data.put("email", doctor.getEmail());
            data.put("contact", doctor.getPhoneNumber());
            data.put("method", "upi");
            data.put("_[flow]", "intent");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        checkout.open(this,data);

    }

    void setFields(){

        if(appointment.isPaymentStatus()){
            binding.pay.setVisibility(View.GONE);
        }

        binding.name.setText(doctor.getDoctorName());
        binding.category.setText(doctor.getCategory());

        new ImageDownloader(binding.image,doctor.getPhotoPath(),this);

        binding.description.setText(appointment.getText());
        binding.interaction.setText(appointment.getInteractionMethod());
        try {
            binding.time.setText(new DateFormatter(appointment.getDate()).getFullDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    void seePdf(){

    }

    Doctor findDoctor(int id){
        for(Doctor doctor:GeneralData.getDoctors()){
            if(doctor.getDoctorId() == id){
                return doctor;
            }
        }

        return null;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(checkout!=null){
            checkout.onActivityResult(requestCode,resultCode,data);
        }
    }  */

    @Override
    public void onPaymentSuccess(String s) {

        Toast.makeText(this, "Payment Successfull", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.e("Payment Error",s);
    }
}