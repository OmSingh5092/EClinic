package com.example.eclinic.retrofit;

import com.example.eclinic.apiModel.AppointmentAddResponseModel;
import com.example.eclinic.apiModel.AppointmentGetResponseModel;
import com.example.eclinic.apiModel.AppointmentUpdateResponseModel;
import com.example.eclinic.apiModel.DoctorAllGetResponseModel;
import com.example.eclinic.apiModel.DoctorGetResponseModel;
import com.example.eclinic.apiModel.DoctorUpdateResponseModel;
import com.example.eclinic.apiModel.PatientAllGetResponseModel;
import com.example.eclinic.apiModel.PatientGetResponseModel;
import com.example.eclinic.apiModel.PatientUpdateResponseModel;
import com.example.eclinic.apiModel.TokenResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    @POST("google/patient")
    Call<TokenResponseModel> getPatientToken(@Body Map<String,String> body);

    @POST("google/doctor")
    Call<TokenResponseModel> getDoctorToken(@Body Map<String,String> body);

    @POST("patient/update")
    Call<PatientUpdateResponseModel> updatePatient(@Header("token") String token, @Body Map<String,String> body);
    @GET("patient/get")
    Call<PatientGetResponseModel> getPatient(@Header("token") String token);
    @GET("patient/getall")
    Call<PatientAllGetResponseModel> getAllPatient(@Header("token") String token);

    @POST("doctor/update")
    Call<DoctorUpdateResponseModel> updateDoctor(@Header("token") String token, @Body Map<String,String> body);
    @GET("doctor/get")
    Call<DoctorGetResponseModel> getDoctor(@Header("token") String token);
    @GET("doctor/getall")
    Call<DoctorAllGetResponseModel> getAllDoctor(@Header("token") String token);

    @POST("appointment/add")
    Call<AppointmentAddResponseModel> addAppointment(@Header("token") String token, @Body Map<String,String> body);
    @POST("appointment/update")
    Call<AppointmentUpdateResponseModel> updateAppointment(@Header("token") String token, @Body Map<String,String>body);
    @GET("appointment/get")
    Call<AppointmentGetResponseModel> getAppointment(@Header("token") String token);
    @GET("appointment/get/doctor")
    Call<AppointmentGetResponseModel> getAppointmentDoctor(@Header("token") String token);
}
