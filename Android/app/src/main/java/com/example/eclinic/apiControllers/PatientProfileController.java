package com.example.eclinic.apiControllers;

import com.example.eclinic.apiModel.Patient;
import com.example.eclinic.apiModel.PatientGetResponseModel;
import com.example.eclinic.apiModel.PatientUpdateResponseModel;
import com.example.eclinic.apiModel.TokenResponseModel;
import com.example.eclinic.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;

public class PatientProfileController {

    public static void updateProfile(Patient patient, String token, Callback<PatientUpdateResponseModel> callback){
        Map<String,String> body = new HashMap<>();
        body.put("patient_name",patient.getPatientName());
        body.put("phone_number",patient.getPhoneNumber());
        body.put("email",patient.getEmail());
        body.put("blood",patient.getBlood());
        body.put("gender",patient.getGender());
        body.put("weight",String.valueOf(patient.getWeight()));

        Call<PatientUpdateResponseModel> call = RetrofitClient.getClient().updatePatient(token,body);
        call.enqueue(callback);
    }

    public static void getProfile(String token, Callback<PatientGetResponseModel> callback){
        Call<PatientGetResponseModel>call = RetrofitClient.getClient().getPatient(token);
        call.enqueue(callback);
    }
}
