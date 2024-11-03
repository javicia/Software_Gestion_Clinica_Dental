package com.clinicadental.view.doctor;

import com.clinicadental.controller.doctor.DoctorAgregarController;
import com.clinicadental.controller.doctor.DoctorEditarController;
import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.impl.DoctorServiceImpl;

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

        // Agregar un MouseListener para detectar doble clic y abrir detalles del doctor
        doctorTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Detectar doble clic
                    int selectedRow = doctorTable.getSelectedRow();
                    if (selectedRow != -1) {  // Si hay una fila seleccionada
                        abrirDetallesDoctor(selectedRow);  // Llamar al método para abrir los detalles del doctor
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(doctorTable);

        // Panel de filtros debajo de la cabecera
        JPanel filterPanel = new JPanel(new GridLayout(1, 8));  // 8 columnas para los filtros
        filterFields = new JTextField[8];
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

        // Botón para añadir nuevo doctor
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
        setTitle("Gestión de Doctores");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    // Método para abrir el formulario de añadir doctor
    private void abrirDoctorForm() {
        DoctorAgregar doctorForm = new DoctorAgregar();
        new DoctorAgregarController(doctorForm, this); // Pasar la referencia de GestionDoctor para actualizar la tabla
        doctorForm.setVisible(true);  // Asegurarse de que el formulario sea visible
    }

    // En GestionDoctor.java, método abrirDetallesDoctor
    private void abrirDetallesDoctor(int rowIndex) {
        String nombre = doctorTable.getValueAt(rowIndex, 0) != null ? doctorTable.getValueAt(rowIndex, 0).toString() : "N/A";
        String apellidos = doctorTable.getValueAt(rowIndex, 1) != null ? doctorTable.getValueAt(rowIndex, 1).toString() : "N/A";
        String dni = doctorTable.getValueAt(rowIndex, 2) != null ? doctorTable.getValueAt(rowIndex, 2).toString() : "N/A";
        String telefono = doctorTable.getValueAt(rowIndex, 3) != null ? doctorTable.getValueAt(rowIndex, 3).toString() : "N/A";
        String direccion = doctorTable.getValueAt(rowIndex, 4) != null ? doctorTable.getValueAt(rowIndex, 4).toString() : "N/A";
        String codPostal = doctorTable.getValueAt(rowIndex, 5) != null ? doctorTable.getValueAt(rowIndex, 5).toString() : "N/A";
        String email = doctorTable.getValueAt(rowIndex, 6) != null ? doctorTable.getValueAt(rowIndex, 6).toString() : "N/A";
        Integer numColegiado = doctorTable.getValueAt(rowIndex, 7) != null ? Integer.parseInt(doctorTable.getValueAt(rowIndex, 7).toString()) : null;

        System.out.println("Número de Colegiado: " + numColegiado); // Imprime el valor para verificarlo

        // Abrir la ventana de detalles del doctor
        DoctorDetails detallesDialog = new DoctorDetails(this, nombre, apellidos, dni, telefono, direccion, codPostal, email, numColegiado);

        // Agregar listener para el botón de eliminar
        detallesDialog.addDeleteListener(e -> {
        // Lógica para eliminar el registro de la base de datos
        eliminarDoctor(numColegiado); // Método que debes implementar para eliminar de la BD
        detallesDialog.cerrarDialogo(); // Cierra el diálogo de detalles
        actualizarTabla(); // Método que debes implementar para refrescar la tabla
    });

        // Agregar listener para el botón de editar
        detallesDialog.addEditListener(e -> {
            editarDoctor(numColegiado); // Pasar el número de colegiado o el objeto Doctor
            detallesDialog.cerrarDialogo(); // Cerrar el diálogo de detalles
        });
 detallesDialog.setVisible(true);
    }

    // Método para eliminar el doctor de la base de datos
    private void eliminarDoctor(Integer numColegiado) {
        // Crear una instancia del servicio de doctor
        IDoctorService doctorService = new DoctorServiceImpl();

        // Obtener el doctor usando el número de colegiado
        Doctor doctor = doctorService.getAllDoctor().stream()
                .filter(d -> d.getNumColegiado() != null && d.getNumColegiado().equals(numColegiado))
                .findFirst()
                .orElse(null);

        if (doctor != null) {
            // Llamar al método del servicio para eliminar el doctor
            doctorService.deleteDoctor(doctor);

            // Actualizar la tabla después de la eliminación
            actualizarTabla(); // Llama al método para refrescar la tabla
            JOptionPane.showMessageDialog(this, "Doctor eliminado exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el doctor a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarDoctor(Integer numColegiado) {
        IDoctorService doctorService = new DoctorServiceImpl();
        Doctor doctor = doctorService.getAllDoctor().stream()
                .filter(d -> d.getNumColegiado() != null && d.getNumColegiado().equals(numColegiado))
                .findFirst()
                .orElse(null);

        if (doctor != null) {
            DoctorEditar editarDoctorForm = new DoctorEditar();
            editarDoctorForm.getNombreField().setText(doctor.getNombre());
            editarDoctorForm.getApellidosField().setText(doctor.getApellidos());
            editarDoctorForm.getDniField().setText(doctor.getDni());
            editarDoctorForm.getTelefonoField().setText(doctor.getTelefono());
            editarDoctorForm.getDireccionField().setText(doctor.getDireccion());
            editarDoctorForm.getCodPostalField().setText(String.valueOf(doctor.getCodPostal()));
            editarDoctorForm.getEmailField().setText(doctor.getEmail());
            editarDoctorForm.getNumColegiadoField().setText(String.valueOf(doctor.getNumColegiado()));

            // Crea el controlador con los tres argumentos
            new DoctorEditarController(doctor, editarDoctorForm, this);

            editarDoctorForm.setVisible(true); // Mostrar el formulario de edición
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar el doctor a editar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



    // Método para actualizar la tabla de doctores
    private void actualizarTabla() {
        IDoctorService doctorService = new DoctorServiceImpl(); // Crear una nueva instancia del servicio
        List<Doctor> doctores = doctorService.getAllDoctor(); // Obtener la lista actualizada de doctores
        setDoctoresData(doctores); // Llamar al método para llenar la tabla
    }



    // Método para llenar la tabla con la lista de doctores
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

    public JTable getDoctorTable() {
        return doctorTable;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAddButton() {
        return addButton;
    }
}
