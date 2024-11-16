package com.clinicadental.view.paciente;

import com.clinicadental.common.Constans;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PacienteAgregar extends JFrame {
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

    // Asteriscos rojos para los campos validados
    private JLabel dniAsterisk;
    private JLabel telefonoAsterisk;
    private JLabel emailAsterisk;

    public PacienteAgregar() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;

        // Establecer el icono de la aplicación
        setIconImage(new ImageIcon(getClass().getResource(Constans.ICON_LOGO_IMAGE_PATH)).getImage());

        // Formato de campos de texto
        Font smallFont = new Font("Arial", Font.PLAIN, 12);

        // Añadir etiquetas y campos de texto con GridBagLayout
        nombreField = new JTextField(20);
        apellidosField = new JTextField(20);
        dniField = new JTextField(20);
        telefonoField = new JTextField(20);
        direccionField = new JTextField(20);
        codPostalField = new JTextField(20);
        emailField = new JTextField(20);

        dniAsterisk = crearAsterisco();
        telefonoAsterisk = crearAsterisco();
        emailAsterisk = crearAsterisco();

        agregarCampo(formPanel, gbc, 0, "Nombre:", nombreField, null, smallFont);
        agregarCampo(formPanel, gbc, 1, "Apellidos:", apellidosField, null, smallFont);
        agregarCampo(formPanel, gbc, 2, "DNI:", dniField, dniAsterisk, smallFont);
        agregarCampo(formPanel, gbc, 3, "Teléfono:", telefonoField, telefonoAsterisk, smallFont);
        agregarCampo(formPanel, gbc, 4, "Dirección:", direccionField, null, smallFont);
        agregarCampo(formPanel, gbc, 5, "Código Postal:", codPostalField, null, smallFont);
        agregarCampo(formPanel, gbc, 6, "Email:", emailField, emailAsterisk, smallFont);

        // Panel de botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        guardarButton = new JButton("Guardar");
        limpiarButton = new JButton("Limpiar");
        retrocederButton = new JButton("Retroceder");

        buttonPanel.add(guardarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(retrocederButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Registrar Paciente");
        setSize(600, 400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void agregarCampo(JPanel panel, GridBagConstraints gbc, int y, String etiqueta, JTextField campo, JLabel asterisco, Font font) {
        gbc.gridx = 0;
        gbc.gridy = y;
        panel.add(new JLabel(etiqueta), gbc);

        gbc.gridx = 1;
        campo.setFont(font);
        campo.setPreferredSize(new Dimension(200, 25));
        campo.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panel.add(campo, gbc);

        if (asterisco != null) {
            gbc.gridx = 2;
            panel.add(asterisco, gbc);
        }
    }

    private JLabel crearAsterisco() {
        JLabel asterisk = new JLabel("*");
        asterisk.setForeground(Color.RED);
        return asterisk;
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

    // Getters para acceder a los campos desde el controlador
    public JTextField getNombreField() { return nombreField; }
    public JTextField getApellidosField() { return apellidosField; }
    public JTextField getDniField() { return dniField; }
    public JTextField getTelefonoField() { return telefonoField; }
    public JTextField getDireccionField() { return direccionField; }
    public JTextField getCodPostalField() { return codPostalField; }
    public JTextField getEmailField() { return emailField; }
    public JLabel getDniAsterisk() { return dniAsterisk; }
    public JLabel getTelefonoAsterisk() { return telefonoAsterisk; }
    public JLabel getEmailAsterisk() { return emailAsterisk; }
}
