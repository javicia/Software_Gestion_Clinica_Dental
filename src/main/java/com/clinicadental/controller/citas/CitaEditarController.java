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
import com.clinicadental.utils.ValidatorUtil;
import com.clinicadental.view.citas.CitaEditar;
import com.clinicadental.view.citas.GestionCita;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class CitaEditarController {
    private ICitasService citaService;
    private IDoctorService doctorService;
    private IPacienteService pacienteService;
    private CitaEditar citaForm;
    private GestionCita citaTable;
    private Cita cita;

    public CitaEditarController(Cita cita, CitaEditar citaForm, GestionCita citaTable) {
        this.cita = cita;
        this.citaForm = citaForm;
        this.citaTable = citaTable;
        this.citaService = new CitasServiceImpl();
        this.doctorService = new DoctorServiceImpl();
        this.pacienteService = new PacienteServiceImpl();

        inicializarFormulario();
        this.citaForm.addGuardarButtonListener(new EditarCitaListener());
        this.citaForm.addLimpiarButtonListener(new LimpiarCamposListener());
        this.citaForm.addRetrocederButtonListener(new RetrocederListener());
    }

    private void inicializarFormulario() {
        citaForm.getFechaField().setText(DateUtils.formatFecha(cita.getFecha()));
        citaForm.getHoraField().setText(DateUtils.formatHora(cita.getHora()));

        // Selecciona el paciente en el JComboBox
        String nombreCompletoPaciente = cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellidos();
        citaForm.getPacienteField().setSelectedItem(nombreCompletoPaciente);

        // Selecciona el doctor en el JComboBox
        String nombreCompletoDoctor = cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellidos();
        citaForm.getDoctorField().setSelectedItem(nombreCompletoDoctor);

        // Inicializar el campo de motivo
        citaForm.getMotivoField().setText(cita.getMotivo());
    }

    // Listener para el botón Guardar
    class EditarCitaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String fechaTexto = citaForm.getFechaField().getText();
            String horaTexto = citaForm.getHoraField().getText();
            String pacienteTexto = (String) citaForm.getPacienteField().getSelectedItem();
            String doctorTexto = (String) citaForm.getDoctorField().getSelectedItem();
            String motivo = citaForm.getMotivoField().getText();

            resetFieldStyles();

            boolean isValid = true;

            Date fecha = ValidatorUtil.validarFecha(fechaTexto);
            if (fecha == null) {
                marcarCampoInvalido(citaForm.getFechaField(), citaForm.getFechaAsterisk());
                isValid = false;
            }

            Date hora = ValidatorUtil.validarHora(horaTexto);
            if (hora == null) {
                marcarCampoInvalido(citaForm.getHoraField(), citaForm.getHoraAsterisk());
                isValid = false;
            }

            Paciente pacienteSeleccionado = ValidatorUtil.validarPaciente(pacienteTexto, pacienteService);
            if (pacienteSeleccionado == null) {
                marcarCampoInvalido(citaForm.getPacienteField(), citaForm.getPacienteAsterisk());
                isValid = false;
                JOptionPane.showMessageDialog(citaForm, "Paciente no encontrado o existen múltiples pacientes con ese nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            }

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

            cita.setFecha(fecha);
            cita.setHora(hora);
            cita.setPaciente(pacienteSeleccionado);
            cita.setDoctor(doctorSeleccionado);
            cita.setMotivo(motivo);

            citaService.updateCita(cita);

            JOptionPane.showMessageDialog(citaForm, "Cita editada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            citaForm.limpiarCampos();
            citaTable.setCitasData(citaService.getAllCitas());
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
        resetFieldStyle(citaForm.getFechaField(), citaForm.getFechaAsterisk());
        resetFieldStyle(citaForm.getHoraField(), citaForm.getHoraAsterisk());
        resetFieldStyle(citaForm.getPacienteField(), citaForm.getPacienteAsterisk());
        resetFieldStyle(citaForm.getDoctorField(), citaForm.getDoctorAsterisk());
    }

    private void resetFieldStyle(JComponent field, JLabel asterisk) {
        field.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("ComboBox.border"));
        if (asterisk != null) {
            asterisk.setForeground(Color.BLACK);
        }
    }

    class LimpiarCamposListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            citaForm.limpiarCampos();
        }
    }

    class RetrocederListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            citaForm.dispose();
        }
    }
}
