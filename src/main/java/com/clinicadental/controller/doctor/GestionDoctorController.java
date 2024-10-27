package com.clinicadental.controller.doctor;

import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.DoctorServiceImpl;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.view.doctor.GestionDoctor;
import com.clinicadental.view.paciente.GestionPaciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;
import java.util.stream.Collectors;

public class GestionDoctorController {
    private IDoctorService doctorService;
    private GestionDoctor doctorTableView;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public GestionDoctorController(GestionDoctor doctorTableView) {
        this.doctorTableView = doctorTableView;
        this.doctorService = new DoctorServiceImpl();

        // Inicializar el sorter con el modelo de la tabla
        this.tableModel = (DefaultTableModel) doctorTableView.getDoctorTable().getModel();
        this.sorter = new TableRowSorter<>(tableModel);
        this.doctorTableView.getDoctorTable().setRowSorter(sorter);

        // Cargar los pacientes en la tabla
        cargarDoctoresEnTabla();

        // Agregar lógica de filtrado
        agregarFiltros();
    }

    private void cargarDoctoresEnTabla() {
        List<Doctor> doctores = doctorService.getAllDoctor();
        setDoctoresData(doctores);
    }

    private void setDoctoresData(List<Doctor> doctores) {
        tableModel.setRowCount(0);  // Limpiar la tabla antes de añadir nuevas filas
        for (Doctor doctor : doctores) {
            Object[] row = {
                    doctor.getNombre(),
                    doctor.getApellidos(),
                    doctor.getDni(),
                    doctor.getTelefono(),
                    doctor.getDireccion(),
                    doctor.getCodPostal(),
                    doctor.getEmail(),
                    doctor.getNumColegiado()
            };
            tableModel.addRow(row);
        }
    }

    // Método para agregar los filtros a la tabla
    private void agregarFiltros() {
        JTextField[] filterFields = doctorTableView.getFilterFields();  // Obtener los campos de filtro

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
        List<RowFilter<Object, Object>> filters = java.util.stream.IntStream.range(0, doctorTableView.getFilterFields().length)
                .mapToObj(i -> {
                    String filterText = doctorTableView.getFilterFields()[i].getText();
                    return filterText.isEmpty() ? null : RowFilter.regexFilter("(?i)" + filterText, i);
                })
                .filter(f -> f != null)
                .collect(Collectors.toList());

        sorter.setRowFilter(filters.isEmpty() ? null : RowFilter.andFilter(filters));
    }
}


