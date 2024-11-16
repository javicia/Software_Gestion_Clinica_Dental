package com.clinicadental.controller.doctor;

import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.service.ICitasService;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.impl.DoctorServiceImpl;
import com.clinicadental.view.doctor.DoctorDetails;
import com.clinicadental.view.doctor.GestionDoctor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class DoctorDetailsController {
    private IDoctorService doctorService;
    private ICitasService citaService;
    private GestionDoctor doctorTable;

    public DoctorDetailsController(GestionDoctor doctorTableView) {
        this.doctorTable = doctorTableView;
        this.doctorService = new DoctorServiceImpl();

        this.doctorTable.getDoctorTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) { // Detectar doble clic
                    int selectedRow = doctorTable.getDoctorTable().getSelectedRow();
                    if (selectedRow != -1) {
                        mostrarDetallesDoctor(selectedRow);
                    }
                }
            }
        });
    }

    // Método para mostrar los detalles del paciente seleccionado
    private void mostrarDetallesDoctor(int selectedRow) {
        Doctor doctor = obtenerDoctorDesdeTabla(selectedRow);
        if (doctor != null) {
            DoctorDetails detailsDialog = new DoctorDetails(
                    doctorTable,
                    doctor.getNombre(),
                    doctor.getApellidos(),
                    doctor.getDni(),
                    doctor.getTelefono(),
                    doctor.getDireccion(),
                    doctor.getCodPostal().toString(),
                    doctor.getEmail(),
                    doctor.getNumColegiado()
            );

            // Listener para el botón "Eliminar"
            detailsDialog.addDeleteListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Botón 'Eliminar' fue presionado en el diálogo");
                    mostrarConfirmDialog(doctor, detailsDialog);
                }
            });

            detailsDialog.setVisible(true);
        } else {
            System.out.println("No se pudo obtener el paciente.");
        }
    }

    // Método para mostrar el cuadro de diálogo de confirmación
    private void mostrarConfirmDialog(Doctor doctor, DoctorDetails detailsDialog) {
        int confirm = JOptionPane.showConfirmDialog(
                detailsDialog,
                "¿Está seguro de que desea eliminar este doctor?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            eliminarDoctor(doctor, detailsDialog);
        }
    }

    // Método para eliminar el doctor de la base de datos y actualizar la tabla
    private void eliminarDoctor(Doctor doctor, DoctorDetails detailsDialog) {
        try {
            // Verificar si el doctor existe antes de intentar eliminar
            if (doctor == null || doctor.getIdDoctor() == 0) {
                JOptionPane.showMessageDialog(null, "El doctor no existe o no tiene un ID válido.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Eliminar citas asociadas al doctor
            citaService.deleteCitasByDoctorId(doctor.getIdDoctor());

            // Eliminar el doctor de la base de datos
            doctorService.deleteDoctor(doctor);

            // Actualizar la tabla de doctores
            actualizarTablaDoctores();

            // Mostrar mensaje de éxito
            JOptionPane.showMessageDialog(null, "Doctor eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Cerrar el diálogo de detalles
            detailsDialog.cerrarDialogo();
        } catch (Exception e) {
            // Manejar errores y mostrar mensaje amigable al usuario
            JOptionPane.showMessageDialog(
                    null,
                    "Error al eliminar el doctor: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            e.printStackTrace(); // Depuración opcional para el desarrollador
        }
    }



    // Método para actualizar la tabla de doctores
    private void actualizarTablaDoctores() {
        List<Doctor> doctoresActualizados = doctorService.getAllDoctor(); // Obtiene la lista actualizada de doctores
        doctorTable.setDoctoresData(doctoresActualizados); // Llama al método en la vista para actualizar la tabla
    }



    private Doctor obtenerDoctorDesdeTabla(int rowIndex) {
        String dni = (String) doctorTable.getDoctorTable().getValueAt(rowIndex, 2);
        return doctorService.getAllDoctor().stream()
                .filter(p -> p.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }
}
