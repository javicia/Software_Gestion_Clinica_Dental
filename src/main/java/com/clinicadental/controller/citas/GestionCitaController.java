package com.clinicadental.controller.citas;

import com.clinicadental.model.Entity.Cita;
import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.ICitasService;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.CitasServiceImpl;
import com.clinicadental.service.impl.DoctorServiceImpl;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.utils.DateUtils;
import com.clinicadental.view.citas.CitaDetails;
import com.clinicadental.view.citas.CitaEditar;
import com.clinicadental.view.citas.GestionCita;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.stream.Collectors;

public class GestionCitaController {
    private final ICitasService citaService;
    private final IDoctorService doctorService;
    private final IPacienteService pacienteService;
    private final GestionCita citaTableView;
    private final DefaultTableModel tableModel;
    private final TableRowSorter<DefaultTableModel> sorter;
    private JDialog activeDialog = null;

    public GestionCitaController(GestionCita citaTableView) {
        this.citaTableView = citaTableView;
        this.citaService = new CitasServiceImpl();
        this.doctorService = new DoctorServiceImpl();
        this.pacienteService = new PacienteServiceImpl();

        this.tableModel = (DefaultTableModel) citaTableView.getCitaTable().getModel();
        this.sorter = new TableRowSorter<>(tableModel);
        this.citaTableView.getCitaTable().setRowSorter(sorter);

        cargarCitasEnTabla();
        agregarFiltros();

        // Agregar listener para doble clic en la tabla
        citaTableView.getCitaTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && citaTableView.getCitaTable().getSelectedRow() != -1) {
                    SwingUtilities.invokeLater(() -> {
                        int selectedRow = citaTableView.getCitaTable().getSelectedRow();
                        mostrarDetallesCita(selectedRow);
                    });
                }
            }
        });
    }

    private void mostrarDetallesCita(int selectedRow) {
        if (activeDialog != null && activeDialog.isVisible()) {
            return; // Evitar abrir múltiples diálogos si ya hay uno activo
        }

        int modelRow = citaTableView.getCitaTable().convertRowIndexToModel(selectedRow);
        int idCita = (int) tableModel.getValueAt(modelRow, 0);

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

            // Configurar acciones de los botones "Editar" y "Eliminar"
            detailsDialog.addEditListener(e -> editarCita(cita, detailsDialog));
            detailsDialog.addDeleteListener(e -> eliminarCita(cita, detailsDialog));

            // Mostrar el diálogo
            activeDialog = detailsDialog;
            activeDialog.setLocationRelativeTo(citaTableView);
            activeDialog.setVisible(true);

            activeDialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent e) {
                    activeDialog = null; // Liberar referencia al cerrar el diálogo
                }
            });
        } else {
            JOptionPane.showMessageDialog(citaTableView, "No se pudo encontrar la cita seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarCita(Cita cita, CitaDetails detailsDialog) {
        try {
            // Cerrar el diálogo actual antes de abrir el formulario de edición
            detailsDialog.dispose();

            List<Paciente> pacientes = pacienteService.obtenerTodos();
            List<Doctor> doctores = doctorService.getAllDoctor();

            CitaEditar citaEditarForm = new CitaEditar(pacientes, doctores);
            new CitaEditarController(cita, citaEditarForm, citaTableView);

            citaEditarForm.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(citaTableView, "Error al abrir el formulario de edición.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCita(Cita cita, CitaDetails detailsDialog) {
        int confirm = JOptionPane.showConfirmDialog(
                detailsDialog,
                "¿Está seguro de que desea eliminar esta cita?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            citaService.deleteCita(cita); // Eliminar la cita del servicio
            actualizarTablaCitas(); // Refrescar la tabla
            JOptionPane.showMessageDialog(detailsDialog, "Cita eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            detailsDialog.dispose(); // Cierra el diálogo después de eliminar
        }
    }


    private void cargarCitasEnTabla() {
        List<Cita> citas = citaService.getAllCitas();
        setCitasData(citas);
    }

    private void setCitasData(List<Cita> citas) {
        tableModel.setRowCount(0); // Limpiar todas las filas de la tabla
        for (Cita cita : citas) {
            Object[] row = {
                    cita.getId_cita(),
                    DateUtils.formatFecha(cita.getFecha()),
                    DateUtils.formatHora(cita.getHora()),
                    cita.getPaciente().getApellidos() + ", " + cita.getPaciente().getNombre(),
                    cita.getDoctor().getApellidos() + ", " + cita.getDoctor().getNombre(),
                    cita.getMotivo()
            };
            tableModel.addRow(row); // Añadir las filas actualizadas al modelo de la tabla
        }
    }

    private void actualizarTablaCitas() {
        List<Cita> citasActualizadas = citaService.getAllCitas(); // Obtener las citas actualizadas desde el servicio
        setCitasData(citasActualizadas); // Actualizar los datos en el modelo de la tabla
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
