package com.clinicadental.view.doctor;

import com.clinicadental.controller.paciente.PacienteAgregarController;
import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.view.paciente.PacienteAgregar;
import com.clinicadental.view.paciente.PacienteDetails;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionDoctor extends JFrame {
    private JTable doctorTable;
    private DefaultTableModel tableModel;
    private JTextField[] filterFields;
    private JButton backButton;
    private JButton addButton;
    private JPanel mainPanel;

    public GestionDoctor() {
        // Configuración de la ventana principal
        mainPanel = new JPanel(new BorderLayout());

        // Crear un panel con título para la tabla
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Listado de Doctores",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                Color.BLACK
        ));

        // Crear la tabla
        tableModel = new DefaultTableModel(new String[]{"Nombre", "Apellidos", "DNI", "Teléfono", "Dirección", "Código Postal", "Email", "NumColegiado"}, 0);
        doctorTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desactivar la edición de las celdas
            }
        };
        doctorTable.setFont(new Font("Arial", Font.PLAIN, 14));
        doctorTable.setRowHeight(30);
        doctorTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        doctorTable.getTableHeader().setBackground(new Color(102, 178, 255));
        doctorTable.getTableHeader().setForeground(Color.WHITE);
        doctorTable.setSelectionBackground(new Color(204, 229, 255));

        // Agregar un MouseListener para detectar doble clic y abrir detalles del paciente
        doctorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Detectar doble clic
                    int selectedRow = doctorTable.getSelectedRow();
                    if (selectedRow != -1) {  // Si hay una fila seleccionada
                        abrirDetallesPaciente(selectedRow);  // Llamar al método para abrir los detalles del paciente
                    }
                }
            }
        });
        JScrollPane scrollPane = new JScrollPane(doctorTable);

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

        // Botón para añadir nuevo paciente
        addButton = new JButton("Añadir Doctor");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirDoctorForm();  // Llamar al método para abrir el formulario de añadir doctor
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
        setTitle("Gestión de Doctor");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Método para abrir el formulario de añadir paciente
    private void abrirDoctorForm() {
        DoctorAgregar doctorForm = new DoctorAgregar();
        new DoctorAgregarController(doctorForm, this); // Pasar la referencia de PacienteTable para actualizar la tabla
        doctorForm.setVisible(true);  // Asegurarse de que el formulario sea visible
    }

    // Método para abrir detalles del paciente seleccionado
    private void abrirDetallesPaciente(int rowIndex) {
        // Obtener los datos de la fila seleccionada, manejando valores null
        String nombre = doctorTable.getValueAt(rowIndex, 0) != null ? doctorTable.getValueAt(rowIndex, 0).toString() : "N/A";
        String apellidos = doctorTable.getValueAt(rowIndex, 1) != null ? doctorTable.getValueAt(rowIndex, 1).toString() : "N/A";
        String dni = doctorTable.getValueAt(rowIndex, 2) != null ? doctorTable.getValueAt(rowIndex, 2).toString() : "N/A";
        String telefono = doctorTable.getValueAt(rowIndex, 3) != null ? doctorTable.getValueAt(rowIndex, 3).toString() : "N/A";
        String direccion = doctorTable.getValueAt(rowIndex, 4) != null ? doctorTable.getValueAt(rowIndex, 4).toString() : "N/A";
        String codPostal = doctorTable.getValueAt(rowIndex, 5) != null ? doctorTable.getValueAt(rowIndex, 5).toString() : "N/A";
        String email = doctorTable.getValueAt(rowIndex, 6) != null ? doctorTable.getValueAt(rowIndex, 6).toString() : "N/A";

        // Abrir la ventana de detalles
        PacienteDetails detallesDialog = new PacienteDetails(this, nombre, apellidos, dni, telefono, direccion, codPostal, email);
        detallesDialog.setVisible(true);
    }

    // Método para llenar la tabla con la lista de pacientes
    public void setDoctoresData(List<Doctor> doctores) {
        tableModel.setRowCount(0);  // Limpiar los datos actuales de la tabla
        for (Doctor doctor : doctores) {
            Object[] row = {
                    doctor.getNombre(),
                    doctor.getApellidos(),
                    doctor.getDni(),
                    doctor.getTelefono(),
                    doctor.getDireccion(),
                    doctor.getCodPostal(),
                    doctor.getEmail(),
                    doctor.getNumColegiado()
            };
            tableModel.addRow(row);
        }
    }

    // Método para obtener los campos de filtro
    public JTextField[] getFilterFields() {
        return filterFields;
    }

    public JTable getPacienteTable() {
        return doctorTable;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAddButton() {
        return addButton;
    }
}
