package com.clinicadental.controller.citas;

import com.clinicadental.model.Entity.Cita;
import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.service.ICitasService;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.impl.CitasServiceImpl;
import com.clinicadental.service.impl.DoctorServiceImpl;
import com.clinicadental.view.citas.GestionCita;
import com.clinicadental.view.doctor.GestionDoctor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.List;
import java.util.stream.Collectors;

public class GestionCitaController {
    private ICitasService citaService;
    private GestionCita citaTableView;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public GestionCitaController(GestionCita citaTableView) {
        this.citaTableView = citaTableView;
        this.citaService = new CitasServiceImpl();

        // Inicializar el sorter con el modelo de la tabla
        this.tableModel = (DefaultTableModel) citaTableView.getCitaTable().getModel();
        this.sorter = new TableRowSorter<>(tableModel);
        this.citaTableView.getCitaTable().setRowSorter(sorter);

        // Cargar los pacientes en la tabla
        cargarCitasEnTabla();

        // Agregar lógica de filtrado
        agregarFiltros();
    }

    private void cargarCitasEnTabla() {
        List<Cita> citas = citaService.getAllCitas();
        setCitasData(citas);
    }

    private void setCitasData(List<Cita> citas) {
        tableModel.setRowCount(0);  // Limpiar la tabla antes de añadir nuevas filas
        for (Cita cita : citas) {
            Object[] row = {
                    cita.getFecha(),
                    cita.getHora(),
                    cita.getPaciente(),
                    cita.getDoctor(),
                    cita.getMotivo()
            };
            tableModel.addRow(row);
        }
    }

    // Método para agregar los filtros a la tabla
    private void agregarFiltros() {
        JTextField[] filterFields = citaTableView.getFilterFields();  // Obtener los campos de filtro

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
        List<RowFilter<Object, Object>> filters = java.util.stream.IntStream.range(0, citaTableView.getFilterFields().length)
                .mapToObj(i -> {
                    String filterText = citaTableView.getFilterFields()[i].getText();
                    return filterText.isEmpty() ? null : RowFilter.regexFilter("(?i)" + filterText, i);
                })
                .filter(f -> f != null)
                .collect(Collectors.toList());

        sorter.setRowFilter(filters.isEmpty() ? null : RowFilter.andFilter(filters));
    }
}


