package com.clinicadental.view.init;

import com.clinicadental.common.Constan;
import com.clinicadental.controller.citas.GestionCitaController;
import com.clinicadental.controller.doctor.GestionDoctorController;
import com.clinicadental.controller.paciente.GestionPacienteController;
import com.clinicadental.utils.BackgroundPanel;
import com.clinicadental.view.citas.GestionCita;
import com.clinicadental.view.doctor.GestionDoctor;
import com.clinicadental.view.paciente.GestionPaciente;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainScreen extends JFrame {
    private JButton pacienteButton;
    private JButton doctorButton;
    private JButton citasButton;

    public MainScreen() {
        setTitle("Clínica Dental Dr.Daza Rodas - Gestión Principal");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Establecer el icono de la aplicación
        setIconImage(new ImageIcon(getClass().getResource(Constan.ICON_LOGO_IMAGE_PATH)).getImage());

        // Crear panel principal con imagen de fondo y ajuste de escala
        BackgroundPanel panel = new BackgroundPanel(new ImageIcon(getClass().getResource(Constan.ICON_FONDO_IMAGE_PATH)).getImage());

        // Crear un panel para los botones con transparencia y diseño compacto
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 5, 5)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
                g2d.setColor(getBackground());
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        buttonPanel.setOpaque(false); // Hacer transparente para ver el fondo

        // Crear botones más compactos con transparencia
        pacienteButton = createCompactButton("Gestión de Pacientes");
        doctorButton = createCompactButton("Gestión de Doctores");
        citasButton = createCompactButton("Gestión de Citas");

        // Añadir los botones al buttonPanel
        buttonPanel.add(pacienteButton);
        buttonPanel.add(doctorButton);
        buttonPanel.add(citasButton);

        // Añadir el buttonPanel al lado izquierdo del panel principal
        panel.setLayout(new BorderLayout());
        panel.add(buttonPanel, BorderLayout.WEST);

        // Asignar eventos a los botones
        pacienteButton.addActionListener(e -> abrirPacienteTable());
        doctorButton.addActionListener(e -> abrirDoctoresTable());
        citasButton.addActionListener(e -> abrirCitasTable());

        setContentPane(panel);
    }

    private JButton createCompactButton(String text) {
        // Redimensionar el icono a 20x20 píxeles
        ImageIcon icon = new ImageIcon(new ImageIcon(getClass().getResource(Constan.ICON_LOGO_IMAGE_PATH))
                .getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH));

        JButton button = new JButton(text, icon);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);
        button.setPreferredSize(new Dimension(150, 20)); // Tamaño más pequeño
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Añadir efecto de cambio de color y elevación
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setContentAreaFilled(true);
                button.setBackground(new Color(0, 153, 204, 180));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setContentAreaFilled(false);
            }
        });
        return button;
    }

    private void abrirPacienteTable() {
        GestionPaciente pacienteTableView = new GestionPaciente();
        new GestionPacienteController(pacienteTableView);
        pacienteTableView.setVisible(true);
    }

    private void abrirDoctoresTable() {
        GestionDoctor doctorTableView = new GestionDoctor();
        new GestionDoctorController(doctorTableView);
        doctorTableView.setVisible(true);
    }

    private void abrirCitasTable() {
        GestionCita citaTableView = new GestionCita();
        new GestionCitaController(citaTableView);
        citaTableView.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainScreen mainScreen = new MainScreen();
            mainScreen.setVisible(true);
        });
    }
}

