package com.clinicadental.controller.citas;

import com.clinicadental.model.Entity.Cita;
import com.clinicadental.service.ICitasService;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.CitasServiceImpl;
import com.clinicadental.view.citas.CitaDetails;
import com.clinicadental.view.citas.CitaEditar;
import com.clinicadental.view.citas.GestionCita;

import javax.swing.*;
import java.util.List;
import java.util.stream.Collectors;

public class CitaDetailsController {
    private ICitasService citaService;
    private IPacienteService pacienteService;
    private IDoctorService doctorService;
    private GestionCita citaTable;

    public CitaDetailsController(GestionCita citaTableView) {
        this.citaTable = citaTableView;
        this.citaService = new CitasServiceImpl();

        // Listener para detectar doble clic en la tabla y mostrar los detalles
        this.citaTable.getCitaTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) { // Detectar doble clic
                    int selectedRow = citaTable.getCitaTable().getSelectedRow();
                    if (selectedRow != -1) {
                        mostrarDetallesCita(selectedRow);
                    }
                }
            }
        });
    }

    private void mostrarDetallesCita(int selectedRow) {
        Cita cita = obtenerCitaDesdeTabla(selectedRow);
        if (cita != null) {
            String nombreCompletoPaciente = cita.getPaciente().toString();
            String nombreCompletoDoctor = cita.getDoctor().toString();

            // Almacena los nombres completos en variables del diálogo
            CitaDetails detailsDialog = new CitaDetails(
                    citaTable,
                    cita.getFecha(),
                    cita.getHora(),
                    nombreCompletoPaciente,
                    nombreCompletoDoctor,
                    cita.getMotivo()
            );

            // Define acciones de los botones sin alterar los nombres completos
            detailsDialog.addDeleteListener(e -> mostrarConfirmDialog(cita, detailsDialog));
            detailsDialog.addEditListener(e -> editarCita(cita, detailsDialog));

            detailsDialog.setVisible(true);
        } else {
            System.out.println("No se pudo obtener los detalles de la cita.");
        }
    }

    private void mostrarConfirmDialog(Cita cita, CitaDetails detailsDialog) {
        int confirm = JOptionPane.showConfirmDialog(
                detailsDialog,
                "¿Está seguro de que desea eliminar esta cita?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            eliminarCita(cita, detailsDialog);
        }
    }

    private void eliminarCita(Cita cita, CitaDetails detailsDialog) {
        citaService.deleteCita(cita);
        actualizarTablaCitas();
        JOptionPane.showMessageDialog(detailsDialog, "Cita eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        detailsDialog.cerrarDialogo(); // Asegura que solo aquí se cierre el diálogo al eliminar
    }

    private void editarCita(Cita cita, CitaDetails detailsDialog) {
        detailsDialog.dispose(); // Cierra el diálogo de detalles antes de abrir edición

        // Obtener listas de nombres de pacientes y doctores
        List<String> nombresPacientes = pacienteService.obtenerTodos().stream()
                .map(paciente -> paciente.getNombre() + " " + paciente.getApellidos())
                .collect(Collectors.toList());
        List<String> nombresDoctores = doctorService.getAllDoctor().stream()
                .map(doctor -> doctor.getNombre() + " " + doctor.getApellidos())
                .collect(Collectors.toList());

        // Crear el formulario de edición con las listas de nombres
        CitaEditar citaEditarForm = new CitaEditar(nombresPacientes, nombresDoctores);
        new CitaEditarController(cita, citaEditarForm, citaTable);
        citaEditarForm.setVisible(true);
    }

    private void actualizarTablaCitas() {
        List<Cita> citasActualizadas = citaService.getAllCitas();
        citaTable.setCitasData(citasActualizadas);
    }

    private Cita obtenerCitaDesdeTabla(int rowIndex) {
        int idCita = (int) citaTable.getCitaTable().getValueAt(rowIndex, 0);
        return citaService.getAllCitas().stream()
                .filter(c -> c.getId_cita() == idCita)
                .findFirst()
                .orElse(null);
    }
}
