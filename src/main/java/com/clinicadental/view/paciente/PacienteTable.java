package com.clinicadental.view.paciente;

import com.clinicadental.model.Entity.Paciente;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class PacienteTable extends JFrame {
    private JTable pacienteTable;
    private JButton backButton;
    private JPanel mainPanel;

    public PacienteTable() {
        // Configuración de la ventana principal
        mainPanel = new JPanel(new BorderLayout());

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

        // Botón para volver
        backButton = new JButton("Volver");

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Lista de Pacientes");
        setSize(800, 400);
        setLocationRelativeTo(null);  // Centrar la ventana
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

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

    // Método para abrir la ventana con los detalles del paciente seleccionado
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

    public JTable getPacienteTable() {
        return pacienteTable;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
