package com.clinicadental.controller.paciente;

import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.view.paciente.PacienteForm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PacienteAddEditController {
    private IPacienteService pacienteService;
    private PacienteForm pacienteForm;

    public PacienteAddEditController(PacienteForm pacienteForm) {
        this.pacienteForm = pacienteForm;
        this.pacienteService = new PacienteServiceImpl();  // Instancia de la implementación del servicio

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

            // Crear un nuevo objeto Paciente
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
            pacienteForm.mostrarMensaje("Paciente guardado exitosamente");

            // Limpiar los campos del formulario después de guardar
            pacienteForm.limpiarCampos();
        }
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
            // Aquí puedes implementar la lógica de retroceder, como volver a un menú principal o cerrar la ventana actual
            pacienteForm.dispose();  // Cerrar la ventana actual como ejemplo
        }
    }
}

