package com.clinicadental.view.paciente;

import com.clinicadental.controller.paciente.PacienteAgregarController;
import com.clinicadental.controller.paciente.PacienteEditarController;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IPacienteService;
import com.clinicadental.service.impl.PacienteServiceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionPaciente extends JFrame {
    private JTable pacienteTable;
    private DefaultTableModel tableModel;
    private JTextField[] filterFields;
    private JButton backButton;
    private JButton addButton;
    private JPanel mainPanel;

    public GestionPaciente() {
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

        // Crear la tabla
        tableModel = new DefaultTableModel(new String[]{"Nombre", "Apellidos", "DNI", "Teléfono", "Dirección", "Código Postal", "Email"}, 0);
        pacienteTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desactivar la edición de las celdas
            }
        };
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
                    if (selectedRow != -1) {  // Si hay una fila seleccionada
                        abrirDetallesPaciente(selectedRow);  // Llamar al método para abrir los detalles del paciente
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(pacienteTable);

        // Panel de filtros debajo de la cabecera
        JPanel filterPanel = new JPanel(new GridLayout(1, 7));  // 7 columnas para los filtros
        filterFields = new JTextField[7];
        for (int i = 0; i < filterFields.length; i++) {
            filterFields[i] = new JTextField();
            filterPanel.add(filterFields[i]);
        }

        // Añadir los filtros y la tabla al panel con scroll
        tablePanel.add(filterPanel, BorderLayout.NORTH);
        tablePanel.add(scrollPane, BorderLayout.CENTER);

        // Botón para volver
        backButton = new JButton("Volver");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Cerrar el JFrame actual
            }
        });


        // Botón para añadir nuevo paciente
        addButton = new JButton("Añadir Paciente");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirPacienteForm();  // Llamar al método para abrir el formulario de añadir paciente
            }
        });

        // Panel de botones (volver y añadir)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(backButton);

        // Añadir el panel de la tabla y los botones al mainPanel
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Gestión de Pacientes");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Método para abrir el formulario de añadir paciente
    private void abrirPacienteForm() {
        PacienteAgregar pacienteForm = new PacienteAgregar();
        new PacienteAgregarController(pacienteForm, this); // Pasar la referencia de PacienteTable para actualizar la tabla
        pacienteForm.setVisible(true);  // Asegurarse de que el formulario sea visible
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
        PacienteDetails detallesDialog = new PacienteDetails(this, nombre, apellidos, dni, telefono, direccion, codPostal, email);
        // Agregar listener para el botón de eliminar
        detallesDialog.addDeleteListener(e -> {
        // Lógica para eliminar el registro de la base de datos
        eliminarPaciente(dni); // Método que debes implementar para eliminar de la BD
        detallesDialog.cerrarDialogo(); // Cierra el diálogo de detalles
        actualizarTabla(); // Método que debes implementar para refrescar la tabla
    });
        // Agregar listener para el botón de editar
        detallesDialog.addEditListener(e -> {
            editarPaciente(dni); // Pasar el dni del paciente o el objeto Paciente
            detallesDialog.cerrarDialogo(); // Cerrar el diálogo de detalles
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


            // Crea el controlador con los tres argumentos
            new PacienteEditarController(paciente, editarPacienteForm, this);

            editarPacienteForm.setVisible(true); // Mostrar el formulario de edición
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el paciente a editar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    // Método para eliminar el doctor de la base de datos
    private void eliminarPaciente(String dni) {
        // Crear una instancia del servicio de doctor
        IPacienteService pacienteService = new PacienteServiceImpl();

        // Obtener el doctor usando el número de colegiado
        Paciente paciente = pacienteService.obtenerTodos().stream()
                .filter(p -> p.getDni() != null && p.getDni().equals(dni))
                .findFirst()
                .orElse(null);

        if (paciente != null) {
            // Llamar al método del servicio para eliminar el doctor
            pacienteService.deletePaciente(paciente);

            // Actualizar la tabla después de la eliminación
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Paciente eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el paciente a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    // Método para actualizar la tabla de doctores
    private void actualizarTabla() {
        IPacienteService pacienteService = new PacienteServiceImpl(); // Crear una nueva instancia del servicio
        List<Paciente> pacientes = pacienteService.obtenerTodos(); // Obtener la lista actualizada de doctores
        setPacientesData(pacientes); // Llamar al método para llenar la tabla
    }

    // Método para llenar la tabla con la lista de pacientes
    public void setPacientesData(List<Paciente> pacientes) {
        tableModel.setRowCount(0);  // Limpiar los datos actuales de la tabla
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

    // Método para obtener los campos de filtro
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
