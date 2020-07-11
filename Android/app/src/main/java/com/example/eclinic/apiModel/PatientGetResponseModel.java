package com.example.eclinic.apiModel;

import android.provider.ContactsContract;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.Body;

public class PatientGetResponseModel {

    @SerializedName("success")
    @Expose
    boolean success;
    @SerializedName("profile")
    @Expose
    List<Patient> list;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Patient> getList() {
        return list;
    }

    public void setList(List<Patient> list) {
        this.list = list;
    }
}
