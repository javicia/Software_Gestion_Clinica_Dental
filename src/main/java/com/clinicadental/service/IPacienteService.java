package com.clinicadental.service;

import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;

import java.util.List;

public interface IPacienteService {

     void savePaciente(Paciente paciente);
     void updatePaciente(Paciente paciente);
     void deletePaciente(Paciente paciente);
     List<Paciente> obtenerTodos();
     List<Paciente> findListPacienteByName(String name);
}
