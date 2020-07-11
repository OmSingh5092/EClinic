package com.example.eclinic.retrofit;

import com.example.eclinic.apiModel.TokenResponseModel;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIInterface {

    @POST("google/patient")
    Call<TokenResponseModel> getPatientToken(@Body Map<String,String> body);

    @FormUrlEncoded
    @POST("google/doctor")
    @Headers({
            "Accept: application/json",
            "Content-Type: application/x-www-form-urlencoded"
    })
    Call<TokenResponseModel> getDoctorToken(@Field("idToken") String token);
}
