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
    private final ICitasService citaService;
    private final IDoctorService doctorService;
    private final IPacienteService pacienteService;
    private final CitaEditar citaForm;
    private final GestionCita citaTable;
    private final Cita cita;

    public CitaEditarController(Cita cita, CitaEditar citaForm, GestionCita citaTable) {
        this.cita = cita;
        this.citaForm = citaForm;
        this.citaTable = citaTable;
        this.citaService = new CitasServiceImpl();
        this.doctorService = new DoctorServiceImpl();
        this.pacienteService = new PacienteServiceImpl();

        inicializarFormulario();
        this.citaForm.addGuardarButtonListener(new EditarCitaListener());
        this.citaForm.addLimpiarButtonListener(e -> citaForm.limpiarCampos());
        this.citaForm.addRetrocederButtonListener(e -> citaForm.dispose());
    }

    private void inicializarFormulario() {
        if (cita.getFecha() != null) {
            citaForm.getFechaField().setText(DateUtils.formatFecha(cita.getFecha()));
        }
        if (cita.getHora() != null) {
            citaForm.getHoraField().setText(DateUtils.formatHora(cita.getHora()));
        }

        if (cita.getPaciente() != null) {
            String nombreCompletoPaciente = cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellidos();
            citaForm.getPacienteField().setSelectedItem(nombreCompletoPaciente);
        }

        if (cita.getDoctor() != null) {
            String nombreCompletoDoctor = cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellidos();
            citaForm.getDoctorField().setSelectedItem(nombreCompletoDoctor);
        }

        citaForm.getMotivoField().setText(cita.getMotivo() != null ? cita.getMotivo() : "");
    }

    private class EditarCitaListener implements ActionListener {
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
                JOptionPane.showMessageDialog(citaForm, "Paciente no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            Doctor doctorSeleccionado = ValidatorUtil.validarDoctor(doctorTexto, doctorService);
            if (doctorSeleccionado == null) {
                marcarCampoInvalido(citaForm.getDoctorField(), citaForm.getDoctorAsterisk());
                isValid = false;
                JOptionPane.showMessageDialog(citaForm, "Doctor no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (!isValid) {
                JOptionPane.showMessageDialog(citaForm, "Corrige los campos marcados.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            cita.setFecha(fecha);
            cita.setHora(hora);
            cita.setPaciente(pacienteSeleccionado);
            cita.setDoctor(doctorSeleccionado);
            cita.setMotivo(motivo);

            citaService.updateCita(cita);

            JOptionPane.showMessageDialog(citaForm, "Cita editada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            citaForm.dispose();
            citaTable.setCitasData(citaService.getAllCitas());
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
        field.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));
        if (asterisk != null) {
            asterisk.setForeground(Color.BLACK);
        }
    }
}
