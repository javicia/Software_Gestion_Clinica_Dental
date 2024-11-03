package com.clinicadental.view.citas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CitaAgregar extends JFrame {
    private JTextField fechaField;
    private JTextField horaField;
    private JTextField pacienteField;
    private JTextField doctorField;
    private JTextField motivoField;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton retrocederButton;
    private JPanel mainPanel;

    // Asteriscos rojos para los campos validados
    private JLabel horaAsterisk;
    private JLabel fechaAsterisk;
    private JLabel pacienteAsterisk;
    private JLabel doctorAsterisk;

    public CitaAgregar() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(8, 3, 10, 10));  // Ajuste a 8 filas para incluir "NumColegiado"
        formPanel.setBorder(BorderFactory.createTitledBorder("Registrar Cita"));

        // Añadir etiquetas y campos de texto
        formPanel.add(new JLabel("Fecha:"));
        fechaField = new JTextField(20);
        formPanel.add(fechaField);
        fechaAsterisk = new JLabel("*");
        fechaAsterisk.setForeground(Color.BLACK);
        formPanel.add(fechaAsterisk);

        formPanel.add(new JLabel("Hora:"));
        horaField = new JTextField(20);
        formPanel.add(horaField);
        horaAsterisk = new JLabel("*");
        horaAsterisk.setForeground(Color.BLACK);
        formPanel.add(horaAsterisk);

        formPanel.add(new JLabel("Paciente:"));
        pacienteField = new JTextField(20);
        formPanel.add(pacienteField);
        pacienteAsterisk = new JLabel("*");
        pacienteAsterisk.setForeground(Color.BLACK);
        formPanel.add(pacienteAsterisk);

        formPanel.add(new JLabel("Doctor:"));
        doctorField = new JTextField(20);
        formPanel.add(doctorField);
        doctorAsterisk = new JLabel("*");
        doctorAsterisk.setForeground(Color.BLACK);
        formPanel.add(doctorAsterisk);

        formPanel.add(new JLabel("Motivo:"));
        motivoField = new JTextField(100);
        formPanel.add(motivoField);
        formPanel.add(new JLabel());

        JPanel buttonPanel = new JPanel();
        guardarButton = new JButton("Guardar");
        limpiarButton = new JButton("Limpiar");
        retrocederButton = new JButton("Volver");

        buttonPanel.add(guardarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(retrocederButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Registrar Cita");
        setSize(500, 400);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Método para limpiar los campos del formulario
    public void limpiarCampos() {
       fechaField.setText("");
       horaField.setText("");
       pacienteField.setText("");
       doctorField.setText("");
       motivoField.setText("");
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


    //Getter y setter


    public JTextField getFechaField() {
        return fechaField;
    }

    public void setFechaField(JTextField fechaField) {
        this.fechaField = fechaField;
    }

    public JTextField getHoraField() {
        return horaField;
    }

    public void setHoraField(JTextField horaField) {
        this.horaField = horaField;
    }

    public JTextField getPacienteField() {
        return pacienteField;
    }

    public void setPacienteField(JTextField pacienteField) {
        this.pacienteField = pacienteField;
    }

    public JTextField getDoctorField() {
        return doctorField;
    }

    public void setDoctorField(JTextField doctorField) {
        this.doctorField = doctorField;
    }

    public JTextField getMotivoField() {
        return motivoField;
    }

    public void setMotivoField(JTextField motivoField) {
        this.motivoField = motivoField;
    }

    public JLabel getHoraAsterisk() {
        return horaAsterisk;
    }

    public void setHoraAsterisk(JLabel horaAsterisk) {
        this.horaAsterisk = horaAsterisk;
    }

    public JLabel getFechaAsterisk() {
        return fechaAsterisk;
    }

    public void setFechaAsterisk(JLabel fechaAsterisk) {
        this.fechaAsterisk = fechaAsterisk;
    }

    public JLabel getPacienteAsterisk() {
        return pacienteAsterisk;
    }

    public void setPacienteAsterisk(JLabel pacienteAsterisk) {
        this.pacienteAsterisk = pacienteAsterisk;
    }

    public JLabel getDoctorAsterisk() {
        return doctorAsterisk;
    }

    public void setDoctorAsterisk(JLabel doctorAsterisk) {
        this.doctorAsterisk = doctorAsterisk;
    }
}
