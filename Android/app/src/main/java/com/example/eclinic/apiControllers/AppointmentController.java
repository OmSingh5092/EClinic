package com.example.eclinic.apiControllers;


import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.AppointmentAddResponseModel;
import com.example.eclinic.apiModel.AppointmentGetResponseModel;
import com.example.eclinic.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class AppointmentController {

    public static void addAppointment(Appointment appointment, String token, Callback<AppointmentAddResponseModel> callback){
        Map<String,String> body = new HashMap<>();
        body.put("prescription_path",appointment.getPrescriptionPath());
        body.put("text",appointment.getText());
        body.put("doctor_id",String.valueOf(appointment.getDoctorId()));
        body.put("interaction_method",appointment.getInteractionMethod());

        Call<AppointmentAddResponseModel> call = RetrofitClient.getClient().addAppointment(token,body);
        call.enqueue(callback);
    }

    public static void getAppointment(String token, Callback<AppointmentGetResponseModel>callback){
        Call<AppointmentGetResponseModel> call= RetrofitClient.getClient().getAppointment(token);
        call.enqueue(callback);
    }

    public static void getAppointmentDoctor(String token, Callback<AppointmentGetResponseModel>callback){
        Call<AppointmentGetResponseModel> call= RetrofitClient.getClient().getAppointmentDoctor(token);
        call.enqueue(callback);
    }
}
