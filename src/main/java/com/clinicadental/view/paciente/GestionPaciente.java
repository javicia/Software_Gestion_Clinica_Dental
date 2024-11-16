package com.clinicadental.view.paciente;

import com.clinicadental.common.Constans;
import com.clinicadental.common.design.GradientDesign;
import com.clinicadental.common.design.ButtonDesign;
import com.clinicadental.common.design.JTextFieldSearchDesign;
import com.clinicadental.controller.paciente.PacienteAgregarController;
import com.clinicadental.controller.paciente.PacienteDetailsController;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.impl.PacienteServiceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionPaciente extends JFrame {
    private JTable pacienteTable;
    private DefaultTableModel tableModel;
    private JTextField globalFilterField;
    private JPanel filterPanel;
    private JButton backButton;
    private JButton addButton;

    public GestionPaciente() {
        setupUI();
        new PacienteDetailsController(this); // Asocia el controlador con esta vista
        actualizarTabla();
    }

    private void setupUI() {
        GradientDesign mainPanel = new GradientDesign(new Color(0, 102, 204), new Color(153, 204, 255));
        mainPanel.setLayout(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Listado de Pacientes",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                Color.BLACK

        ));


        // Establecer el icono de la aplicación
        setIconImage(new ImageIcon(getClass().getResource(Constans.ICON_LOGO_IMAGE_PATH)).getImage());

        // Crear la tabla
        tableModel = new DefaultTableModel(new String[]{"Nombre", "Apellidos", "DNI", "Teléfono", "Dirección", "Código Postal", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Hacer que las celdas no sean editables
            }
        };

        pacienteTable = new JTable(tableModel);
        pacienteTable.setFont(new Font("Arial", Font.PLAIN, 14));
        pacienteTable.setRowHeight(30);
        pacienteTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        pacienteTable.getTableHeader().setBackground(new Color(102, 178, 255));
        pacienteTable.getTableHeader().setForeground(Color.WHITE);
        pacienteTable.setSelectionBackground(new Color(204, 229, 255));

        JScrollPane scrollPane = new JScrollPane(pacienteTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Crear el campo de filtro global
        filterPanel = JTextFieldSearchDesign.createSearchField();

        // Obtener el JTextField del panel y asignarlo a globalFilterField
        globalFilterField = (JTextField) filterPanel.getComponent(0);

        // Configuración de los botones
        addButton = new ButtonDesign("Añadir Paciente");
        addButton.addActionListener(e -> abrirPacienteForm());

        backButton = new ButtonDesign("Volver");
        backButton.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(filterPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        tablePanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Gestión de Pacientes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Método para abrir el formulario de añadir paciente
    private void abrirPacienteForm() {
        PacienteAgregar pacienteForm = new PacienteAgregar();
        new PacienteAgregarController(pacienteForm, this);
        pacienteForm.setVisible(true);
    }

    // Método para actualizar la tabla
    public void actualizarTabla() {
        List<Paciente> pacientes = new PacienteServiceImpl().obtenerTodos();
        setPacientesData(pacientes);
    }

    public void setPacientesData(List<Paciente> pacientes) {
        tableModel.setRowCount(0);
        for (Paciente paciente : pacientes) {
            tableModel.addRow(new Object[]{
                    paciente.getNombre(),
                    paciente.getApellidos(),
                    paciente.getDni(),
                    paciente.getTelefono(),
                    paciente.getDireccion(),
                    paciente.getCodPostal(),
                    paciente.getEmail()
            });
        }
    }

    public JTable getPacienteTable() {
        return pacienteTable;
    }

    public JTextField getGlobalFilterField() {
        return globalFilterField;
    }
}
