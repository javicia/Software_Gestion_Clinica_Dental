package com.clinicadental.view.citas;

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
        super(parent, "Detalles de las Citas", true);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Se asegura de cerrar al llamar dispose()

        setSize(800, 600);
        setLocationRelativeTo(parent);

        contentPane = new JPanel(new GridLayout(10, 2));

        fechaLabel = new JLabel(fecha.toString());
        horaLabel = new JLabel(hora.toString());
        pacienteLabel = new JLabel(paciente);
        doctorLabel = new JLabel(doctor);
        motivoLabel = new JLabel(motivo);

        buttonOK = new JButton("OK");
        editButton = new JButton("Editar");
        deleteButton = new JButton("Eliminar");

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

        contentPane.add(buttonOK);
        contentPane.add(editButton);
        contentPane.add(deleteButton);

        setContentPane(contentPane);

        buttonOK.addActionListener(e -> dispose()); // Solo llamamos a dispose()
    }

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
