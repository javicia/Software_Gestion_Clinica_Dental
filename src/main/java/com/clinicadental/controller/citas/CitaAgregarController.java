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
import com.clinicadental.utils.ValidatorUtil;
import com.clinicadental.view.citas.CitaAgregar;
import com.clinicadental.view.citas.GestionCita;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class CitaAgregarController {
    private ICitasService citasService;
    private CitaAgregar citaForm;
    private GestionCita citaTable;

    public CitaAgregarController(CitaAgregar citaForm, GestionCita citaTable) {
        this.citaForm = citaForm;
        this.citaTable = citaTable;
        this.citasService = new CitasServiceImpl();

        // Configurar los eventos de los botones en el controlador
        this.citaForm.addGuardarButtonListener(new GuardarCitaListener());
        this.citaForm.addLimpiarButtonListener(new LimpiarCamposListener());
        this.citaForm.addRetrocederButtonListener(new RetrocederListener());
    }

    // Método dentro de CitaAgregarController
    class GuardarCitaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Date fecha = citaForm.getFechaSeleccionada();
            String horaTexto = citaForm.getHoraField().getText();
            String motivo = citaForm.getMotivoField().getText();
            String pacienteTexto = (String) citaForm.getPacienteField().getSelectedItem(); // Obtener paciente seleccionado
            String doctorTexto = (String) citaForm.getDoctorField().getSelectedItem();

            resetFieldStyles();
            boolean isValid = true;

            if (fecha == null) {
                citaForm.getFechaAsterisk().setForeground(Color.RED);
                isValid = false;
            }

            Date hora = ValidatorUtil.validarHora(horaTexto);
            if (hora == null) {
                marcarCampoInvalido(citaForm.getHoraField(), citaForm.getHoraAsterisk());
                isValid = false;
            }

            // Asumimos que `pacienteTexto` y `doctorTexto` son válidos, ya que solo pueden ser seleccionados de las listas disponibles

            if (!isValid) {
                JOptionPane.showMessageDialog(citaForm, "Por favor, corrige los campos marcados en rojo.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear y guardar la cita
            Cita cita = new Cita();
            cita.setFecha(fecha);
            cita.setHora(hora);
            cita.setMotivo(motivo);

            // Crear el objeto Paciente y establecer su nombre
            Paciente paciente = new Paciente();
            paciente.setNombre(pacienteTexto); // Usa el método setter para el nombre
            cita.setPaciente(paciente);

            // Crear el objeto Doctor y establecer su nombre
            Doctor doctor = new Doctor();
            doctor.setNombre(doctorTexto); // Usa el método setter para el nombre
            cita.setDoctor(doctor);

            citasService.saveCita(cita);

            citaTable.actualizarTabla();
            JOptionPane.showMessageDialog(citaForm, "Cita guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            citaForm.limpiarCampos();
            citaTable.setCitasData(citasService.getAllCitas());

            citaForm.dispose();
        }
    }

    private void marcarCampoInvalido(JComponent field, JLabel asterisk) {
        field.setBorder(new LineBorder(Color.RED, 2));
        if (asterisk != null) {
            asterisk.setForeground(Color.RED);
        }
    }

    private void resetFieldStyles() {
        resetFieldStyle(citaForm.getHoraField(), citaForm.getHoraAsterisk());
        citaForm.getFechaAsterisk().setForeground(Color.BLACK);
    }

    private void resetFieldStyle(JComponent field, JLabel asterisk) {
        field.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        if (asterisk != null) {
            asterisk.setForeground(Color.BLACK);
        }
    }

    // Listener para el botón Limpiar
    class LimpiarCamposListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            citaForm.limpiarCampos();
        }
    }

    // Listener para el botón Retroceder
    class RetrocederListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            citaForm.dispose();
        }
    }
}
