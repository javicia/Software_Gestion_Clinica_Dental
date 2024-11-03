package com.clinicadental.view.citas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;

public class CitaDetails extends JFrame {
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
        super("Detalles de las Citas");

        // Configurar el tamaño del JFrame para que coincida con MainScreen
        setSize(800, 600);
        setLocationRelativeTo(parent);  // Centrar la ventana en la pantalla

        contentPane = new JPanel(new GridLayout(10, 2));  // Usar GridLayout para organizar los elementos

        // Inicializar etiquetas con los valores
        fechaLabel = new JLabel(fecha.toString());
        horaLabel = new JLabel(hora.toString());
        pacienteLabel = new JLabel(paciente);
        doctorLabel = new JLabel(doctor);
        motivoLabel = new JLabel(motivo);


        // Inicializar botones
        buttonOK = new JButton("OK");
        editButton = new JButton("Editar");
        deleteButton = new JButton("Eliminar");

        // Añadir componentes al panel
        contentPane.add(new JLabel("Fecha:"));
        contentPane.add(fechaLabel);
        contentPane.add(new JLabel("Hora:"));
        contentPane.add(horaLabel);
        contentPane.add(new JLabel("Paciente:"));
        contentPane.add(pacienteLabel);
        contentPane.add(new JLabel("Doctor:"));
        contentPane.add(doctorLabel);
        contentPane.add(new JLabel("Motivo:"));
        contentPane.add(motivoLabel);


        // Añadir botones
        contentPane.add(buttonOK);
        contentPane.add(editButton);
        contentPane.add(deleteButton);

        setContentPane(contentPane);  // Establecer el panel como el contenido de la ventana

        // Acción para cerrar la ventana al presionar "OK"
        buttonOK.addActionListener(e -> dispose());
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
