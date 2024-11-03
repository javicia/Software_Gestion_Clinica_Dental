package com.clinicadental.view.citas;

import com.clinicadental.controller.citas.CitaAgregarController;
import com.clinicadental.model.Entity.Cita;
import com.clinicadental.service.ICitasService;
import com.clinicadental.service.impl.CitasServiceImpl;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GestionCita extends JFrame {
    private JTable citaTable;
    private DefaultTableModel tableModel;
    private JTextField[] filterFields;
    private JButton backButton;
    private JButton addButton;
    private JPanel mainPanel;
    private ICitasService citasService;

    public GestionCita() {
        citasService = new CitasServiceImpl();

        // Configuración de la ventana principal
        mainPanel = new JPanel(new BorderLayout());

        // Crear un panel con título para la tabla
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Listado de Citas",
                TitledBorder.CENTER,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 18),
                Color.BLACK
        ));

        // Crear la tabla con id_cita como columna oculta
        tableModel = new DefaultTableModel(new String[]{"ID", "Fecha", "Hora", "Paciente", "Doctor", "Motivo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Desactivar la edición de las celdas
            }
        };
        citaTable = new JTable(tableModel);
        citaTable.setFont(new Font("Arial", Font.PLAIN, 14));
        citaTable.setRowHeight(30);
        citaTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        citaTable.getTableHeader().setBackground(new Color(102, 178, 255));
        citaTable.getTableHeader().setForeground(Color.WHITE);
        citaTable.setSelectionBackground(new Color(204, 229, 255));

        // Ocultar la columna de ID para que no se muestre en la tabla
        citaTable.getColumnModel().getColumn(0).setMinWidth(0);
        citaTable.getColumnModel().getColumn(0).setMaxWidth(0);

        // Agregar un MouseListener para detectar doble clic y abrir detalles de la cita
        citaTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Detectar doble clic
                    int selectedRow = citaTable.getSelectedRow();
                    if (selectedRow != -1) {  // Si hay una fila seleccionada
                        abrirDetallesCita(selectedRow);  // Llamar al método para abrir los detalles de las citas
                    }
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(citaTable);

        // Panel de filtros debajo de la cabecera
        JPanel filterPanel = new JPanel(new GridLayout(1, 5));  // 5 columnas para los filtros
        filterFields = new JTextField[5];
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

        // Botón para añadir nueva cita
        addButton = new JButton("Añadir Cita");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abrirCitaForm();  // Llamar al método para abrir el formulario de añadir cita
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
        setTitle("Gestión de Citas");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Cargar datos de citas
        actualizarTabla();
    }

    // Método para abrir el formulario de añadir cita
    private void abrirCitaForm() {
        CitaAgregar citaForm = new CitaAgregar();
        new CitaAgregarController(citaForm, this); // Pasar la referencia de GestionCita para actualizar la tabla
        citaForm.setVisible(true);  // Asegurarse de que el formulario sea visible
    }

    // Método para abrir los detalles de una cita
    private void abrirDetallesCita(int rowIndex) {
        int idCita = (int) tableModel.getValueAt(rowIndex, 0);
        Cita cita = citasService.getCitaById(idCita);

        if (cita != null) {
            CitaDetails detallesDialog = new CitaDetails(this, cita.getFecha(), cita.getHora(),
                    cita.getPaciente().getNombre(),
                    cita.getDoctor().getNombre(),
                    cita.getMotivo());

            // Listener para el botón de eliminar
            detallesDialog.addDeleteListener(e -> {
                eliminarCita(idCita);
                detallesDialog.cerrarDialogo();
                actualizarTabla();
            });

            detallesDialog.setVisible(true);
        }
    }

    // Método para eliminar la cita de la base de datos
    private void eliminarCita(int idCita) {
        Cita cita = citasService.getCitaById(idCita);
        if (cita != null) {
            citasService.deleteCita(cita);
            JOptionPane.showMessageDialog(this, "La cita se eliminó exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo encontrar la cita a eliminar.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método para actualizar la tabla de citas
    private void actualizarTabla() {
        List<Cita> citas = citasService.getAllCitas();
        setCitasData(citas);
    }

    // Método para llenar la tabla con la lista de citas
    public void setCitasData(List<Cita> citas) {
        tableModel.setRowCount(0);  // Limpiar los datos actuales de la tabla
        for (Cita cita : citas) {
            Object[] row = {
                    cita.getId_cita(),
                    cita.getFecha(),
                    cita.getHora(),
                    cita.getPaciente().getNombre(),
                    cita.getDoctor().getNombre(),
                    cita.getMotivo()
            };
            tableModel.addRow(row);
        }
    }

    // Métodos para obtener los componentes de la vista
    public JTextField[] getFilterFields() {
        return filterFields;
    }

    public JTable getCitaTable() {
        return citaTable;
    }

    public JButton getBackButton() {
        return backButton;
    }

    public JButton getAddButton() {
        return addButton;
    }
}
