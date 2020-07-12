package com.example.eclinic.data;

import com.example.eclinic.apiModel.Doctor;

import java.util.ArrayList;
import java.util.List;

public class GeneralData {

    private static List<Doctor> doctors= new ArrayList<>();

    public static List<Doctor> getDoctors() {
        return doctors;
    }

    public static void setDoctors(List<Doctor> doctors) {
        GeneralData.doctors = doctors;
    }
}
