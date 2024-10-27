package com.clinicadental.controller.paciente;

import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.view.paciente.PacienteAgregar;
import com.clinicadental.view.paciente.GestionPaciente;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacienteAgregarController {
    private IPacienteService pacienteService;
    private PacienteAgregar pacienteForm;
    private GestionPaciente pacienteTable;

    public PacienteAgregarController(PacienteAgregar pacienteForm, GestionPaciente pacienteTable) {
        this.pacienteForm = pacienteForm;
        this.pacienteTable = pacienteTable;
        this.pacienteService = new PacienteServiceImpl();

        // Configurar los eventos de los botones en el controlador
        this.pacienteForm.addGuardarButtonListener(new GuardarPacienteListener());
        this.pacienteForm.addLimpiarButtonListener(new LimpiarCamposListener());
        this.pacienteForm.addRetrocederButtonListener(new RetrocederListener());
    }

    // Listener para el botón Guardar
    class GuardarPacienteListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Obtener los datos del formulario
            String nombre = pacienteForm.getNombreField().getText();
            String apellidos = pacienteForm.getApellidosField().getText();
            String dni = pacienteForm.getDniField().getText();
            String telefono = pacienteForm.getTelefonoField().getText();
            String direccion = pacienteForm.getDireccionField().getText();
            String codPostal = pacienteForm.getCodPostalField().getText();
            String email = pacienteForm.getEmailField().getText();

            // Restablecer estilos por defecto
            resetFieldStyles();

            // Realizar validaciones
            boolean isValid = true;

            // Validar DNI (8 dígitos + letra)
            if (!validarDNI(dni)) {
                marcarCampoInvalido(pacienteForm.getDniField(), pacienteForm.getDniAsterisk());
                isValid = false;
            }

            // Validar teléfono (solo dígitos, longitud entre 9-12)
            if (!validarTelefono(telefono)) {
                marcarCampoInvalido(pacienteForm.getTelefonoField(), pacienteForm.getTelefonoAsterisk());
                isValid = false;
            }

            // Validar email (formato estándar de correo electrónico)
            if (!validarEmail(email)) {
                marcarCampoInvalido(pacienteForm.getEmailField(), pacienteForm.getEmailAsterisk());
                isValid = false;
            }

            // Si algún campo es inválido, mostrar una alerta
            if (!isValid) {
                JOptionPane.showMessageDialog(pacienteForm, "Por favor, corrige los campos marcados en rojo.", "Error de validación", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Crear un nuevo objeto Paciente si los datos son válidos
            Paciente paciente = new Paciente();
            paciente.setNombre(nombre);
            paciente.setApellidos(apellidos);
            paciente.setDni(dni);
            paciente.setTelefono(telefono);
            paciente.setDireccion(direccion);
            paciente.setCodPostal(Integer.parseInt(codPostal));
            paciente.setEmail(email);

            // Guardar el paciente usando el servicio
            pacienteService.savePaciente(paciente);

            // Mostrar mensaje de confirmación
            JOptionPane.showMessageDialog(pacienteForm, "Paciente guardado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Limpiar los campos del formulario después de guardar
            pacienteForm.limpiarCampos();

            // Actualizar la tabla de pacientes
            pacienteTable.setPacientesData(pacienteService.obtenerTodos());

            // Cerrar el formulario
            pacienteForm.dispose();
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
        resetFieldStyle(pacienteForm.getDniField(), pacienteForm.getDniAsterisk());
        resetFieldStyle(pacienteForm.getTelefonoField(), pacienteForm.getTelefonoAsterisk());
        resetFieldStyle(pacienteForm.getEmailField(), pacienteForm.getEmailAsterisk());
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
            pacienteForm.limpiarCampos();  // Llama al método de limpiar los campos
        }
    }

    // Listener para el botón Retroceder
    class RetrocederListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            pacienteForm.dispose();  // Cerrar la ventana actual
        }
    }
}
