package com.example.eclinic.data;

import com.example.eclinic.apiModel.Appointment;
import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.apiModel.Patient;

import java.util.ArrayList;
import java.util.List;

public class GeneralData {

    private static List<Doctor> doctors= new ArrayList<>();
    private static  List<Patient> patients = new ArrayList<>();
    public static  List<Appointment> appointments = new ArrayList<>();

    public static List<Appointment> getAppointments() {
        return appointments;
    }

    public static void setAppointments(List<Appointment> appointments) {
        GeneralData.appointments = appointments;
    }

    public static List<Patient> getPatients() {
        return patients;
    }

    public static void setPatients(List<Patient> patients) {
        GeneralData.patients = patients;
    }

    public static List<Doctor> getDoctors() {
        return doctors;
    }

    public static void setDoctors(List<Doctor> doctors) {
        GeneralData.doctors = doctors;
    }

    public static  void reset(){
        doctors= new ArrayList<>();
        patients = new ArrayList<>();
        appointments = new ArrayList<>();

    }
}
