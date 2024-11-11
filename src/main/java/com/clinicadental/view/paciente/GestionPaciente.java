package com.clinicadental.view.paciente;

import com.clinicadental.common.Constans;
import com.clinicadental.common.design.GradientDesign;
import com.clinicadental.common.design.ButtonDesign;
import com.clinicadental.controller.paciente.PacienteAgregarController;
import com.clinicadental.controller.paciente.PacienteEditarController;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.PacienteServiceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionPaciente extends JFrame {
    private JTable pacienteTable;
    private DefaultTableModel tableModel;
    private JTextField[] filterFields;
    private JButton backButton;
    private JButton addButton;

    public GestionPaciente() {
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
                return false; // Desactivar la edición de las celdas
            }
        };

        pacienteTable = new JTable(tableModel);
        pacienteTable.setFont(new Font("Arial", Font.PLAIN, 14));
        pacienteTable.setRowHeight(30);
        pacienteTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        pacienteTable.getTableHeader().setBackground(new Color(102, 178, 255));
        pacienteTable.getTableHeader().setForeground(Color.WHITE);
        pacienteTable.setSelectionBackground(new Color(204, 229, 255));

        // Agregar un MouseListener para detectar doble clic y abrir detalles del paciente
        pacienteTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Detectar doble clic
                    int selectedRow = pacienteTable.getSelectedRow();
                    if (selectedRow != -1) {
                        abrirDetallesPaciente(selectedRow);
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(pacienteTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Configuración del panel de filtros
        filterFields = new JTextField[7];
        JPanel filterPanel = new JPanel(new GridLayout(1, 7));
        filterPanel.setOpaque(false);
        for (int i = 0; i < filterFields.length; i++) {
            filterFields[i] = new JTextField();
            filterPanel.add(filterFields[i]);
        }

        // Configuración de los botones "Añadir Paciente" y "Volver"
        addButton = new ButtonDesign("Añadir Paciente");
        addButton.addActionListener(e -> abrirPacienteForm());

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

    // Método para abrir detalles del paciente seleccionado
    private void abrirDetallesPaciente(int rowIndex) {
        String nombre = pacienteTable.getValueAt(rowIndex, 0) != null ? pacienteTable.getValueAt(rowIndex, 0).toString() : "N/A";
        String apellidos = pacienteTable.getValueAt(rowIndex, 1) != null ? pacienteTable.getValueAt(rowIndex, 1).toString() : "N/A";
        String dni = pacienteTable.getValueAt(rowIndex, 2) != null ? pacienteTable.getValueAt(rowIndex, 2).toString() : "N/A";
        String telefono = pacienteTable.getValueAt(rowIndex, 3) != null ? pacienteTable.getValueAt(rowIndex, 3).toString() : "N/A";
        String direccion = pacienteTable.getValueAt(rowIndex, 4) != null ? pacienteTable.getValueAt(rowIndex, 4).toString() : "N/A";
        String codPostal = pacienteTable.getValueAt(rowIndex, 5) != null ? pacienteTable.getValueAt(rowIndex, 5).toString() : "N/A";
        String email = pacienteTable.getValueAt(rowIndex, 6) != null ? pacienteTable.getValueAt(rowIndex, 6).toString() : "N/A";

        PacienteDetails detallesDialog = new PacienteDetails(this, nombre, apellidos, dni, telefono, direccion, codPostal, email);
        detallesDialog.addDeleteListener(e -> {
            eliminarPaciente(dni);
            detallesDialog.cerrarDialogo();
            actualizarTabla();
        });
        detallesDialog.addEditListener(e -> {
            editarPaciente(dni);
            detallesDialog.cerrarDialogo();
        });
        detallesDialog.setVisible(true);
    }

    private void editarPaciente(String dni) {
        IPacienteService pacienteService = new PacienteServiceImpl();
        Paciente paciente = pacienteService.obtenerTodos().stream()
                .filter(p -> p.getDni() != null && p.getDni().equals(dni))
                .findFirst()
                .orElse(null);

        if (paciente != null) {
            PacienteEditar editarPacienteForm = new PacienteEditar();
            editarPacienteForm.getNombreField().setText(paciente.getNombre());
            editarPacienteForm.getApellidosField().setText(paciente.getApellidos());
            editarPacienteForm.getDniField().setText(paciente.getDni());
            editarPacienteForm.getTelefonoField().setText(paciente.getTelefono());
            editarPacienteForm.getDireccionField().setText(paciente.getDireccion());
            editarPacienteForm.getCodPostalField().setText(String.valueOf(paciente.getCodPostal()));
            editarPacienteForm.getEmailField().setText(paciente.getEmail());

            new PacienteEditarController(paciente, editarPacienteForm, this);
            editarPacienteForm.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el paciente a editar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarPaciente(String dni) {
        IPacienteService pacienteService = new PacienteServiceImpl();
        Paciente paciente = pacienteService.obtenerTodos().stream()
                .filter(p -> p.getDni() != null && p.getDni().equals(dni))
                .findFirst()
                .orElse(null);

        if (paciente != null) {
            pacienteService.deletePaciente(paciente);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Paciente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el paciente a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTabla() {
        IPacienteService pacienteService = new PacienteServiceImpl();
        List<Paciente> pacientes = pacienteService.obtenerTodos();
        setPacientesData(pacientes);
    }

    public void setPacientesData(List<Paciente> pacientes) {
        tableModel.setRowCount(0);
        for (Paciente paciente : pacientes) {
            Object[] row = {
                    paciente.getNombre(),
                    paciente.getApellidos(),
                    paciente.getDni(),
                    paciente.getTelefono(),
                    paciente.getDireccion(),
                    paciente.getCodPostal(),
                    paciente.getEmail()
            };
            tableModel.addRow(row);
        }
    }

    public JTextField[] getFilterFields() {
        return filterFields;
    }

    public JTable getPacienteTable() {
        return pacienteTable;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAddButton() {
        return addButton;
    }
}
