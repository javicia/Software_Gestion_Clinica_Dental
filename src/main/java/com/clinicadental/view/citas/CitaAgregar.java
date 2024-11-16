package com.clinicadental.view.citas;

import com.clinicadental.common.Constans;
import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.utils.CalendarUtils;
import com.clinicadental.utils.DateUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class CitaAgregar extends JFrame {
    private JTextField fechaField;
    private JTextField horaField;
    private JComboBox<Paciente> pacienteField;
    private JComboBox<Doctor> doctorField;
    private JTextArea motivoField;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton retrocederButton;
    private JPanel mainPanel;

    private JLabel horaAsterisk;
    private JLabel fechaAsterisk;
    private JLabel pacienteAsterisk;
    private JLabel doctorAsterisk;

    public CitaAgregar(List<Paciente> pacientesOrdenados, List<Doctor> doctoresOrdenados) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Establecer el icono de la aplicación
        setIconImage(new ImageIcon(getClass().getResource(Constans.ICON_LOGO_IMAGE_PATH)).getImage());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Registrar Cita"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        Font smallFont = new Font("Arial", Font.PLAIN, 12);



        // Configuración de fecha
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Fecha:"), gbc);

        fechaField = new JTextField(20);
        configurarCampoUniforme(fechaField, smallFont);
        JPanel fechaPanel = new JPanel(new BorderLayout());
        fechaPanel.add(fechaField, BorderLayout.CENTER);

        ImageIcon calendarIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icons/icon_calendar.png")));
        Image scaledImage = calendarIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        calendarIcon = new ImageIcon(scaledImage);

        JLabel calendarioLabel = new JLabel(calendarIcon);
        calendarioLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        calendarioLabel.setToolTipText("Seleccionar fecha");

        calendarioLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CalendarUtils.mostrarCalendario(CitaAgregar.this, fechaField);
            }
        });

        // Agregar espacio entre el JTextField y el icono
        JPanel iconPanelFecha = new JPanel(new BorderLayout());
        iconPanelFecha.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));  // Ajuste de espacio
        iconPanelFecha.add(calendarioLabel, BorderLayout.CENTER);

        fechaPanel.add(iconPanelFecha, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 0;
        formPanel.add(fechaPanel, gbc);

        fechaAsterisk = new JLabel("*");
        fechaAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        gbc.gridy = 0;
        formPanel.add(fechaAsterisk, gbc);

        // Configuración de hora
        gbc.gridx = 0;
        gbc.gridy = 1;
        formPanel.add(new JLabel("Hora:"), gbc);

        horaField = new JTextField(20);
        configurarCampoUniforme(horaField, smallFont);
        JPanel horaPanel = new JPanel(new BorderLayout());
        horaPanel.add(horaField, BorderLayout.CENTER);

        ImageIcon clockIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icons/icon_our.png")));
        Image scaledClockImage = clockIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        clockIcon = new ImageIcon(scaledClockImage);

        JLabel relojLabel = new JLabel(clockIcon);
        relojLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        relojLabel.setToolTipText("Seleccionar hora");

        relojLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CalendarUtils.mostrarSelectorHora(CitaAgregar.this, horaField);
            }
        });

        // Agregar espacio entre el JTextField y el icono
        JPanel iconPanelHora = new JPanel(new BorderLayout());
        iconPanelHora.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));  // Ajuste de espacio
        iconPanelHora.add(relojLabel, BorderLayout.CENTER);

        horaPanel.add(iconPanelHora, BorderLayout.EAST);

        gbc.gridx = 1;
        gbc.gridy = 1;
        formPanel.add(horaPanel, gbc);

        horaAsterisk = new JLabel("*");
        horaAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        gbc.gridy = 1;
        formPanel.add(horaAsterisk, gbc);

        // Configuración de JComboBox para pacientes
        gbc.gridx = 0;
        gbc.gridy = 2;
        formPanel.add(new JLabel("Paciente:"), gbc);

        pacienteField = new JComboBox<>();
        for (Paciente paciente : pacientesOrdenados) {
            pacienteField.addItem(paciente);
        }
        pacienteField.setFont(smallFont);
        pacienteField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 2;
        formPanel.add(pacienteField, gbc);

        pacienteAsterisk = new JLabel("*");
        pacienteAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        gbc.gridy = 2;
        formPanel.add(pacienteAsterisk, gbc);

        // Configuración de JComboBox para doctores
        gbc.gridx = 0;
        gbc.gridy = 3;
        formPanel.add(new JLabel("Doctor:"), gbc);

        doctorField = new JComboBox<>();
        for (Doctor doctor : doctoresOrdenados) {
            doctorField.addItem(doctor);
        }
        doctorField.setFont(smallFont);
        doctorField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 3;
        formPanel.add(doctorField, gbc);

        doctorAsterisk = new JLabel("*");
        doctorAsterisk.setForeground(Color.BLACK);
        gbc.gridx = 2;
        gbc.gridy = 3;
        formPanel.add(doctorAsterisk, gbc);

        // Configuración del campo de motivo más ancho y largo
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 1;
        formPanel.add(new JLabel("Motivo:"), gbc);

        motivoField = new JTextArea(5, 30);
        motivoField.setLineWrap(true);
        motivoField.setWrapStyleWord(true);
        configurarCampoArea(motivoField, smallFont);

        JScrollPane scrollPane = new JScrollPane(motivoField);
        scrollPane.setPreferredSize(new Dimension(400, 150));

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(scrollPane, gbc);

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
        setSize(600, 500);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public Date getFechaSeleccionada() {
        return DateUtils.parseFecha(fechaField.getText());
    }

    private void configurarCampoUniforme(JTextField textField, Font font) {
        textField.setFont(font);
        textField.setPreferredSize(new Dimension(200, 25));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private void configurarCampoArea(JTextArea textArea, Font font) {
        textArea.setFont(font);
        textArea.setMargin(new Insets(5, 5, 5, 5));
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    public void limpiarCampos() {
        fechaField.setText("");
        horaField.setText("");
        pacienteField.setSelectedIndex(-1);
        doctorField.setSelectedIndex(-1);
        motivoField.setText("");
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

    public JTextField getFechaField() { return fechaField; }
    public JTextField getHoraField() { return horaField; }
    public JComboBox<Paciente> getPacienteField() { return pacienteField; }
    public JComboBox<Doctor> getDoctorField() { return doctorField; }
    public JTextArea getMotivoField() { return motivoField; }
    public JLabel getHoraAsterisk() { return horaAsterisk; }
    public JLabel getFechaAsterisk() { return fechaAsterisk; }
    public JLabel getPacienteAsterisk() { return pacienteAsterisk; }
    public JLabel getDoctorAsterisk() { return doctorAsterisk; }
}
