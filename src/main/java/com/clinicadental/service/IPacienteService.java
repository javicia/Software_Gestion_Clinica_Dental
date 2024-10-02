package com.clinicadental.service;

import com.clinicadental.model.Entity.Paciente;

import java.util.List;

public interface IPacienteService {

     void savePaciente(Paciente paciente);
     void updatePaciente(Paciente paciente);
     void deletePaciente(Paciente paciente);
     Paciente getPacienteById(int id);
     List<Paciente> obtenerTodos();
}
