package com.clinicadental.view.paciente;

import com.clinicadental.controller.paciente.PacienteAddEditController;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.PacienteServiceImpl;
import com.clinicadental.view.init.MainScreen;
import com.clinicadental.model.Entity.Paciente;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PacienteTable extends JFrame {
    private JTable pacienteTable;
    private JButton backButton;
    private JButton addButton; // Botón para añadir pacientes
    private JPanel mainPanel;
    private IPacienteService pacienteService;

    public PacienteTable() {
        this.pacienteService = new PacienteServiceImpl();

        // Configuración de la ventana principal
        mainPanel = new JPanel(new BorderLayout());

        // Crear un panel con título para la tabla
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Listado de Pacientes",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                Color.BLACK
        ));

        // Crear la tabla con colores y fuente personalizada
        pacienteTable = new JTable() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desactivar la edición de las celdas
            }
        };
        pacienteTable.setFont(new Font("Arial", Font.PLAIN, 14));
        pacienteTable.setRowHeight(30);
        pacienteTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        pacienteTable.getTableHeader().setBackground(new Color(102, 178, 255));  // Color del encabezado
        pacienteTable.getTableHeader().setForeground(Color.WHITE);
        pacienteTable.setSelectionBackground(new Color(204, 229, 255));  // Color de selección

        JScrollPane scrollPane = new JScrollPane(pacienteTable);

        // Añadir la tabla con scroll al panel con título
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Botón para volver
        backButton = new JButton("Volver");

        // Botón para añadir nuevo paciente
        addButton = new JButton("Añadir Paciente");

        // Panel de botones (volver y añadir)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        // Añadir el panel de la tabla y los botones al mainPanel
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Gestión de Pacientes");
        setSize(800, 600);  // Establecer el mismo tamaño que MainScreen
        setLocationRelativeTo(null);  // Centrar la ventana en la pantalla
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Cargar pacientes en la tabla
        cargarPacientesEnTabla();

        // Evento para abrir detalles del paciente al hacer doble clic en una fila
        pacienteTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Detectar doble clic
                    int selectedRow = pacienteTable.getSelectedRow();  // Obtener la fila seleccionada
                    if (selectedRow != -1) {
                        abrirDetallesPaciente(selectedRow);
                    }
                }
            }
        });

        // Acción del botón "Volver" para regresar a MainScreen
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                volverAMainScreen();
            }
        });

        // Acción del botón "Añadir Paciente" para abrir la ventana PacienteForm
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirPacienteForm();
            }
        });
    }

    // Método para cargar los pacientes en la tabla
    public void cargarPacientesEnTabla() {
        List<Paciente> pacientes = pacienteService.obtenerTodos();
        setPacientesData(pacientes);
    }

    // Método para llenar la tabla con la lista de pacientes (sin el ID)
    public void setPacientesData(List<Paciente> pacientes) {
        String[] columnNames = {"Nombre", "Apellidos", "DNI", "Teléfono", "Dirección", "Código Postal", "Email"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;  // Evitar edición de celdas
            }
        };

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
            model.addRow(row);
        }

        pacienteTable.setModel(model);
    }

    // Método para abrir la pantalla de añadir paciente (PacienteForm)
    private void abrirPacienteForm() {
        PacienteForm pacienteForm = new PacienteForm();
        new PacienteAddEditController(pacienteForm, this); // Pasar referencia de PacienteTable
        pacienteForm.setVisible(true);
    }

    // Método para abrir detalles del paciente seleccionado
    private void abrirDetallesPaciente(int rowIndex) {
        // Obtener los datos de la fila seleccionada, manejando valores null
        String nombre = pacienteTable.getValueAt(rowIndex, 0) != null ? pacienteTable.getValueAt(rowIndex, 0).toString() : "N/A";
        String apellidos = pacienteTable.getValueAt(rowIndex, 1) != null ? pacienteTable.getValueAt(rowIndex, 1).toString() : "N/A";
        String dni = pacienteTable.getValueAt(rowIndex, 2) != null ? pacienteTable.getValueAt(rowIndex, 2).toString() : "N/A";
        String telefono = pacienteTable.getValueAt(rowIndex, 3) != null ? pacienteTable.getValueAt(rowIndex, 3).toString() : "N/A";
        String direccion = pacienteTable.getValueAt(rowIndex, 4) != null ? pacienteTable.getValueAt(rowIndex, 4).toString() : "N/A";
        String codPostal = pacienteTable.getValueAt(rowIndex, 5) != null ? pacienteTable.getValueAt(rowIndex, 5).toString() : "N/A";
        String email = pacienteTable.getValueAt(rowIndex, 6) != null ? pacienteTable.getValueAt(rowIndex, 6).toString() : "N/A";

        // Abrir la ventana de detalles
        PacienteDetailsDialog detallesDialog = new PacienteDetailsDialog(this, nombre, apellidos, dni, telefono, direccion, codPostal, email);
        detallesDialog.setVisible(true);
    }

    // Método para regresar a MainScreen
    private void volverAMainScreen() {
        // Cerrar la ventana actual
        dispose();
        // Abrir la pantalla principal MainScreen
        MainScreen mainScreen = new MainScreen();
        mainScreen.setVisible(true);
    }

    public JTable getPacienteTable() {
        return pacienteTable;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
