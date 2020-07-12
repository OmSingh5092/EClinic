package com.example.eclinic.data;

import com.example.eclinic.apiModel.Doctor;
import com.example.eclinic.apiModel.Patient;

import java.util.ArrayList;
import java.util.List;

public class GeneralData {

    private static List<Doctor> doctors= new ArrayList<>();
    private static  List<Patient> patients = new ArrayList<>();

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
}
