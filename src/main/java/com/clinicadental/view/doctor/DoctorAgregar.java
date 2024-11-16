package com.clinicadental.view.doctor;

import com.clinicadental.common.Constans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DoctorAgregar extends JFrame {

    private JTextField nombreField;
    private JTextField apellidosField;
    private JTextField dniField;
    private JTextField telefonoField;
    private JTextField direccionField;
    private JTextField codPostalField;
    private JTextField emailField;
    private JTextField numColegiadoField;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton retrocederButton;
    private JPanel mainPanel;

    // Asteriscos rojos para los campos validados
    private JLabel dniAsterisk;
    private JLabel telefonoAsterisk;
    private JLabel emailAsterisk;

    public DoctorAgregar() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Establecer el icono de la aplicación
        setIconImage(new ImageIcon(getClass().getResource(Constans.ICON_LOGO_IMAGE_PATH)).getImage());

        JPanel formPanel = new JPanel(new GridLayout(8, 3, 10, 10));  // Ajuste a 8 filas para incluir "NumColegiado"
        formPanel.setBorder(BorderFactory.createTitledBorder("Registrar Doctor"));

        // Añadir etiquetas y campos de texto
        formPanel.add(new JLabel("Nombre:"));
        nombreField = new JTextField(20);
        formPanel.add(nombreField);
        formPanel.add(new JLabel());

        formPanel.add(new JLabel("Apellidos:"));
        apellidosField = new JTextField(20);
        formPanel.add(apellidosField);
        formPanel.add(new JLabel());

        formPanel.add(new JLabel("DNI:"));
        dniField = new JTextField(20);
        formPanel.add(dniField);
        dniAsterisk = new JLabel("*");
        dniAsterisk.setForeground(Color.BLACK);
        formPanel.add(dniAsterisk);

        formPanel.add(new JLabel("Teléfono:"));
        telefonoField = new JTextField(20);
        formPanel.add(telefonoField);
        telefonoAsterisk = new JLabel("*");
        telefonoAsterisk.setForeground(Color.BLACK);
        formPanel.add(telefonoAsterisk);

        formPanel.add(new JLabel("Dirección:"));
        direccionField = new JTextField(20);
        formPanel.add(direccionField);
        formPanel.add(new JLabel());

        formPanel.add(new JLabel("Código Postal:"));
        codPostalField = new JTextField(20);
        formPanel.add(codPostalField);
        formPanel.add(new JLabel());

        formPanel.add(new JLabel("Email:"));
        emailField = new JTextField(20);
        formPanel.add(emailField);
        emailAsterisk = new JLabel("*");
        emailAsterisk.setForeground(Color.BLACK);
        formPanel.add(emailAsterisk);

        formPanel.add(new JLabel("Número de Colegiado:"));
        numColegiadoField = new JTextField(20);  // Inicialización del campo numColegiadoField
        formPanel.add(numColegiadoField);
        formPanel.add(new JLabel());

        JPanel buttonPanel = new JPanel();
        guardarButton = new JButton("Guardar");
        limpiarButton = new JButton("Limpiar");
        retrocederButton = new JButton("Retroceder");

        buttonPanel.add(guardarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(retrocederButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Registrar Doctor");
        setSize(500, 400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
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
        numColegiadoField.setText("");
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

    // Getter y setter
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

    public JTextField getNumColegiadoField() {
        return numColegiadoField;
    }

    public JLabel getDniAsterisk() {
        return dniAsterisk;
    }

    public JLabel getTelefonoAsterisk() {
        return telefonoAsterisk;
    }

    public JLabel getEmailAsterisk() {
        return emailAsterisk;
    }

}
