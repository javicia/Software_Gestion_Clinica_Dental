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
import com.clinicadental.view.citas.CitaAgregar;
import com.clinicadental.view.citas.GestionCita;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

public class CitaAgregarController {
    private ICitasService citasService;
    private CitaAgregar citaForm;
    private GestionCita citaTable;
    private IDoctorService doctorService;
    private IPacienteService pacienteService;

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

    // Método dentro de CitaAgregarController
    class GuardarCitaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Date fecha = citaForm.getFechaSeleccionada();
            String horaTexto = citaForm.getHoraField().getText();
            String motivo = citaForm.getMotivoField().getText();

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

            if (!isValid) {
                JOptionPane.showMessageDialog(citaForm, "Por favor, corrige los campos marcados en rojo.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Obtener el objeto seleccionado del JComboBox en lugar de buscar por nombre
            Paciente paciente = (Paciente) citaForm.getPacienteField().getSelectedItem();
            Doctor doctor = (Doctor) citaForm.getDoctorField().getSelectedItem();

            if (doctor == null || paciente == null) {
                JOptionPane.showMessageDialog(citaForm, "Seleccione un doctor y un paciente válidos.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear y guardar la cita con el doctor y paciente seleccionados
            Cita cita = new Cita();
            cita.setFecha(fecha);
            cita.setHora(hora);
            cita.setMotivo(motivo);
            cita.setDoctor(doctor);
            cita.setPaciente(paciente);

            citasService.saveCita(cita);
            citaTable.actualizarTabla();

            JOptionPane.showMessageDialog(citaForm, "Cita guardada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            citaForm.limpiarCampos();
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
