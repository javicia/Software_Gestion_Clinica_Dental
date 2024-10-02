package com.clinicadental.view.paciente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PacienteForm extends JFrame {
    private JTextField nombreField;
    private JTextField apellidosField;
    private JTextField dniField;
    private JTextField telefonoField;
    private JTextField direccionField;
    private JTextField codPostalField;
    private JTextField emailField;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton retrocederButton;
    private JPanel mainPanel;

    public PacienteForm() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));  // Usar BorderLayout para organizar mejor los elementos
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));  // Añadir padding

        // Panel para el formulario con GridLayout
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));  // 7 filas para los campos
        formPanel.setBorder(BorderFactory.createTitledBorder("Registrar Paciente"));

        // Campos del formulario
        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField(20);
        formPanel.add(nombreField);

        formPanel.add(new JLabel("Apellidos:"));
        apellidosField = new JTextField(20);
        formPanel.add(apellidosField);

        formPanel.add(new JLabel("DNI:"));
        dniField = new JTextField(20);
        formPanel.add(dniField);

        formPanel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField(20);
        formPanel.add(telefonoField);

        formPanel.add(new JLabel("Dirección:"));
        direccionField = new JTextField(20);
        formPanel.add(direccionField);

        formPanel.add(new JLabel("Código Postal:"));
        codPostalField = new JTextField(20);
        formPanel.add(codPostalField);

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        formPanel.add(emailField);

        // Panel para los botones
        JPanel buttonPanel = new JPanel();
        guardarButton = new JButton("Guardar");
        limpiarButton = new JButton("Limpiar");
        retrocederButton = new JButton("Retroceder");

        guardarButton.setPreferredSize(new Dimension(120, 30));  // Tamaño del botón
        limpiarButton.setPreferredSize(new Dimension(120, 30));
        retrocederButton.setPreferredSize(new Dimension(120, 30));

        buttonPanel.add(guardarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(retrocederButton);

        // Añadir paneles al mainPanel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Configuración de la ventana
        setContentPane(mainPanel);
        setTitle("Registrar Paciente");
        setSize(500, 400);  // Tamaño ajustado
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centrar la ventana
    }

    // Getters para acceder a los campos desde el controlador
    public JTextField getNombreField() {
        return nombreField;
    }

    public JTextField getApellidosField() {
        return apellidosField;
    }

    public JTextField getDniField() {
        return dniField;
    }

    public JTextField getTelefonoField() {
        return telefonoField;
    }

    public JTextField getDireccionField() {
        return direccionField;
    }

    public JTextField getCodPostalField() {
        return codPostalField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    // Métodos para agregar los listeners de los botones
    public void addGuardarButtonListener(ActionListener listener) {
        guardarButton.addActionListener(listener);
    }

    public void addLimpiarButtonListener(ActionListener listener) {
        limpiarButton.addActionListener(listener);
    }

    public void addRetrocederButtonListener(ActionListener listener) {
        retrocederButton.addActionListener(listener);
    }

    // Método para mostrar un mensaje en la ventana
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    // Método para limpiar los campos del formulario
    public void limpiarCampos() {
        nombreField.setText("");
        apellidosField.setText("");
        dniField.setText("");
        telefonoField.setText("");
        direccionField.setText("");
        codPostalField.setText("");
        emailField.setText("");
    }
}
