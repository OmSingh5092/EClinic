package com.example.eclinic.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Doctor {

    @SerializedName("doctor_id")
    @Expose
    int doctorId;

    @SerializedName("phone_number")
    @Expose
    String phoneNumber;
    @SerializedName("email")
    @Expose
    String email;

    @SerializedName("doctor_name")
    @Expose
    String doctorName;
    @SerializedName("registration_number")
    @Expose
    String registrationNumber;
    @SerializedName("year_of_registration")
    @Expose
    String yearOfRegistration;
    @SerializedName("state_medical_council")
    @Expose
    String stateMedicalCouncil;

    @SerializedName("photo_path")
    @Expose
    String photoPath;

    @SerializedName("created_at")
    @Expose
    String createdAt;
    @SerializedName("updated_at")
    @Expose
    String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    String deletedAt;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getYearOfRegistration() {
        return yearOfRegistration;
    }

    public void setYearOfRegistration(String yearOfRegistration) {
        this.yearOfRegistration = yearOfRegistration;
    }

    public String getStateMedicalCouncil() {
        return stateMedicalCouncil;
    }

    public void setStateMedicalCouncil(String stateMedicalCouncil) {
        this.stateMedicalCouncil = stateMedicalCouncil;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
