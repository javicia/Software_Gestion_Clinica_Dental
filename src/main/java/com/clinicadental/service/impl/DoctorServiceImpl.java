package com.clinicadental.service.impl;

import com.clinicadental.model.Dao.CitaDao;
import com.clinicadental.model.Dao.DoctorDao;
import com.clinicadental.model.Dao.PacienteDao;
import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IDoctorService;

import java.util.List;

public class DoctorServiceImpl implements IDoctorService {

    private DoctorDao doctorDao = new DoctorDao();
    private CitaDao citaDao = new CitaDao();
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
        try {
            // Eliminar todas las citas asociadas al doctor
            citaDao.deleteCitasByDoctorId(doctor.getIdDoctor());

            // Luego eliminar al doctor
            doctorDao.deleteDoctor(doctor);
        } catch (Exception e) {
            throw new RuntimeException("Error al eliminar el doctor: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Doctor> getAllDoctor() {
        return doctorDao.getAllDoctor();
    }

    @Override
    public List<Doctor> findDoctorByName(String name) {
        return doctorDao.findDoctorByName(name);
    }
}

