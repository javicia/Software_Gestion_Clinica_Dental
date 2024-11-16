package com.clinicadental.controller.paciente;

import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.view.paciente.GestionPaciente;
import com.clinicadental.view.paciente.PacienteDetails;
import com.clinicadental.view.paciente.PacienteEditar;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacienteDetailsController {
    private final IPacienteService pacienteService;
    private final GestionPaciente pacienteTable;

    public PacienteDetailsController(GestionPaciente pacienteTableView) {
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

    private void mostrarDetallesPaciente(int selectedRow) {
        Paciente paciente = obtenerPacienteDesdeTabla(selectedRow);
        if (paciente != null) {
            PacienteDetails detailsDialog = new PacienteDetails(
                    pacienteTable,
                    paciente.getNombre(),
                    paciente.getApellidos(),
                    paciente.getDni(),
                    paciente.getTelefono(),
                    paciente.getDireccion(),
                    paciente.getCodPostal().toString(),
                    paciente.getEmail()
            );

            // Listener para el botón "Editar"
            detailsDialog.addEditListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    abrirFormularioEdicion(paciente, detailsDialog);
                }
            });

            // Listener para el botón "Eliminar"
            detailsDialog.addDeleteListener(e -> mostrarConfirmDialog(paciente, detailsDialog));

            detailsDialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(pacienteTable, "No se pudo obtener los detalles del paciente.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void abrirFormularioEdicion(Paciente paciente, PacienteDetails detailsDialog) {
        // Cerrar la ventana de detalles
        detailsDialog.cerrarDialogo();

        // Crear y abrir el formulario de edición
        PacienteEditar editarPacienteForm = new PacienteEditar();
        editarPacienteForm.getNombreField().setText(paciente.getNombre());
        editarPacienteForm.getApellidosField().setText(paciente.getApellidos());
        editarPacienteForm.getDniField().setText(paciente.getDni());
        editarPacienteForm.getTelefonoField().setText(paciente.getTelefono());
        editarPacienteForm.getDireccionField().setText(paciente.getDireccion());
        editarPacienteForm.getCodPostalField().setText(String.valueOf(paciente.getCodPostal()));
        editarPacienteForm.getEmailField().setText(paciente.getEmail());

        new PacienteEditarController(paciente, editarPacienteForm, pacienteTable);
        editarPacienteForm.setVisible(true);
    }

    private void mostrarConfirmDialog(Paciente paciente, PacienteDetails detailsDialog) {
        int confirm = JOptionPane.showConfirmDialog(
                detailsDialog,
                "¿Está seguro de que desea eliminar este paciente?",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            eliminarPaciente(paciente, detailsDialog);
        }
    }

    private void eliminarPaciente(Paciente paciente, PacienteDetails detailsDialog) {
        pacienteService.deletePaciente(paciente);
        pacienteTable.actualizarTabla();
        JOptionPane.showMessageDialog(null, "Paciente eliminado exitosamente.");
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
