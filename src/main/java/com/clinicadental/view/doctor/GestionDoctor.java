package com.clinicadental.view.doctor;

import com.clinicadental.common.Constans;
import com.clinicadental.common.design.ButtonDesign;
import com.clinicadental.common.design.GradientDesign;
import com.clinicadental.common.design.JTextFieldSearchDesign;
import com.clinicadental.controller.doctor.DoctorAgregarController;
import com.clinicadental.controller.doctor.DoctorDetailsController;
import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.service.impl.DoctorServiceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class GestionDoctor extends JFrame {
    private JTable doctorTable;
    private DefaultTableModel tableModel;
    private JTextField globalFilterField;
    private JPanel filterPanel;
    private JButton backButton;
    private JButton addButton;

    public GestionDoctor() {
        setupUI();
        new DoctorDetailsController(this); // Asocia el controlador con esta vista
        actualizarTabla();
    }

    private void setupUI() {
        GradientDesign mainPanel = new GradientDesign(new Color(0, 102, 204), new Color(153, 204, 255));
        mainPanel.setLayout(new BorderLayout());

        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setOpaque(false);
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Listado de Doctores",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                Color.BLACK
        ));

        setIconImage(new ImageIcon(getClass().getResource(Constans.ICON_LOGO_IMAGE_PATH)).getImage());

        // Crear la tabla
        tableModel = new DefaultTableModel(new String[]{
                "Nombre", "Apellidos", "DNI", "Teléfono", "Dirección", "Código Postal", "Email", "NumColegiado"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desactivar edición de las celdas
            }
        };

        doctorTable = new JTable(tableModel);
        doctorTable.setFont(new Font("Arial", Font.PLAIN, 14));
        doctorTable.setRowHeight(30);
        doctorTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        doctorTable.getTableHeader().setBackground(new Color(102, 178, 255));
        doctorTable.getTableHeader().setForeground(Color.WHITE);
        doctorTable.setSelectionBackground(new Color(204, 229, 255));

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        // Crear campo de filtro global
        filterPanel = JTextFieldSearchDesign.createSearchField();
        // Obtener el JTextField del panel y asignarlo a globalFilterField
        globalFilterField = (JTextField) filterPanel.getComponent(0); // Extraer el JTextField del panel

        // Configuración de los botones "Añadir Doctor" y "Volver"
        addButton = new ButtonDesign("Añadir Doctor");
        addButton.addActionListener(e -> abrirDoctorForm());

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
        mainPanel.add(tablePanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setTitle("Gestión de Doctores");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // Método para abrir el formulario de añadir doctor
    private void abrirDoctorForm() {
        DoctorAgregar doctorForm = new DoctorAgregar();
        new DoctorAgregarController(doctorForm, this);
        doctorForm.setVisible(true);
    }

    // Método para actualizar la tabla
    public void actualizarTabla() {
        List<Doctor> doctores = new DoctorServiceImpl().getAllDoctor();
        setDoctoresData(doctores);
    }

    public void setDoctoresData(List<Doctor> doctores) {
        tableModel.setRowCount(0);
        for (Doctor doctor : doctores) {
            tableModel.addRow(new Object[]{
                    doctor.getNombre(),
                    doctor.getApellidos(),
                    doctor.getDni(),
                    doctor.getTelefono(),
                    doctor.getDireccion(),
                    doctor.getCodPostal(),
                    doctor.getEmail(),
                    doctor.getNumColegiado()
            });
        }
    }

    public JTable getDoctorTable() {
        return doctorTable;
    }

    public JTextField getGlobalFilterField() {
        return globalFilterField;
    }

    public JButton getAddButton() {
        return addButton;
    }


}
