package com.example.eclinic.apiControllers;

import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.apiModel.DoctorAllGetResponseModel;
import com.example.eclinic.apiModel.DoctorGetResponseModel;
import com.example.eclinic.apiModel.DoctorUpdateResponseModel;
import com.example.eclinic.retrofit.RetrofitClient;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class DoctorProfileController {

    public static void updateDoctor(Doctor doctor, String token, Callback<DoctorUpdateResponseModel> callback){
        Map<String,String> body = new HashMap<>();

        body.put("phone_number",doctor.getPhoneNumber());
        body.put("email",doctor.getEmail());
        body.put("doctor_name",doctor.getDoctorName());
        body.put("registration_number",doctor.getRegistrationNumber());
        body.put("year_of_registration",doctor.getYearOfRegistration());
        body.put("state_medical_council",doctor.getStateMedicalCouncil());
        body.put("photo_path",doctor.getPhotoPath());
        body.put("category",doctor.getCategory());
        body.put("about",doctor.getAbout());

        Call<DoctorUpdateResponseModel> call = RetrofitClient.getClient().updateDoctor(token,body);
        call.enqueue(callback);
    }

    public static void getAllDoctors(String token, Callback<DoctorAllGetResponseModel> callback){
        Call<DoctorAllGetResponseModel> call = RetrofitClient.getClient().getAllDoctor(token);
        call.enqueue(callback);
    }

    public static void getDoctor(String token,Callback<DoctorGetResponseModel> callback){
        Call<DoctorGetResponseModel> call = RetrofitClient.getClient().getDoctor(token);
        call.enqueue(callback);
    }

}
