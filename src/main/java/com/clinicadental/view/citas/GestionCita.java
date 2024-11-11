package com.clinicadental.view.citas;

import com.clinicadental.common.Constans;
import com.clinicadental.common.design.GradientDesign;
import com.clinicadental.common.design.ButtonDesign;
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
import javax.swing.border.TitledBorder;
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
        // Panel principal con fondo degradado
        GradientDesign mainPanel = new GradientDesign(new Color(0, 102, 204), new Color(153, 204, 255));
        mainPanel.setLayout(new BorderLayout());

        // Panel con título para la tabla
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Listado de Citas",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                Color.BLACK
        ));

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

        setIconImage(new ImageIcon(getClass().getResource(Constans.ICON_LOGO_IMAGE_PATH)).getImage());

        JScrollPane scrollPane = new JScrollPane(citaTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Configuración del panel de filtros con transparencia
        filterFields = new JTextField[5];
        JPanel filterPanel = new JPanel(new GridLayout(1, 5));
        filterPanel.setOpaque(false);
        for (int i = 0; i < filterFields.length; i++) {
            filterFields[i] = new JTextField();
            filterPanel.add(filterFields[i]);
        }

        // Configuración de los botones
        addButton = new ButtonDesign("Añadir Cita");
        addButton.addActionListener(e -> abrirCitaForm());

        backButton = new ButtonDesign("Volver");
        backButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        // Añadir filtros y scrollPane al panel de la tabla
        tablePanel.add(filterPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Añadir los componentes al panel principal
        mainPanel.add(tablePanel, BorderLayout.CENTER);
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

    private void abrirCitaForm() {
        List<Paciente> pacientes = pacienteService.obtenerTodos().stream()
                .sorted((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()))
                .collect(Collectors.toList());

        List<Doctor> doctores = doctorService.getAllDoctor().stream()
                .sorted((d1, d2) -> d1.getNombre().compareToIgnoreCase(d2.getNombre()))
                .collect(Collectors.toList());

        CitaAgregar citaForm = new CitaAgregar(pacientes, doctores);
        new CitaAgregarController(citaForm, this);

        citaForm.setVisible(true);
    }
}
