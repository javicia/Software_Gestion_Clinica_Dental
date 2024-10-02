package com.clinicadental.view.init;

import com.clinicadental.view.paciente.PacienteTable;
import com.clinicadental.controller.paciente.PacienteTableController;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class MainScreen extends JFrame {
    private JButton pacienteButton;
    private JButton doctorButton;
    private JButton citasButton;
    private JButton englishButton;
    private JButton spanishButton;
    private JButton catalanButton;
    private ResourceBundle bundle;
    private Locale currentLocale;

    public MainScreen() {
        // Establecer idioma inicial
        currentLocale = new Locale("es", "ES");
        cargarBundle();

        // Configuración básica de la ventana
        setTitle(bundle.getString("title"));
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Crear panel principal
        JPanel panel = new JPanel(new BorderLayout());

        // Crear un panel para los botones alineados a la izquierda
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));

        // Crear botones con textos traducidos
        pacienteButton = new JButton(bundle.getString("pacienteButton"));
        doctorButton = new JButton(bundle.getString("doctorButton"));
        citasButton = new JButton(bundle.getString("citasButton"));

        // Añadir los botones al buttonPanel
        buttonPanel.add(pacienteButton);
        buttonPanel.add(doctorButton);
        buttonPanel.add(citasButton);

        // Añadir el buttonPanel al lado izquierdo (WEST) del panel principal
        panel.add(buttonPanel, BorderLayout.WEST);

        // Crear panel para los botones de idiomas
        JPanel languagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        englishButton = crearBotonRedondo("images/botons/icon_uk.png");
        spanishButton = crearBotonRedondo("images/botons/icon_es.png");
        catalanButton = crearBotonRedondo("images/botons/icon_ca.png");

        languagePanel.add(englishButton);
        languagePanel.add(spanishButton);
        languagePanel.add(catalanButton);

        // Añadir el languagePanel en la parte superior
        panel.add(languagePanel, BorderLayout.NORTH);

        // Añadir el panel al frame
        add(panel);

        // Listener para el botón de Gestión de Pacientes
        pacienteButton.addActionListener(e -> abrirPacienteTable());

        // Listeners para cambiar el idioma
        englishButton.addActionListener(e -> cambiarIdioma(new Locale("en", "US")));
        spanishButton.addActionListener(e -> cambiarIdioma(new Locale("es", "ES")));
        catalanButton.addActionListener(e -> cambiarIdioma(new Locale("ca", "ES")));
    }

    // Método para cargar el ResourceBundle con la localización actual
    private void cargarBundle() {
        bundle = ResourceBundle.getBundle("translations.messages", currentLocale);
    }

    // Método para cambiar el idioma y actualizar la interfaz
    private void cambiarIdioma(Locale newLocale) {
        currentLocale = newLocale;
        cargarBundle();
        actualizarInterfaz();
    }

    // Método para actualizar los textos de la interfaz
    private void actualizarInterfaz() {
        setTitle(bundle.getString("title"));
        pacienteButton.setText(bundle.getString("pacienteButton"));
        doctorButton.setText(bundle.getString("doctorButton"));
        citasButton.setText(bundle.getString("citasButton"));
    }

    // Método para abrir la vista de la tabla de pacientes
    private void abrirPacienteTable() {
        PacienteTable pacienteTableView = new PacienteTable();
        new PacienteTableController(pacienteTableView);  // Controlador para gestionar la vista
        pacienteTableView.setVisible(true);  // Mostrar la ventana de PacienteTable
    }

    // Método para crear botones redondos con imágenes
    private JButton crearBotonRedondo(String iconPath) {
        URL imageUrl = getClass().getClassLoader().getResource(iconPath);
        System.out.println("Buscando imagen en: " + iconPath + ", URL obtenida: " + imageUrl);
        if (imageUrl != null) {
            System.out.println("Cargando imagen desde: " + imageUrl.toExternalForm());
            JButton button = new JButton(new ImageIcon(imageUrl));
            button.setPreferredSize(new Dimension(50, 50)); // Tamaño del botón
            button.setContentAreaFilled(false);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            return button;
        } else {
            System.out.println("No se pudo encontrar la imagen: " + iconPath);
            JButton button = new JButton("?");
            button.setPreferredSize(new Dimension(50, 50));
            return button;
        }
    }

    public static void main(String[] args) {
        MainScreen mainScreen = new MainScreen();
        mainScreen.setVisible(true);
    }
}
