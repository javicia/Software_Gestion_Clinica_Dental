package com.clinicadental.controller.citas;

import com.clinicadental.model.Entity.Cita;
import com.clinicadental.service.ICitasService;
import com.clinicadental.service.impl.CitasServiceImpl;
import com.clinicadental.view.citas.CitaDetails;
import com.clinicadental.view.citas.GestionCita;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CitaDetailsController {
    private ICitasService citaService;
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

    // Método para mostrar los detalles de la cita seleccionada
    private void mostrarDetallesCita(int selectedRow) {
        Cita cita = obtenerCitaDesdeTabla(selectedRow);
        if (cita != null) {
            // Concatenamos nombre y apellidos para el paciente y el doctor
            String nombreCompletoPaciente = cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellidos();
            String nombreCompletoDoctor = cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellidos();

            // Creamos el diálogo con los detalles de la cita
            CitaDetails detailsDialog = new CitaDetails(
                    citaTable,
                    cita.getFecha(),
                    cita.getHora(),
                    nombreCompletoPaciente, // Pasamos el nombre completo del paciente
                    nombreCompletoDoctor,   // Pasamos el nombre completo del doctor
                    cita.getMotivo()
            );

            // Mostrar el diálogo de detalles
            detailsDialog.setVisible(true);
        } else {
            System.out.println("No se pudo obtener los detalles de la cita.");
        }
    }


    // Método para mostrar el cuadro de diálogo de confirmación
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

    // Método para eliminar la cita de la base de datos y actualizar la tabla
    private void eliminarCita(Cita cita, CitaDetails detailsDialog) {
        // Eliminar la cita de la base de datos
        citaService.deleteCita(cita); // Llama al método del servicio para eliminar la cita

        // Actualiza la tabla de citas
        List<Cita> citasActualizadas = citaService.getAllCitas(); // Obtiene la lista actualizada de citas
        citaTable.setCitasData(citasActualizadas); // Actualiza el modelo de la tabla en la vista

        // Mostrar mensaje de confirmación
        JOptionPane.showMessageDialog(null, "Cita eliminada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        // Cerrar el diálogo de detalles
        detailsDialog.cerrarDialogo();
    }

    // Método para obtener la cita desde la tabla usando el id_cita
    private Cita obtenerCitaDesdeTabla(int rowIndex) {
        int idCita = (int) citaTable.getCitaTable().getValueAt(rowIndex, 0); // Obtener id_cita desde la primera columna
        return citaService.getAllCitas().stream()
                .filter(c -> c.getId_cita() == idCita)
                .findFirst()
                .orElse(null);
    }
}
