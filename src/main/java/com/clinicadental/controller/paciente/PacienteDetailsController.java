package com.clinicadental.controller.paciente;

import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.view.paciente.PacienteDetailsDialog;
import com.clinicadental.view.paciente.PacienteTable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacienteDetailsController {
    private IPacienteService pacienteService;
    private PacienteTable pacienteTable;

    public PacienteDetailsController(PacienteTable pacienteTableView) {
        this.pacienteTable = pacienteTableView;
        this.pacienteService = new PacienteServiceImpl();

        this.pacienteTable.getPacienteTable().addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) { // Detectar doble clic
                    int selectedRow = pacienteTable.getPacienteTable().getSelectedRow();
                    if (selectedRow != -1) {
                        mostrarDetallesPaciente(selectedRow);
                    }
                }
            }
        });
    }

    // Método para mostrar los detalles del paciente seleccionado
    private void mostrarDetallesPaciente(int selectedRow) {
        Paciente paciente = obtenerPacienteDesdeTabla(selectedRow);
        if (paciente != null) {
            PacienteDetailsDialog detailsDialog = new PacienteDetailsDialog(
                    pacienteTable,
                    paciente.getNombre(),
                    paciente.getApellidos(),
                    paciente.getDni(),
                    paciente.getTelefono(),
                    paciente.getDireccion(),
                    paciente.getCodPostal().toString(),
                    paciente.getEmail()
            );

            // Listener para el botón "Eliminar"
            detailsDialog.addDeleteListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("Botón 'Eliminar' fue presionado en el diálogo");
                    mostrarConfirmDialog(paciente, detailsDialog);
                }
            });

            detailsDialog.setVisible(true);
        } else {
            System.out.println("No se pudo obtener el paciente.");
        }
    }

    // Mostrar el cuadro de diálogo de confirmación
    private void mostrarConfirmDialog(Paciente paciente, PacienteDetailsDialog detailsDialog) {
        System.out.println("Intentando mostrar el JOptionPane de confirmación");

        // Mostrar el cuadro de diálogo de confirmación
        int confirm = JOptionPane.showConfirmDialog(
                detailsDialog, // Cambié null a detailsDialog para asegurar que sea modal respecto al diálogo de detalles
                "¿Está seguro de que desea eliminar este paciente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        // Verificar qué opción eligió el usuario
        System.out.println("Respuesta del JOptionPane: " + confirm);

        // Si el usuario confirma (opción "Sí")
        if (confirm == JOptionPane.YES_OPTION) {
            eliminarPaciente(paciente, detailsDialog);
        } else {
            System.out.println("Eliminación cancelada por el usuario.");
        }
    }

    // Método para eliminar el paciente de la base de datos y actualizar la tabla
    private void eliminarPaciente(Paciente paciente, PacienteDetailsDialog detailsDialog) {
        System.out.println("Eliminando paciente: " + paciente.getDni());

        // Eliminar paciente de la base de datos
        pacienteService.deletePaciente(paciente);

        // Actualizar la tabla con los pacientes restantes
        pacienteTable.setPacientesData(pacienteService.obtenerTodos());

        // Mostrar mensaje de confirmación
        JOptionPane.showMessageDialog(null, "Paciente eliminado exitosamente.");

        // Cerrar el diálogo de detalles
        detailsDialog.cerrarDialogo();
    }

    private Paciente obtenerPacienteDesdeTabla(int rowIndex) {
        String dni = (String) pacienteTable.getPacienteTable().getValueAt(rowIndex, 2);
        return pacienteService.obtenerTodos().stream()
                .filter(p -> p.getDni().equals(dni))
                .findFirst()
                .orElse(null);
    }
}
