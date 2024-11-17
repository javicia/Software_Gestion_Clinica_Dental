package com.clinicadental.view.citas;

import com.clinicadental.common.Constans;
import com.clinicadental.common.design.GradientDesign;
import com.clinicadental.common.design.ButtonDesign;
import com.clinicadental.common.design.JTextFieldSearchDesign;
import com.clinicadental.controller.citas.CitaAgregarController;
import com.clinicadental.controller.citas.CitaDetailsController;
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
    private JTextField globalFilterField;
    private JPanel filterPanel;
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
        new CitaDetailsController(this);
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

        setIconImage(new ImageIcon(getClass().getResource(Constans.ICON_LOGO_IMAGE_PATH)).getImage());

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

        // Crear el campo de filtro global
        filterPanel = JTextFieldSearchDesign.createSearchField();
        globalFilterField = (JTextField) filterPanel.getComponent(0);

        // Configuración de los botones
        addButton = new ButtonDesign("Añadir Cita");
        addButton.addActionListener(e -> abrirCitaForm());

        backButton = new ButtonDesign("Volver");
        backButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        // Crear un panel inferior para filtro y botones
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(filterPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Añadir scrollPane y panel inferior al panel de la tabla
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        tablePanel.add(bottomPanel, BorderLayout.SOUTH);

        // Añadir los componentes al panel principal
        mainPanel.add(tablePanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setTitle("Gestión de Citas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    private void abrirCitaForm() {
        List<Paciente> pacientes = pacienteService.obtenerTodos().stream()
                .sorted((p1, p2) -> p1.getNombre().compareToIgnoreCase(p2.getNombre()))
                .collect(Collectors.toList());

        List<Doctor> doctores = doctorService.getAllDoctor().stream()
                .sorted((d1, d2) -> d1.getNombre().compareToIgnoreCase(d2.getNombre()))
                .collect(Collectors.toList());

        if (pacientes.isEmpty() || doctores.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No hay pacientes o doctores disponibles.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        CitaAgregar citaForm = new CitaAgregar(pacientes, doctores);
        new CitaAgregarController(citaForm, this);

        citaForm.setVisible(true);
    }

    public void actualizarTabla() {
        ICitasService citasService = new CitasServiceImpl(); // Instancia válida del servicio
        List<Cita> citas = citasService.getAllCitas(); // Obtener todas las citas
        setCitasData(citas); // Refrescar la tabla
    }

    public void setCitasData(List<Cita> citas) {
        tableModel.setRowCount(0); // Limpiar las filas existentes en la tabla
        for (Cita cita : citas) {
            tableModel.addRow(new Object[]{
                    cita.getId_cita(),
                    cita.getFecha().toString(),
                    cita.getHora().toString(),
                    cita.getPaciente().getApellidos() + ", " + cita.getPaciente().getNombre(),
                    cita.getDoctor().getApellidos() + ", " + cita.getDoctor().getNombre(),
                    cita.getMotivo()
            });
        }
    }


    public JTable getCitaTable() {
        return citaTable;
    }

    public JTextField getGlobalFilterField() {
        return globalFilterField;
    }
}

