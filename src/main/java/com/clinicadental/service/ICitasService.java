package com.clinicadental.service;

import com.clinicadental.model.Entity.Cita;

import java.util.List;

public interface ICitasService {
    void saveCita(Cita cita);
    void updateCita(Cita cita);
    void deleteCita(Cita cita);
    List<Cita> getAllCitas();
    Cita getCitaById(int id);
    void deleteCitasByDoctorId(int doctorId);
    void deleteCitasByPacienteId(int pacienteId);
}
