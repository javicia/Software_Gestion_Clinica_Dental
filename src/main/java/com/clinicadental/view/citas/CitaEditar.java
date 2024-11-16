package com.clinicadental.view.citas;

import com.clinicadental.common.Constans;
import com.clinicadental.utils.CalendarUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class CitaEditar extends JFrame {
    private JTextField fechaField;
    private JTextField horaField;
    private JComboBox<String> pacienteField;
    private JComboBox<String> doctorField;
    private JTextArea motivoField;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton retrocederButton;

    private JLabel fechaAsterisk;
    private JLabel horaAsterisk;
    private JLabel pacienteAsterisk;
    private JLabel doctorAsterisk;

    public CitaEditar(List<String> pacientes, List<String> doctores) {
        setTitle("Editar Cita");
        setIconImage(new ImageIcon(Objects.requireNonNull(getClass().getResource(Constans.ICON_LOGO_IMAGE_PATH))).getImage());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Editar Cita"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Configuración de campos y etiquetas
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Fecha:"), gbc);

        fechaField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(fechaField, gbc);

        fechaAsterisk = new JLabel("*");
        fechaAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        formPanel.add(fechaAsterisk, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Hora:"), gbc);

        horaField = new JTextField(20);
        gbc.gridx = 1;
        formPanel.add(horaField, gbc);

        horaAsterisk = new JLabel("*");
        horaAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        formPanel.add(horaAsterisk, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Paciente:"), gbc);

        pacienteField = new JComboBox<>(pacientes.toArray(new String[0]));
        gbc.gridx = 1;
        formPanel.add(pacienteField, gbc);

        pacienteAsterisk = new JLabel("*");
        pacienteAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        formPanel.add(pacienteAsterisk, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Doctor:"), gbc);

        doctorField = new JComboBox<>(doctores.toArray(new String[0]));
        gbc.gridx = 1;
        formPanel.add(doctorField, gbc);

        doctorAsterisk = new JLabel("*");
        doctorAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        formPanel.add(doctorAsterisk, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Motivo:"), gbc);

        motivoField = new JTextArea(5, 20);
        motivoField.setLineWrap(true);
        motivoField.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(motivoField);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(scrollPane, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        guardarButton = new JButton("Guardar");
        limpiarButton = new JButton("Limpiar");
        retrocederButton = new JButton("Volver");
        buttonPanel.add(guardarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(retrocederButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    public JTextField getFechaField() {
        return fechaField;
    }

    public JTextField getHoraField() {
        return horaField;
    }

    public JComboBox<String> getPacienteField() {
        return pacienteField;
    }

    public JComboBox<String> getDoctorField() {
        return doctorField;
    }

    public JTextArea getMotivoField() {
        return motivoField;
    }

    public JLabel getFechaAsterisk() {
        return fechaAsterisk;
    }

    public JLabel getHoraAsterisk() {
        return horaAsterisk;
    }

    public JLabel getPacienteAsterisk() {
        return pacienteAsterisk;
    }

    public JLabel getDoctorAsterisk() {
        return doctorAsterisk;
    }

    public void addGuardarButtonListener(ActionListener listener) {
        guardarButton.addActionListener(listener);
    }

    public void addLimpiarButtonListener(ActionListener listener) {
        limpiarButton.addActionListener(listener);
    }

    public void addRetrocederButtonListener(ActionListener listener) {
        retrocederButton.addActionListener(listener);
    }

    public void limpiarCampos() {
        fechaField.setText("");
        horaField.setText("");
        pacienteField.setSelectedIndex(-1);
        doctorField.setSelectedIndex(-1);
        motivoField.setText("");
    }
}
