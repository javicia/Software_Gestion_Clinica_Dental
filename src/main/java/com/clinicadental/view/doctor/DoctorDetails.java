package com.clinicadental.view.doctor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DoctorDetails extends JFrame {

    private JPanel contentPane;
    private JLabel nombreLabel;
    private JLabel apellidosLabel;
    private JLabel dniLabel;
    private JLabel telefonoLabel;
    private JLabel direccionLabel;
    private JLabel codPostalLabel;
    private JLabel emailLabel;
    private JLabel numColegiadoLabel;
    private JButton buttonOK;
    private JButton editButton;
    private JButton deleteButton;

    public DoctorDetails(JFrame parent, String nombre, String apellidos, String dni, String telefono, String direccion, String codPostal, String email, Integer numColegiado) {
        super("Detalles del Doctor");

        // Configurar el tamaño del JFrame para que coincida con MainScreen
        setSize(800, 600);
        setLocationRelativeTo(parent);  // Centrar la ventana en la pantalla

        contentPane = new JPanel(new GridLayout(10, 2));  // Usar GridLayout para organizar los elementos

        // Inicializar etiquetas con los valores
        nombreLabel = new JLabel(nombre);
        apellidosLabel = new JLabel(apellidos);
        dniLabel = new JLabel(dni);
        telefonoLabel = new JLabel(telefono);
        direccionLabel = new JLabel(direccion);
        codPostalLabel = new JLabel(codPostal);
        emailLabel = new JLabel(email);

        // Verificar si numColegiado es null antes de asignarlo a la etiqueta
        numColegiadoLabel = new JLabel(numColegiado != null ? numColegiado.toString() : "");



        // Inicializar botones
        buttonOK = new JButton("OK");
        editButton = new JButton("Editar");
        deleteButton = new JButton("Eliminar");

        // Añadir componentes al panel
        contentPane.add(new JLabel("Nombre:"));
        contentPane.add(nombreLabel);
        contentPane.add(new JLabel("Apellidos:"));
        contentPane.add(apellidosLabel);
        contentPane.add(new JLabel("DNI:"));
        contentPane.add(dniLabel);
        contentPane.add(new JLabel("Teléfono:"));
        contentPane.add(telefonoLabel);
        contentPane.add(new JLabel("Dirección:"));
        contentPane.add(direccionLabel);
        contentPane.add(new JLabel("Código Postal:"));
        contentPane.add(codPostalLabel);
        contentPane.add(new JLabel("Email:"));
        contentPane.add(emailLabel);
        contentPane.add(new JLabel("Número de Colegiado:"));
        contentPane.add(numColegiadoLabel);

        // Añadir botones
        contentPane.add(buttonOK);
        contentPane.add(editButton);
        contentPane.add(deleteButton);

        setContentPane(contentPane);  // Establecer el panel como el contenido de la ventana

        // Acción para cerrar la ventana al presionar "OK"
        buttonOK.addActionListener(e -> dispose());
    }

    // Métodos para agregar listeners a los botones
    public void addEditListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    // Método para cerrar el JFrame
    public void cerrarDialogo() {
        this.dispose();
    }
}
