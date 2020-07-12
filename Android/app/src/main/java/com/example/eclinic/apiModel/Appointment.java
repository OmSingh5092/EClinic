package com.example.eclinic.apiModel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Appointment {

    @SerializedName("appointment_id")
    @Expose
    int appointmentId;
    @SerializedName("doctor_id")
    @Expose
    int doctorId;
    @SerializedName("patient_id")
    @Expose
    int patientId;

    public int getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(int doctorId) {
        this.doctorId = doctorId;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    @SerializedName("prescription_path")
    @Expose
    String prescriptionPath;
    @SerializedName("text")
    @Expose
    String text;
    @SerializedName("status")
    @Expose
    boolean status;
    @SerializedName("payment_status")
    @Expose
    boolean paymentStatus;
    @SerializedName("new_prescription_path")
    @Expose
    String newPrescriptionPath;
    @SerializedName("interaction_method")
    @Expose
    String interactionMethod;
    @SerializedName("date")
    @Expose
    String date;
    @SerializedName("priority")
    @Expose
    int priority;

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getPrescriptionPath() {
        return prescriptionPath;
    }

    public void setPrescriptionPath(String prescriptionPath) {
        this.prescriptionPath = prescriptionPath;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(boolean paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getNewPrescriptionPath() {
        return newPrescriptionPath;
    }

    public void setNewPrescriptionPath(String newPrescriptionPath) {
        this.newPrescriptionPath = newPrescriptionPath;
    }

    public String getInteractionMethod() {
        return interactionMethod;
    }

    public void setInteractionMethod(String interactionMethod) {
        this.interactionMethod = interactionMethod;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
