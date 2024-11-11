package com.clinicadental.view.citas;

import com.clinicadental.common.design.ButtonDesign;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;

public class CitaDetails extends JDialog {
    private JPanel contentPane;
    private JLabel fechaLabel;
    private JLabel horaLabel;
    private JLabel pacienteLabel;
    private JLabel doctorLabel;
    private JLabel motivoLabel;
    private JButton buttonOK;
    private JButton editButton;
    private JButton deleteButton;

    public CitaDetails(JFrame parent, Date fecha, Date hora, String paciente, String doctor, String motivo) {
        super(parent, "Detalles de la Cita", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(parent);

        // Configuración del panel principal
        contentPane = new JPanel(new BorderLayout());
        contentPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPane.setBackground(new Color(240, 240, 240));

        // Título superior
        JLabel titleLabel = new JLabel("Detalles de la Cita", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(new Color(70, 130, 180));
        contentPane.add(titleLabel, BorderLayout.NORTH);

        // Panel central con detalles de la cita
        JPanel detailsPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        detailsPanel.setOpaque(false);

        // Añadir etiquetas y valores de los detalles
        detailsPanel.add(new JLabel("Fecha:"));
        detailsPanel.add(fechaLabel = createValueLabel(fecha.toString()));
        detailsPanel.add(new JLabel("Hora:"));
        detailsPanel.add(horaLabel = createValueLabel(hora.toString()));
        detailsPanel.add(new JLabel("Paciente:"));
        detailsPanel.add(pacienteLabel = createValueLabel(paciente));
        detailsPanel.add(new JLabel("Doctor:"));
        detailsPanel.add(doctorLabel = createValueLabel(doctor));
        detailsPanel.add(new JLabel("Motivo:"));
        detailsPanel.add(motivoLabel = createValueLabel(motivo));

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

        // Acción para cerrar la ventana al presionar "Cerrar"
        buttonOK.addActionListener(e -> dispose());
    }

    // Crear un JLabel estilizado para los valores de los detalles
    private JLabel createValueLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 16));
        label.setForeground(new Color(50, 50, 50));
        return label;
    }

    // Métodos para agregar listeners a los botones
    public void addEditListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void addDeleteListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    // Método para cerrar el diálogo
    public void cerrarDialogo() {
        this.dispose();
    }
}
