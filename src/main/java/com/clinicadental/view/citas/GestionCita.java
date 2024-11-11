package com.clinicadental.view.citas;

import com.clinicadental.common.Constan;
import com.clinicadental.controller.citas.CitaAgregarController;
import com.clinicadental.model.Entity.Cita;
import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.ICitasService;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.CitasServiceImpl;
import com.clinicadental.service.impl.DoctorServiceImpl;
import com.clinicadental.service.impl.PacienteServiceImpl;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class GestionCita extends JFrame {
    private JTable citaTable;
    private DefaultTableModel tableModel;
    private JTextField[] filterFields;
    private JButton addButton;
    private JButton backButton;
    private ICitasService citasService;
    private IPacienteService pacienteService;
    private IDoctorService doctorService;

    public GestionCita() {
        citasService = new CitasServiceImpl();
        pacienteService = new PacienteServiceImpl();
        doctorService = new DoctorServiceImpl();

        setupUI();
        actualizarTabla();
    }

    private void setupUI() {
        // Configuración de la tabla de citas
        tableModel = new DefaultTableModel(new String[]{"ID", "Fecha", "Hora", "Paciente", "Doctor", "Motivo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desactivar edición de celdas
            }
        };

        citaTable = new JTable(tableModel);
        citaTable.setFont(new Font("Arial", Font.PLAIN, 14));
        citaTable.setRowHeight(30);
        citaTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        citaTable.getTableHeader().setBackground(new Color(102, 178, 255));
        citaTable.getTableHeader().setForeground(Color.WHITE);
        citaTable.setSelectionBackground(new Color(204, 229, 255));

        citaTable.getColumnModel().getColumn(0).setMinWidth(0);
        citaTable.getColumnModel().getColumn(0).setMaxWidth(0); // Ocultar la columna de ID

        // Establecer el icono de la aplicación
        setIconImage(new ImageIcon(getClass().getResource(Constan.ICON_LOGO_IMAGE_PATH)).getImage());

        JScrollPane scrollPane = new JScrollPane(citaTable);

        // Configuración del panel de filtros
        filterFields = new JTextField[5];
        JPanel filterPanel = new JPanel(new GridLayout(1, 5));
        for (int i = 0; i < filterFields.length; i++) {
            filterFields[i] = new JTextField();
            filterPanel.add(filterFields[i]);
        }

        // Configuración de los botones "Añadir Cita" y "Volver"
        addButton = new JButton("Añadir Cita");
        addButton.addActionListener(e -> abrirCitaForm());

        backButton = new JButton("Volver");
        backButton.addActionListener(e -> dispose()); // Cerrar el JFrame actual

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        // Configuración del panel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Gestión de Citas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    public JTable getCitaTable() {
        return citaTable;
    }

    public JTextField[] getFilterFields() {
        return filterFields;
    }

    public void actualizarTabla() {
        List<Cita> citas = citasService.getAllCitas();
        setCitasData(citas);
    }

    public void setCitasData(List<Cita> citas) {
        tableModel.setRowCount(0);
        for (Cita cita : citas) {
            tableModel.addRow(new Object[]{
                    cita.getId_cita(),
                    cita.getFecha().toString(),
                    cita.getHora().toString(),
                    cita.getPaciente().getNombre(),
                    cita.getDoctor().getNombre(),
                    cita.getMotivo()
            });
        }
    }

    // Método para abrir el formulario de añadir cita
    private void abrirCitaForm() {
        // Obtener listas de pacientes y doctores ordenados
        List<Paciente> pacientes = pacienteService.obtenerTodos().stream()
                .sorted((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()))
                .collect(Collectors.toList());

        List<Doctor> doctores = doctorService.getAllDoctor().stream()
                .sorted((d1, d2) -> d1.getNombre().compareToIgnoreCase(d2.getNombre()))
                .collect(Collectors.toList());

        // Crear instancia de CitaAgregar y CitaAgregarController
        CitaAgregar citaForm = new CitaAgregar(pacientes, doctores);
        new CitaAgregarController(citaForm, this);

        // Mostrar el formulario de añadir cita
        citaForm.setVisible(true);
    }
}
