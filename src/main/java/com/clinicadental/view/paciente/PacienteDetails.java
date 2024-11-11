package com.clinicadental.view.paciente;

import com.clinicadental.common.design.ButtonDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class PacienteDetails extends JFrame {
    private JPanel contentPane;
    private JLabel nombreLabel;
    private JLabel apellidosLabel;
    private JLabel dniLabel;
    private JLabel telefonoLabel;
    private JLabel direccionLabel;
    private JLabel codPostalLabel;
    private JLabel emailLabel;
    private JButton buttonOK;
    private JButton editButton;
    private JButton deleteButton;

    public PacienteDetails(JFrame parent, String nombre, String apellidos, String dni, String telefono, String direccion, String codPostal, String email) {
        super("Detalles del Paciente");

        // Configurar el tamaño del JFrame para que coincida con MainScreen
        setSize(600, 400);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Configurar el panel principal con un borde y fondo gris claro
        contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPane.setBackground(new Color(240, 240, 240));

        // Título superior
        JLabel titleLabel = new JLabel("Detalles del Paciente", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(70, 130, 180));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        // Panel central con detalles del paciente
        JPanel detailsPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        detailsPanel.setOpaque(false);

        // Añadir etiquetas y valores de los detalles
        detailsPanel.add(new JLabel("Nombre:"));
        detailsPanel.add(nombreLabel = createValueLabel(nombre));
        detailsPanel.add(new JLabel("Apellidos:"));
        detailsPanel.add(apellidosLabel = createValueLabel(apellidos));
        detailsPanel.add(new JLabel("DNI:"));
        detailsPanel.add(dniLabel = createValueLabel(dni));
        detailsPanel.add(new JLabel("Teléfono:"));
        detailsPanel.add(telefonoLabel = createValueLabel(telefono));
        detailsPanel.add(new JLabel("Dirección:"));
        detailsPanel.add(direccionLabel = createValueLabel(direccion));
        detailsPanel.add(new JLabel("Código Postal:"));
        detailsPanel.add(codPostalLabel = createValueLabel(codPostal));
        detailsPanel.add(new JLabel("Email:"));
        detailsPanel.add(emailLabel = createValueLabel(email));

        // Panel para botones de acción con estilo y espaciado
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.setOpaque(false);
        buttonOK = new ButtonDesign("Cerrar");
        editButton = new ButtonDesign("Editar");
        deleteButton = new ButtonDesign("Eliminar");
        buttonPanel.add(buttonOK);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        // Agregar componentes al panel principal
        contentPane.add(detailsPanel, BorderLayout.CENTER);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        setContentPane(contentPane);

        // Acción para cerrar la ventana al presionar "OK"
        buttonOK.addActionListener(e -> dispose());
    }

    // Crear un JLabel estilizado para los valores de los detalles
    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    // Crear un botón estilizado para las acciones
    private JButton createActionButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(100, 35));
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    // Métodos para agregar listeners a los botones
    public void addEditListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    // Método para cerrar el JFrame
    public void cerrarDialogo() {
        this.dispose();
    }
}
