package com.clinicadental.view.citas;

import com.clinicadental.common.Constans;
import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.utils.CalendarUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Objects;

public class CitaEditar extends JFrame {
    private JTextField fechaField;
    private JTextField horaField;
    private JComboBox<Paciente> pacienteField;
    private JComboBox<Doctor> doctorField;
    private JTextArea motivoField;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton retrocederButton;

    private JLabel fechaAsterisk;
    private JLabel horaAsterisk;
    private JLabel pacienteAsterisk;
    private JLabel doctorAsterisk;

    public CitaEditar(List<Paciente> pacientes, List<Doctor> doctores) {
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

        // Campo Fecha con icono de calendario
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Fecha:"), gbc);

        fechaField = new JTextField(20);
        JPanel fechaPanel = new JPanel(new BorderLayout());
        fechaPanel.add(fechaField, BorderLayout.CENTER);

        ImageIcon calendarIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icons/calendar.png")));
        Image scaledCalendarImage = calendarIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        calendarIcon = new ImageIcon(scaledCalendarImage);

        JLabel calendarioLabel = new JLabel(calendarIcon);
        calendarioLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        calendarioLabel.setToolTipText("Seleccionar fecha");

        calendarioLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CalendarUtils.mostrarCalendario(CitaEditar.this, fechaField);
            }
        });

        fechaPanel.add(calendarioLabel, BorderLayout.EAST);
        gbc.gridx = 1;
        formPanel.add(fechaPanel, gbc);

        fechaAsterisk = new JLabel("*");
        fechaAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        formPanel.add(fechaAsterisk, gbc);

        // Campo Hora con icono de reloj
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Hora:"), gbc);

        horaField = new JTextField(20);
        JPanel horaPanel = new JPanel(new BorderLayout());
        horaPanel.add(horaField, BorderLayout.CENTER);

        ImageIcon clockIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icons/our.png")));
        Image scaledClockImage = clockIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        clockIcon = new ImageIcon(scaledClockImage);

        JLabel relojLabel = new JLabel(clockIcon);
        relojLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        relojLabel.setToolTipText("Seleccionar hora");

        relojLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CalendarUtils.mostrarSelectorHora(CitaEditar.this, horaField);
            }
        });

        horaPanel.add(relojLabel, BorderLayout.EAST);
        gbc.gridx = 1;
        formPanel.add(horaPanel, gbc);

        horaAsterisk = new JLabel("*");
        horaAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        formPanel.add(horaAsterisk, gbc);

        // JComboBox para pacientes
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Paciente:"), gbc);

        pacienteField = new JComboBox<>();
        for (Paciente paciente : pacientes) {
            pacienteField.addItem(paciente);
        }
        gbc.gridx = 1;
        formPanel.add(pacienteField, gbc);

        pacienteAsterisk = new JLabel("*");
        pacienteAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        formPanel.add(pacienteAsterisk, gbc);

        // JComboBox para doctores
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Doctor:"), gbc);

        doctorField = new JComboBox<>();
        for (Doctor doctor : doctores) {
            doctorField.addItem(doctor);
        }
        gbc.gridx = 1;
        formPanel.add(doctorField, gbc);

        doctorAsterisk = new JLabel("*");
        doctorAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        formPanel.add(doctorAsterisk, gbc);

        // Campo Motivo
        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Motivo:"), gbc);

        motivoField = new JTextArea(5, 20);
        motivoField.setLineWrap(true);
        motivoField.setWrapStyleWord(true);
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        formPanel.add(new JScrollPane(motivoField), gbc);

        // Panel de botones
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

    public JComboBox<Paciente> getPacienteField() {
        return pacienteField;
    }

    public JComboBox<Doctor> getDoctorField() {
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
