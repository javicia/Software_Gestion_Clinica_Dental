package com.clinicadental.controller.paciente;

import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.view.paciente.GestionPaciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;
import java.util.stream.Collectors;

public class GestionPacienteController {
    private IPacienteService pacienteService;
    private GestionPaciente pacienteTableView;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public GestionPacienteController(GestionPaciente pacienteTableView) {
        this.pacienteTableView = pacienteTableView;
        this.pacienteService = new PacienteServiceImpl();

        // Inicializar el sorter con el modelo de la tabla
        this.tableModel = (DefaultTableModel) pacienteTableView.getPacienteTable().getModel();
        this.sorter = new TableRowSorter<>(tableModel);
        this.pacienteTableView.getPacienteTable().setRowSorter(sorter);

        // Cargar los pacientes en la tabla
        cargarPacientesEnTabla();

        // Agregar lógica de filtrado
        agregarFiltros();
    }

    private void cargarPacientesEnTabla() {
        List<Paciente> pacientes = pacienteService.obtenerTodos();
        setPacientesData(pacientes);
    }

    private void setPacientesData(List<Paciente> pacientes) {
        tableModel.setRowCount(0);  // Limpiar la tabla antes de añadir nuevas filas
        for (Paciente paciente : pacientes) {
            Object[] row = {
                    paciente.getNombre(),
                    paciente.getApellidos(),
                    paciente.getDni(),
                    paciente.getTelefono(),
                    paciente.getDireccion(),
                    paciente.getCodPostal(),
                    paciente.getEmail()
            };
            tableModel.addRow(row);
        }
    }

    // Método para agregar los filtros a la tabla
    private void agregarFiltros() {
        JTextField[] filterFields = pacienteTableView.getFilterFields();  // Obtener los campos de filtro

        for (int i = 0; i < filterFields.length; i++) {
            final int colIndex = i;
            filterFields[i].addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent e) {
                    aplicarFiltros();  // Llamar al método de aplicar filtros cuando se escribe en un campo
                }
            });
        }
    }

    // Método para aplicar los filtros a la tabla
    private void aplicarFiltros() {
        List<RowFilter<Object, Object>> filters = java.util.stream.IntStream.range(0, pacienteTableView.getFilterFields().length)
                .mapToObj(i -> {
                    String filterText = pacienteTableView.getFilterFields()[i].getText();
                    return filterText.isEmpty() ? null : RowFilter.regexFilter("(?i)" + filterText, i);
                })
                .filter(f -> f != null)
                .collect(Collectors.toList());

        sorter.setRowFilter(filters.isEmpty() ? null : RowFilter.andFilter(filters));
    }
}
