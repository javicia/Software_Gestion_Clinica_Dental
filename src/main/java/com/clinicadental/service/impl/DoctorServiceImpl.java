package com.clinicadental.service.impl;

import com.clinicadental.model.Dao.DoctorDao;
import com.clinicadental.model.Dao.PacienteDao;
import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IDoctorService;

import java.util.List;

public class DoctorServiceImpl implements IDoctorService {

    private DoctorDao doctorDao = new DoctorDao();
    @Override
    public void saveDoctor(Doctor doctor) {
        doctorDao.saveDoctor(doctor);
    }

    @Override
    public void updateDoctor(Doctor doctor) {
        doctorDao.updateDoctor(doctor);
    }

    @Override
    public void deleteDoctor(Doctor doctor) {
        doctorDao.deleteDoctor(doctor);
    }

    @Override
    public Doctor getDoctorId(int id) {
        return doctorDao.getDoctorId(id);
    }

    @Override
    public List<Doctor> getAllDoctor() {
        return doctorDao.getAllDoctor();
    }
}

