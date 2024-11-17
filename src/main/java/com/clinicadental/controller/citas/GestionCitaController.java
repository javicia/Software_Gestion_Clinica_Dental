package com.clinicadental.controller.citas;

import com.clinicadental.model.Entity.Cita;
import com.clinicadental.service.ICitasService;
import com.clinicadental.service.impl.CitasServiceImpl;
import com.clinicadental.utils.DateUtils;
import com.clinicadental.view.citas.CitaDetails;
import com.clinicadental.view.citas.GestionCita;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionCitaController {
    private ICitasService citaService;
    private GestionCita citaTableView;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    private CitaDetails detailsDialog;

    public GestionCitaController(GestionCita citaTableView) {
        this.citaTableView = citaTableView;
        this.citaService = new CitasServiceImpl();

        // Inicializar el sorter con el modelo de la tabla
        this.tableModel = (DefaultTableModel) citaTableView.getCitaTable().getModel();
        this.sorter = new TableRowSorter<>(tableModel);
        this.citaTableView.getCitaTable().setRowSorter(sorter);


        cargarCitasEnTabla();
        configurarFiltroEnTiempoReal();

    }

    private void cargarCitasEnTabla() {
        List<Cita> citas = citaService.getAllCitas();
        setCitasData(citas);
    }

private void setCitasData(List<Cita> citas) {
    tableModel.setRowCount(0);  // Limpiar la tabla antes de añadir nuevas filas
    for (Cita cita : citas) {
        Object[] row = {
                cita.getId_cita(),
                DateUtils.formatFecha(cita.getFecha()),
                DateUtils.formatHora(cita.getHora()),
                cita.getPaciente().getApellidos() + ", " + cita.getPaciente().getNombre(),
                cita.getDoctor().getApellidos() + ", " + cita.getDoctor().getNombre(),
                cita.getMotivo()
        };
        tableModel.addRow(row);
    }
}

    private void configurarFiltroEnTiempoReal() {
        JTextField filterField = citaTableView.getGlobalFilterField(); // Obtener el campo de filtro global
        filterField.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                aplicarFiltro(filterField.getText());
            }
        });
    }

    private void aplicarFiltro(String textoFiltro) {
        if (textoFiltro.isEmpty()) {
            sorter.setRowFilter(null); // Mostrar todas las filas si el filtro está vacío
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + textoFiltro)); // Filtrar ignorando mayúsculas y minúsculas
        }
    }
}
