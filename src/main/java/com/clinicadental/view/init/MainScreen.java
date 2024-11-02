package com.clinicadental.view.init;

import com.clinicadental.controller.doctor.GestionDoctorController;
import com.clinicadental.view.doctor.GestionDoctor;
import com.clinicadental.view.paciente.GestionPaciente;
import com.clinicadental.controller.paciente.GestionPacienteController;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {
    private JButton pacienteButton;
    private JButton doctorButton;
    private JButton citasButton;

    public MainScreen() {
        // Configuración básica de la ventana
        setTitle("Clínica Dental - Gestión Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Crear un panel para los botones
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10)); // Organizar los botones verticalmente con espacios

        // Crear botones
        pacienteButton = new JButton("Gestión de Pacientes");
        doctorButton = new JButton("Gestión de Doctores");
        citasButton = new JButton("Gestión de Citas");

        // Añadir los botones al buttonPanel
        buttonPanel.add(pacienteButton);
        buttonPanel.add(doctorButton);
        buttonPanel.add(citasButton);

        // Añadir el buttonPanel al lado izquierdo (WEST) del panel principal
        panel.add(buttonPanel, BorderLayout.WEST);

        // Listener para el botón de Gestión de Pacientes
        pacienteButton.addActionListener(e -> abrirPacienteTable());
        doctorButton.addActionListener(e -> abrirDoctoresTable());
        // Listener para Gestión de Citas (agrega lógica aquí si es necesario)

        setContentPane(panel);
    }

    // Método para abrir la vista de la tabla de pacientes
    private void abrirPacienteTable() {
        GestionPaciente pacienteTableView = new GestionPaciente();
        new GestionPacienteController(pacienteTableView);  // Controlador para gestionar la vista
        pacienteTableView.setVisible(true);  // Mostrar la ventana de PacienteTable
    }

    // Método para abrir la vista de la tabla de doctores
    private void abrirDoctoresTable() {
        GestionDoctor doctorTableView = new GestionDoctor();
        new GestionDoctorController(doctorTableView);  // Controlador para gestionar la vista
        doctorTableView.setVisible(true);  // Mostrar la ventana de DoctorTable
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
        });
    }
}
