package com.clinicadental.controller.doctor;

import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.DoctorServiceImpl;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.view.doctor.GestionDoctor;
import com.clinicadental.view.paciente.GestionPaciente;
import com.clinicadental.view.paciente.PacienteAgregar;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorAgregarController {
    private IDoctorService doctorService;
    private DoctorAgregar doctorForm;
    private GestionDoctor doctorTable;

    public DoctorAgregarController(DoctorAgregar doctorForm, GestionDoctor doctorTable) {
        this.doctorForm = doctorForm;
        this.doctorTable = doctorTable;
        this.doctorService = new DoctorServiceImpl();

        // Configurar los eventos de los botones en el controlador
        this.doctorForm.addGuardarButtonListener(new GuardarDoctorListener());
        this.doctorForm.addLimpiarButtonListener(new LimpiarCamposListener());
        this.doctorForm.addRetrocederButtonListener(new RetrocederListener());
    }

    // Listener para el botón Guardar
    class GuardarDoctorListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Obtener los datos del formulario
            String nombre = doctorForm.getNombreField().getText();
            String apellidos = doctorForm.getApellidosField().getText();
            String dni = doctorForm.getDniField().getText();
            String telefono = doctorForm.getTelefonoField().getText();
            String direccion = doctorForm.getDireccionField().getText();
            String codPostal = doctorForm.getCodPostalField().getText();
            String email = doctorForm.getEmailField().getText();
            String numColegiado = doctorForm.getNumColegiado().getText();

            // Restablecer estilos por defecto
            resetFieldStyles();

            // Realizar validaciones
            boolean isValid = true;

            // Validar DNI (8 dígitos + letra)
            if (!validarDNI(dni)) {
                marcarCampoInvalido(doctorForm.getDniField(), doctorForm.getDniAsterisk());
                isValid = false;
            }

            // Validar teléfono (solo dígitos, longitud entre 9-12)
            if (!validarTelefono(telefono)) {
                marcarCampoInvalido(doctorForm.getTelefonoField(), doctorForm.getTelefonoAsterisk());
                isValid = false;
            }

            // Validar email (formato estándar de correo electrónico)
            if (!validarEmail(email)) {
                marcarCampoInvalido(doctorForm.getEmailField(), doctorForm.getEmailAsterisk());
                isValid = false;
            }

            // Si algún campo es inválido, mostrar una alerta
            if (!isValid) {
                JOptionPane.showMessageDialog(doctorForm, "Por favor, corrige los campos marcados en rojo.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear un nuevo objeto Paciente si los datos son válidos
            Doctor doctor = new Doctor();
            doctor.setNombre(nombre);
            doctor.setApellidos(apellidos);
            doctor.setDni(dni);
            doctor.setTelefono(telefono);
            doctor.setDireccion(direccion);
            doctor.setCodPostal(Integer.parseInt(codPostal));
            doctor.setEmail(email);
            doctor.getNumColegiado(numColegiado);

            // Guardar el paciente usando el servicio
            doctorService.saveDoctor(doctor);

            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(doctorForm, "Doctor guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar los campos del formulario después de guardar
            doctorForm.limpiarCampos();

            // Actualizar la tabla de pacientes
            doctorTable.setDoctoresData(doctorService.getAllDoctor());

            // Cerrar el formulario
            doctorForm.dispose();
        }
    }

    // Método para marcar un campo como inválido (borde rojo y mostrar asterisco rojo)
    private void marcarCampoInvalido(JTextField field, JLabel asterisk) {
        field.setBorder(new LineBorder(Color.RED, 2));  // Cambiar el borde a rojo
        asterisk.setForeground(Color.RED);  // Cambiar el color del asterisco a rojo
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
        resetFieldStyle(doctorForm.getDniField(), doctorForm.getDniAsterisk());
        resetFieldStyle(doctorForm.getTelefonoField(), doctorForm.getTelefonoAsterisk());
        resetFieldStyle(doctorForm.getEmailField(), doctorForm.getEmailAsterisk());
    }

    // Método para restablecer el estilo del campo (borde por defecto y quitar asterisco rojo)
    private void resetFieldStyle(JTextField field, JLabel asterisk) {
        field.setBorder(UIManager.getLookAndFeel().getDefaults().getBorder("TextField.border"));  // Restablecer el borde predeterminado
        asterisk.setForeground(Color.BLACK);  // Poner el asterisco en negro o invisible
    }

    // Listener para el botón Limpiar
    class LimpiarCamposListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doctorForm.limpiarCampos();  // Llama al método de limpiar los campos
        }
    }

    // Listener para el botón Retroceder
    class RetrocederListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doctorForm.dispose();  // Cerrar la ventana actual
        }
    }
}
