package com.clinicadental.controller.doctor;

import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.impl.DoctorServiceImpl;
import com.clinicadental.view.doctor.GestionDoctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;

public class GestionDoctorController {
    private final IDoctorService doctorService;
    private final GestionDoctor doctorTableView;
    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> sorter;

    public GestionDoctorController(GestionDoctor doctorTableView) {
        this.doctorTableView = doctorTableView;
        this.doctorService = new DoctorServiceImpl();

        // Inicializar el sorter con el modelo de la tabla
        this.tableModel = (DefaultTableModel) doctorTableView.getDoctorTable().getModel();
        this.sorter = new TableRowSorter<>(tableModel);
        this.doctorTableView.getDoctorTable().setRowSorter(sorter);

        // Cargar doctores en la tabla
        cargarDoctoresEnTabla();

        // Configurar filtro en tiempo real
        configurarFiltroEnTiempoReal();

    }

    private void cargarDoctoresEnTabla() {
        List<Doctor> doctores = doctorService.getAllDoctor();
        setDoctoresData(doctores);
    }

        private void setDoctoresData(List< Doctor > doctores) {
        tableModel.setRowCount(0); // Limpiar la tabla antes de actualizar
        for (Doctor doctor : doctores) {
            tableModel.addRow(new Object[]{
                    doctor.getNombre(),
                    doctor.getApellidos(),
                    doctor.getDni(),
                    doctor.getTelefono(),
                    doctor.getDireccion(),
                    doctor.getCodPostal(),
                    doctor.getEmail(),
                    doctor.getNumColegiado()
            });
        }
    }

    private void configurarFiltroEnTiempoReal() {
        JTextField filterField = doctorTableView.getGlobalFilterField();  // Obtener el campo de filtro global
        filterField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                aplicarFiltro(filterField.getText());
            }
        });
    }


    private void aplicarFiltro(String textoFiltro) {
        if (textoFiltro.isEmpty()) {
            sorter.setRowFilter(null); // Muestra todas las filas si no hay filtro
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoFiltro)); // Aplica el filtro ignorando mayúsculas/minúsculas
        }
    }


}
