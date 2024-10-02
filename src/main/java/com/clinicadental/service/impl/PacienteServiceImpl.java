package com.clinicadental.service.impl;

import com.clinicadental.model.Dao.PacienteDao;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IPacienteService;

import java.util.List;

public class PacienteServiceImpl implements IPacienteService {
    private PacienteDao pacienteDao = new PacienteDao();

    @Override
    public void savePaciente(Paciente paciente) {
        pacienteDao.savePaciente(paciente);
    }

    @Override
    public void updatePaciente(Paciente paciente) {
        pacienteDao.updatePaciente(paciente);
    }

    @Override
    public void deletePaciente(Paciente paciente) {
        pacienteDao.deletePaciente(paciente);
    }

    @Override
    public Paciente getPacienteById(int id) {
        return pacienteDao.getIdPaciente(id);
    }

    @Override
    public List<Paciente> obtenerTodos() {
        return pacienteDao.obtenerTodos();
    }
}
