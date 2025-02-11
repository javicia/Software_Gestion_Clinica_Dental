package com.clinicadental.controller.doctor;

import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.impl.DoctorServiceImpl;
import com.clinicadental.utils.ValidatorUtil;
import com.clinicadental.view.doctor.DoctorEditar;
import com.clinicadental.view.doctor.GestionDoctor;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorEditarController {
    private IDoctorService doctorService;
    private DoctorEditar doctorForm;
    private GestionDoctor doctorTable;
    private Doctor doctor;  // Objeto Doctor a editar

    public DoctorEditarController(Doctor doctor, DoctorEditar doctorForm, GestionDoctor doctorTable) {
        this.doctor = doctor;
        this.doctorForm = doctorForm;
        this.doctorTable = doctorTable;
        this.doctorService = new DoctorServiceImpl();

        // Inicializar el formulario con los datos del doctor
        inicializarFormulario();

        // Configurar los eventos de los botones en el controlador
        this.doctorForm.addGuardarButtonListener(new EditarDoctorListener());
        this.doctorForm.addLimpiarButtonListener(new LimpiarCamposListener());
        this.doctorForm.addRetrocederButtonListener(new RetrocederListener());
    }

    // Método para inicializar el formulario con los datos del doctor existente
    private void inicializarFormulario() {
        doctorForm.getNombreField().setText(doctor.getNombre());
        doctorForm.getApellidosField().setText(doctor.getApellidos());
        doctorForm.getDniField().setText(doctor.getDni());
        doctorForm.getTelefonoField().setText(doctor.getTelefono());
        doctorForm.getDireccionField().setText(doctor.getDireccion());
        doctorForm.getCodPostalField().setText(String.valueOf(doctor.getCodPostal()));
        doctorForm.getEmailField().setText(doctor.getEmail());
        doctorForm.getNumColegiadoField().setText(String.valueOf(doctor.getNumColegiado()));
    }

    // Listener para el botón Guardar
    class EditarDoctorListener implements ActionListener {
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
            String numColegiadoText = doctorForm.getNumColegiadoField().getText();

            // Restablecer estilos por defecto
            resetFieldStyles();

            // Realizar validaciones
            boolean isValid = true;

            // Validar DNI
            if (!ValidatorUtil.validarDNI(dni)) {
                marcarCampoInvalido(doctorForm.getDniField(), doctorForm.getDniAsterisk());
                isValid = false;
            }

            // Validar teléfono
            if (!ValidatorUtil.validarTelefono(telefono)) {
                marcarCampoInvalido(doctorForm.getTelefonoField(), doctorForm.getTelefonoAsterisk());
                isValid = false;
            }

            // Validar email
            if (!ValidatorUtil.validarEmail(email)) {
                marcarCampoInvalido(doctorForm.getEmailField(), doctorForm.getEmailAsterisk());
                isValid = false;
            }

            // Validar número de colegiado
            if (!ValidatorUtil.validarNumColegiado(numColegiadoText)) {
                marcarCampoInvalido(doctorForm.getNumColegiadoField(), null);
                JOptionPane.showMessageDialog(doctorForm, "Número de colegiado no válido o vacío.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                isValid = false;
            }

            if (!isValid) {
                JOptionPane.showMessageDialog(doctorForm, "Por favor, corrige los campos marcados en rojo.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Actualizar el objeto Doctor con los datos editados
            doctor.setNombre(nombre);
            doctor.setApellidos(apellidos);
            doctor.setDni(dni);
            doctor.setTelefono(telefono);
            doctor.setDireccion(direccion);
            doctor.setCodPostal(Integer.parseInt(codPostal));
            doctor.setEmail(email);
            doctor.setNumColegiado(Integer.parseInt(numColegiadoText)); // Asignar el número de colegiado

            // Actualizar el doctor usando el servicio
            doctorService.updateDoctor(doctor);

            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(doctorForm, "Doctor editado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar los campos del formulario después de guardar
            doctorForm.limpiarCampos();

            // Actualizar la tabla de doctores
            doctorTable.setDoctoresData(doctorService.getAllDoctor());

            // Cerrar el formulario
            doctorForm.dispose();
        }
    }

    // Método para marcar un campo como inválido (borde rojo y mostrar asterisco rojo)
    private void marcarCampoInvalido(JTextField field, JLabel asterisk) {
        field.setBorder(new LineBorder(Color.RED, 2));  // Cambiar el borde a rojo
        if (asterisk != null) {
            asterisk.setForeground(Color.RED);  // Cambiar el color del asterisco a rojo si no es null
        }
    }

    // Restablecer el estilo de todos los campos
    private void resetFieldStyles() {
        resetFieldStyle(doctorForm.getDniField(), doctorForm.getDniAsterisk());
        resetFieldStyle(doctorForm.getTelefonoField(), doctorForm.getTelefonoAsterisk());
        resetFieldStyle(doctorForm.getEmailField(), doctorForm.getEmailAsterisk());
        resetFieldStyle(doctorForm.getNumColegiadoField(), null);
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
