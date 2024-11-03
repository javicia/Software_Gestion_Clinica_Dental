package com.clinicadental.view.citas;

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
    private JComboBox<String> pacienteField;
    private JComboBox<String> doctorField;
    private JTextArea motivoField;
    private JButton guardarButton;
    private JButton limpiarButton;
    private JButton retrocederButton;
    private JPanel mainPanel;

    // Asteriscos rojos para los campos validados
    private JLabel horaAsterisk;
    private JLabel fechaAsterisk;
    private JLabel pacienteAsterisk;
    private JLabel doctorAsterisk;

    public CitaAgregar(List<String> pacientesOrdenados, List<String> doctoresOrdenados) { // Recibe lista de pacientes y doctores
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formPanel = new JPanel(new GridLayout(5, 3, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Registrar Cita"));

        Font smallFont = new Font("Arial", Font.PLAIN, 12);

        // Fecha con selector de calendario
        formPanel.add(new JLabel("Fecha:"));
        JPanel fechaPanel = new JPanel(new BorderLayout());

        fechaField = new JTextField(20);
        configurarCampoUniforme(fechaField, smallFont);
        fechaPanel.add(fechaField, BorderLayout.CENTER);

        // Icono del calendario
        ImageIcon calendarIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icons/calendar.png")));
        Image scaledImage = calendarIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        calendarIcon = new ImageIcon(scaledImage);

        JLabel calendarioLabel = new JLabel(calendarIcon);
        calendarioLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        calendarioLabel.setToolTipText("Seleccionar fecha");

        // Mostrar el JCalendar en un JDialog al hacer clic en el icono
        calendarioLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CalendarUtils.mostrarCalendario(CitaAgregar.this, fechaField);
            }
        });

        JPanel iconPanelFecha = new JPanel(new BorderLayout());
        iconPanelFecha.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        iconPanelFecha.add(calendarioLabel, BorderLayout.CENTER);

        fechaPanel.add(iconPanelFecha, BorderLayout.EAST);
        formPanel.add(fechaPanel);

        fechaAsterisk = new JLabel("*");
        fechaAsterisk.setForeground(Color.BLACK);
        formPanel.add(fechaAsterisk);

        // Hora con selector de reloj
        formPanel.add(new JLabel("Hora:"));
        JPanel horaPanel = new JPanel(new BorderLayout());

        horaField = new JTextField(20);
        configurarCampoUniforme(horaField, smallFont);
        horaPanel.add(horaField, BorderLayout.CENTER);

        // Icono del reloj
        ImageIcon clockIcon = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("icons/our.png")));
        Image scaledClockImage = clockIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        clockIcon = new ImageIcon(scaledClockImage);

        JLabel relojLabel = new JLabel(clockIcon);
        relojLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        relojLabel.setToolTipText("Seleccionar hora");

        // Mostrar el selector de reloj en un JDialog al hacer clic en el icono
        relojLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CalendarUtils.mostrarSelectorHora(CitaAgregar.this, horaField);
            }
        });

        JPanel iconPanelHora = new JPanel(new BorderLayout());
        iconPanelHora.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        iconPanelHora.add(relojLabel, BorderLayout.CENTER);

        horaPanel.add(iconPanelHora, BorderLayout.EAST);
        formPanel.add(horaPanel);

        horaAsterisk = new JLabel("*");
        horaAsterisk.setForeground(Color.BLACK);
        formPanel.add(horaAsterisk);

        // Campo de Paciente como JComboBox (no editable)
        formPanel.add(new JLabel("Paciente:"));
        pacienteField = new JComboBox<>(pacientesOrdenados.toArray(new String[0]));
        pacienteField.setFont(smallFont);
        pacienteField.setEditable(false);  // Desactivar edición
        formPanel.add(pacienteField);
        pacienteAsterisk = new JLabel("*");
        pacienteAsterisk.setForeground(Color.BLACK);
        formPanel.add(pacienteAsterisk);

        // Campo de Doctor como JComboBox (no editable)
        formPanel.add(new JLabel("Doctor:"));
        doctorField = new JComboBox<>(doctoresOrdenados.toArray(new String[0]));
        doctorField.setFont(smallFont);
        doctorField.setEditable(false);  // Desactivar edición
        formPanel.add(doctorField);
        doctorAsterisk = new JLabel("*");
        doctorAsterisk.setForeground(Color.BLACK);
        formPanel.add(doctorAsterisk);

        formPanel.add(new JLabel("Motivo:"));
        motivoField = new JTextArea(5, 30);
        motivoField.setLineWrap(true);
        motivoField.setWrapStyleWord(true);
        configurarCampoArea(motivoField, smallFont);

        JScrollPane scrollPane = new JScrollPane(motivoField);
        scrollPane.setPreferredSize(new Dimension(400, 100));
        formPanel.add(scrollPane);
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

    public Date getFechaSeleccionada() {
        return DateUtils.parseFecha(fechaField.getText());
    }

    // Método para configurar cada JTextField con altura uniforme
    private void configurarCampoUniforme(JTextField textField, Font font) {
        textField.setFont(font);
        textField.setPreferredSize(new Dimension(200, 25));
        textField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    // Método para configurar el JTextArea de motivoField
    private void configurarCampoArea(JTextArea textArea, Font font) {
        textArea.setFont(font);
        textArea.setMargin(new Insets(5, 5, 5, 5));
        textArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    // Método para limpiar los campos del formulario y restablecer los asteriscos a negro
    public void limpiarCampos() {
        fechaField.setText("");
        horaField.setText("");
        pacienteField.setSelectedIndex(-1); // Restablecer JComboBox
        doctorField.setSelectedIndex(-1);   // Restablecer JComboBox
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

    // Getters y Setters
    public JTextField getFechaField() { return fechaField; }
    public JTextField getHoraField() { return horaField; }
    public JComboBox<String> getPacienteField() { return pacienteField; }
    public JComboBox<String> getDoctorField() { return doctorField; }
    public JTextArea getMotivoField() { return motivoField; }
    public JLabel getHoraAsterisk() { return horaAsterisk; }
    public JLabel getFechaAsterisk() { return fechaAsterisk; }
    public JLabel getPacienteAsterisk() { return pacienteAsterisk; }
    public JLabel getDoctorAsterisk() { return doctorAsterisk; }
}
