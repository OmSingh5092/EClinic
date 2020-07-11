package com.example.eclinic.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorUpdateResponseModel {
    @SerializedName("success")
    @Expose
    boolean success;
    @SerializedName("user")
    @Expose
    Doctor doctor;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }
}
