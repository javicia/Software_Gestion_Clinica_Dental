package com.clinicadental.common.design;

import com.clinicadental.common.Constans;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class JTextFieldSearchDesign {

    /**
     * Crea un JTextField con un diseño de borde redondeado y un icono de búsqueda.
     *
     * @return Un JPanel que contiene el JTextField y el ícono de búsqueda.
     */
    public static JPanel createSearchField() {
        // Crear el JTextField con diseño redondeado
        JTextField globalFilterField = new JTextField();
        globalFilterField.setBorder(new RoundedBorder(10)); // Borde redondeado con radio de 10 px
        globalFilterField.setFont(new Font("Arial", Font.PLAIN, 14));
        globalFilterField.setPreferredSize(new Dimension(200, 30));
        globalFilterField.setMargin(new Insets(5, 10, 5, 10));

        // Crear el icono de búsqueda
        JLabel searchIcon;
        try {
            searchIcon = new JLabel(new ImageIcon(new ImageIcon(
                    JTextFieldSearchDesign.class.getClassLoader().getResource(Constans.ICON_SEARCH_IMAGE_PATH))
                    .getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        } catch (Exception e) {
            searchIcon = new JLabel("🔍"); // Icono alternativo si el recurso no se encuentra
        }
        searchIcon.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Panel para combinar el JTextField y el ícono
        JPanel filterPanel = new JPanel(new BorderLayout());
        filterPanel.setOpaque(false);
        filterPanel.add(globalFilterField, BorderLayout.CENTER);
        filterPanel.add(searchIcon, BorderLayout.EAST);

        return filterPanel;
    }

    /**
     * Clase interna para el borde redondeado.
     */
    private static class RoundedBorder extends AbstractBorder {
        private final int radius;

        public RoundedBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(Color.GRAY); // Color del borde
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 10, 5, 10);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = insets.top = insets.bottom = 10;
            return insets;
        }
    }
}

