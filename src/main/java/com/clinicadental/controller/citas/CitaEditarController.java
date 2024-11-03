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
import com.clinicadental.view.doctor.DoctorEditar;
import com.clinicadental.view.doctor.GestionDoctor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CitaEditarController {
    private ICitasService citaService;
    private IDoctorService doctorService;
    private IPacienteService pacienteService;
    private CitaEditar citaForm;
    private GestionCita citaTable;
    private Cita cita;  // Objeto Cita a editar

    public CitaEditarController(Cita cita, CitaEditar citaForm, GestionCita citaTable) {
        this.cita = cita;
        this.citaForm = citaForm;
        this.citaTable = citaTable;
        this.citaService = new CitasServiceImpl();
        this.doctorService = new DoctorServiceImpl();
        this.pacienteService = new PacienteServiceImpl();

        // Inicializar el formulario con los datos del cita
        inicializarFormulario();

        // Configurar los eventos de los botones en el controlador
        this.citaForm.addGuardarButtonListener(new EditarCitaListener());
        this.citaForm.addLimpiarButtonListener(new LimpiarCamposListener());
        this.citaForm.addRetrocederButtonListener(new RetrocederListener());
    }

    // Método para inicializar el formulario con los datos del cita existente
    private void inicializarFormulario() {

        citaForm.getFechaField().setText(DateUtils.formatFecha(cita.getFecha()));
        citaForm.getHoraField().setText(DateUtils.formatHora(cita.getHora()));

        // Inicializar campos de paciente con nombre y apellidos
        String nombreCompletoPaciente = cita.getPaciente().getNombre() + " " + cita.getPaciente().getApellidos();
        citaForm.getPacienteField().setText(nombreCompletoPaciente);

        // Inicializar campos de doctor con nombre y apellidos
        String nombreCompletoDoctor = cita.getDoctor().getNombre() + " " + cita.getDoctor().getApellidos();
        citaForm.getDoctorField().setText(nombreCompletoDoctor);

        // Inicializar el campo de motivo
        citaForm.getMotivoField().setText(cita.getMotivo());
    }

    // Listener para el botón Guardar
    class EditarCitaListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Obtener los datos del formulario
            String fechaTexto = citaForm.getFechaField().getText();
            String horaTexto = citaForm.getHoraField().getText();
            String pacienteTexto = citaForm.getPacienteField().getText();
            String doctorTexto = citaForm.getDoctorField().getText();
            String motivo = citaForm.getMotivoField().getText();

            // Restablecer estilos por defecto
            resetFieldStyles();

            // Realizar validaciones
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

            // Actualizar el objeto Cita con los datos editados
            cita.setFecha(fecha);
            cita.setHora(hora);
            cita.setPaciente(pacienteSeleccionado);
            cita.setDoctor(doctorSeleccionado);
            cita.setMotivo(motivo);

            // Actualizar el cita usando el servicio
            citaService.updateCita(cita);

            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(citaForm, "Cita editada exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar los campos del formulario después de guardar
            citaForm.limpiarCampos();

            // Actualizar la tabla de citaes
            citaTable.setCitasData(citaService.getAllCitas());

            // Cerrar el formulario
            citaForm.dispose();
        }
    }

    // Método para marcar un campo como inválido (borde rojo y mostrar asterisco rojo)
    private void marcarCampoInvalido(JTextField field, JLabel asterisk) {
        field.setBorder(new LineBorder(Color.RED, 2));  // Cambiar el borde a rojo
        if (asterisk != null) {
            asterisk.setForeground(Color.RED);  // Cambiar el color del asterisco a rojo si no es null
        }
    }

    // Método para validar el DNI (8 dígitos seguidos de una letra)
    private boolean validarDNI(String dni) {
        String dniPattern = "\\d{8}[A-Za-z]";  // 8 dígitos + 1 letra
        Pattern pattern = Pattern.compile(dniPattern);
        Matcher matcher = pattern.matcher(dni);
        return matcher.matches();
    }

    // Método para validar el teléfono (solo números, longitud entre 9-12 dígitos)
    private boolean validarTelefono(String telefono) {
        String telefonoPattern = "\\d{9,12}";  // Solo dígitos, longitud entre 9 y 12
        Pattern pattern = Pattern.compile(telefonoPattern);
        Matcher matcher = pattern.matcher(telefono);
        return matcher.matches();
    }

    // Método para validar el email (formato estándar)
    private boolean validarEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";  // Patrón estándar para email
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Restablecer el estilo de todos los campos
    private void resetFieldStyles() {
        resetFieldStyle(citaForm.getFechaField(), citaForm.getFechaAsterisk());
        resetFieldStyle(citaForm.getHoraField(), citaForm.getHoraAsterisk());
        resetFieldStyle(citaForm.getPacienteField(), citaForm.getPacienteAsterisk());
        resetFieldStyle(citaForm.getDoctorField(), citaForm.getDoctorAsterisk());
    }

    // Método para restablecer el estilo del campo (borde por defecto y quitar asterisco rojo)
    private void resetFieldStyle(JTextField field, JLabel asterisk) {
        field.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));  // Restablecer el borde predeterminado
        if (asterisk != null) {
            asterisk.setForeground(Color.BLACK);  // Poner el asterisco en negro o invisible si no es null
        }
    }

    // Listener para el botón Limpiar
    class LimpiarCamposListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            citaForm.limpiarCampos();  // Llama al método de limpiar los campos
        }
    }

    // Listener para el botón Retroceder
    class RetrocederListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            citaForm.dispose();  // Cerrar la ventana actual
        }
    }
}
