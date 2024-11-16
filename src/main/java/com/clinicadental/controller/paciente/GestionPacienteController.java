package com.clinicadental.controller.paciente;

import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.view.paciente.GestionPaciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class GestionPacienteController {
    private final IPacienteService pacienteService;
    private final GestionPaciente pacienteTableView;
    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> sorter;

    public GestionPacienteController(GestionPaciente pacienteTableView) {
        this.pacienteTableView = pacienteTableView;
        this.pacienteService = new PacienteServiceImpl();

        // Inicializar el sorter con el modelo de la tabla
        this.tableModel = (DefaultTableModel) pacienteTableView.getPacienteTable().getModel();
        this.sorter = new TableRowSorter<>(tableModel);
        this.pacienteTableView.getPacienteTable().setRowSorter(sorter);

        // Cargar los pacientes en la tabla
        cargarPacientesEnTabla();

        // Configurar filtro en tiempo real
        configurarFiltroEnTiempoReal();
    }

    private void cargarPacientesEnTabla() {
        List<Paciente> pacientes = pacienteService.obtenerTodos();
        setPacientesData(pacientes);
    }

    private void setPacientesData(List<Paciente> pacientes) {
        tableModel.setRowCount(0);  // Limpiar la tabla antes de añadir nuevas filas
        for (Paciente paciente : pacientes) {
            tableModel.addRow(new Object[]{
                    paciente.getNombre(),
                    paciente.getApellidos(),
                    paciente.getDni(),
                    paciente.getTelefono(),
                    paciente.getDireccion(),
                    paciente.getCodPostal(),
                    paciente.getEmail()
            });
        }
    }

    private void configurarFiltroEnTiempoReal() {
        JTextField filterField = pacienteTableView.getGlobalFilterField();  // Obtener el campo de filtro global
        filterField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                aplicarFiltro(filterField.getText());
            }
        });
    }

    private void aplicarFiltro(String textoFiltro) {
        if (textoFiltro.isEmpty()) {
            sorter.setRowFilter(null);  // Mostrar todas las filas si el filtro está vacío
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoFiltro));  // Filtrar ignorando mayúsculas y minúsculas
        }
    }
}
