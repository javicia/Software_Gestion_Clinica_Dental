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
    private IDoctorService doctorService;
    private IPacienteService pacienteService;
    private CitaAgregar citaForm;
    private GestionCita citaTable;

    public CitaAgregarController(CitaAgregar citaForm, GestionCita citaTable) {
        this.citaForm = citaForm;
        this.citaTable = citaTable;
        this.citasService = new CitasServiceImpl();
        this.doctorService = new DoctorServiceImpl();
        this.pacienteService = new PacienteServiceImpl();

        // Configurar los eventos de los botones en el controlador
        this.citaForm.addGuardarButtonListener(new GuardarCitaListener());
        this.citaForm.addLimpiarButtonListener(new LimpiarCamposListener());
        this.citaForm.addRetrocederButtonListener(new RetrocederListener());
    }

    // Listener para el botón Guardar
    class GuardarCitaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Obtener los datos del formulario
            String fechaTexto = citaForm.getFechaField().getText();
            String horaTexto = citaForm.getHoraField().getText();
            String motivo = citaForm.getMotivoField().getText();
            String pacienteTexto = citaForm.getPacienteField().getText();
            String doctorTexto = citaForm.getDoctorField().getText();

            // Restablecer estilos por defecto
            resetFieldStyles();
            boolean isValid = true;

            // Validar fecha
            Date fecha = ValidatorUtil.validarFecha(fechaTexto);
            if (fecha == null) {
                marcarCampoInvalido(citaForm.getFechaField(), citaForm.getFechaAsterisk());
                isValid = false;
            }

            // Validar hora
            Date hora = ValidatorUtil.validarHora(horaTexto);
            if (hora == null) {
                marcarCampoInvalido(citaForm.getHoraField(), citaForm.getHoraAsterisk());
                isValid = false;
            }

            // Validar que el paciente exista
            Paciente pacienteSeleccionado = ValidatorUtil.validarPaciente(pacienteTexto, pacienteService);
            if (pacienteSeleccionado == null) {
                marcarCampoInvalido(citaForm.getPacienteField(), citaForm.getPacienteAsterisk());
                isValid = false;
                JOptionPane.showMessageDialog(citaForm, "Paciente no encontrado o existen múltiples pacientes con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            // Validar que el doctor exista
            Doctor doctorSeleccionado = ValidatorUtil.validarDoctor(doctorTexto, doctorService);
            if (doctorSeleccionado == null) {
                marcarCampoInvalido(citaForm.getDoctorField(), citaForm.getDoctorAsterisk());
                isValid = false;
                JOptionPane.showMessageDialog(citaForm, "Doctor no encontrado o existen múltiples doctores con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (!isValid) {
                JOptionPane.showMessageDialog(citaForm, "Por favor, corrige los campos marcados en rojo.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear un nuevo objeto Cita
            Cita cita = new Cita();
            cita.setFecha(fecha);
            cita.setHora(hora);
            cita.setMotivo(motivo);
            cita.setPaciente(pacienteSeleccionado);
            cita.setDoctor(doctorSeleccionado);

            // Guardar la cita usando el servicio
            citasService.saveCita(cita);

            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(citaForm, "Cita guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar los campos del formulario después de guardar
            citaForm.limpiarCampos();

            // Actualizar la tabla de citas
            citaTable.setCitasData(citasService.getAllCitas());

            // Cerrar el formulario
            citaForm.dispose();
        }
    }

    // Métodos auxiliares para formateo y marcado de campos (sin cambios)
    private void marcarCampoInvalido(JTextField field, JLabel asterisk) {
        field.setBorder(new LineBorder(Color.RED, 2));
        if (asterisk != null) {
            asterisk.setForeground(Color.RED);
        }
    }

    private void resetFieldStyles() {
        resetFieldStyle(citaForm.getFechaField(), citaForm.getFechaAsterisk());
        resetFieldStyle(citaForm.getHoraField(), citaForm.getHoraAsterisk());
        resetFieldStyle(citaForm.getPacienteField(), citaForm.getPacienteAsterisk());
        resetFieldStyle(citaForm.getDoctorField(), citaForm.getDoctorAsterisk());
    }

    private void resetFieldStyle(JTextField field, JLabel asterisk) {
        field.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        if (asterisk != null) {
            asterisk.setForeground(Color.BLACK);
        }
    }

    // Listener para el botón Limpiar (sin cambios)
    class LimpiarCamposListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            citaForm.limpiarCampos();
        }
    }

    // Listener para el botón Retroceder (sin cambios)
    class RetrocederListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            citaForm.dispose();
        }
    }
}
