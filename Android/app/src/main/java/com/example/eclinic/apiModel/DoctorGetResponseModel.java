package com.example.eclinic.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class DoctorGetResponseModel {
    @SerializedName("success")
    @Expose
    boolean success;
    @SerializedName("profile")
    @Expose
    List<Doctor> profile;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<Doctor> getProfile() {
        return profile;
    }

    public void setProfile(List<Doctor> profile) {
        this.profile = profile;
    }
}
