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
        this.pacienteService = new PacienteServiceImpl(); // Asegúrate de inicializar
        this.doctorService = new DoctorServiceImpl();    // Asegúrate de inicializar

        // Listener para doble clic en la tabla
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

            // Crear el diálogo de detalles
            CitaDetails detailsDialog = new CitaDetails(
                    citaTable,
                    cita.getFecha(),
                    cita.getHora(),
                    nombreCompletoPaciente,
                    nombreCompletoDoctor,
                    cita.getMotivo()
            );

            // Define acciones de los botones
            detailsDialog.addDeleteListener(e -> mostrarConfirmDialog(cita, detailsDialog));
            detailsDialog.addEditListener(e -> editarCita(cita, detailsDialog));

            // Mostrar el diálogo
            detailsDialog.setVisible(true);
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
        try {
            System.out.println("Iniciando edición de cita...");

            // Cerrar el diálogo actual antes de abrir el formulario de edición
            if (detailsDialog != null) {
                System.out.println("Cerrando CitaDetails...");
                detailsDialog.setVisible(false); // Ocultar el diálogo
                detailsDialog.dispose();        // Liberar recursos
                System.out.println("CitaDetails cerrado.");
            }

            // Obtener listas de pacientes y doctores
            List<Paciente> pacientes = pacienteService.obtenerTodos();
            List<Doctor> doctores = doctorService.getAllDoctor();

            // Crear el formulario de edición con las listas obtenidas
            CitaEditar citaEditarForm = new CitaEditar(pacientes, doctores);
            new CitaEditarController(cita, citaEditarForm, citaTable);

            // Mostrar el formulario de edición
            citaEditarForm.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
