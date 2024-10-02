package com.clinicadental.controller.paciente;

import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.view.paciente.PacienteTable;

public class PacienteTableController {
    private IPacienteService pacienteService;
    private PacienteTable pacienteTableView;

    public PacienteTableController(PacienteTable pacienteTableView) {
        this.pacienteTableView = pacienteTableView;
        this.pacienteService = new PacienteServiceImpl();

        // Cargar la tabla con los datos iniciales de los pacientes
        cargarPacientesEnTabla();
    }

    private void cargarPacientesEnTabla() {
        pacienteTableView.setPacientesData(pacienteService.obtenerTodos());  // Cargar datos en la tabla
    }
}
