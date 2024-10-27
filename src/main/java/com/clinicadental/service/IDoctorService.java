package com.clinicadental.service;

import com.clinicadental.model.Entity.Doctor;

import java.util.List;

public interface IDoctorService {

    void saveDoctor(Doctor doctor);
    void updateDoctor(Doctor doctor);
    void deleteDoctor(Doctor doctor);
    Doctor getDoctorId(int id);
    List<Doctor> getAllDoctor();
}
