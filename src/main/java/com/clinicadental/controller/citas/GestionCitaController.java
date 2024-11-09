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

        // Crear instancia de CitaDetailsController para manejar el diálogo de detalles
        new CitaDetailsController(citaTableView);

        cargarCitasEnTabla();
        agregarFiltros();

        // Agregar listener para doble clic en la tabla
        citaTableView.getCitaTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && citaTableView.getCitaTable().getSelectedRow() != -1) {
                    int selectedRow = citaTableView.getCitaTable().getSelectedRow();
                    mostrarDetallesCita(selectedRow);
                }
            }
        });
    }

    private void mostrarDetallesCita(int selectedRow) {
        // Convertir el índice de la vista al índice del modelo original
        int modelRow = citaTableView.getCitaTable().convertRowIndexToModel(selectedRow);
        int idCita = (int) tableModel.getValueAt(modelRow, 0); // Obtener el ID en la primera columna del modelo

        Cita cita = citaService.getCitaById(idCita);
        if (cita != null) {
            String nombreCompletoPaciente = cita.getPaciente().getApellidos() + ", " + cita.getPaciente().getNombre();
            String nombreCompletoDoctor = cita.getDoctor().getApellidos() + ", " + cita.getDoctor().getNombre();

            // Crear el diálogo de detalles
            CitaDetails detailsDialog = new CitaDetails(
                    citaTableView,
                    cita.getFecha(),
                    cita.getHora(),
                    nombreCompletoPaciente,
                    nombreCompletoDoctor,
                    cita.getMotivo()
            );

            // Mostrar el diálogo en el centro de la pantalla
            detailsDialog.setLocationRelativeTo(citaTableView);
            detailsDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(citaTableView, "No se pudo encontrar la cita seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
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

    private void agregarFiltros() {
        JTextField[] filterFields = citaTableView.getFilterFields();
        for (int i = 0; i < filterFields.length; i++) {
            final int colIndex = i;
            filterFields[i].addKeyListener(new java.awt.event.KeyAdapter() {
                @Override
                public void keyReleased(java.awt.event.KeyEvent e) {
                    aplicarFiltros();
                }
            });
        }
    }

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
