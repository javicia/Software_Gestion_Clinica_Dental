package com.clinicadental.common.design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonDesign extends JButton {
    private Color backgroundColor;
    private Color hoverColor;
    private Color pressedColor;
    private Color textColor;

    public ButtonDesign(String text) {
        super(text);

        // Colores de fondo y texto por defecto
        this.backgroundColor = new Color(70, 130, 180); // Azul predeterminado
        this.hoverColor = new Color(30, 144, 255);      // Azul claro al pasar el mouse
        this.pressedColor = new Color(0, 102, 204);     // Azul oscuro al presionar
        this.textColor = Color.WHITE;

        setFocusPainted(false);  // Quitar borde de enfoque
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(textColor);
        setBackground(backgroundColor);
        setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Añadir efecto de elevación y color al pasar el mouse
        setContentAreaFilled(false);
        setOpaque(false);
        addMouseListener(new ButtonMouseListener());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Activar anti-aliasing para bordes suaves
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Definir color de fondo según el estado del botón
        if (getModel().isPressed()) {
            g2.setColor(pressedColor);
        } else if (getModel().isRollover()) {
            g2.setColor(hoverColor);
        } else {
            g2.setColor(backgroundColor);
        }

        // Dibujar botón con esquinas redondeadas (menos redondas, radio más pequeño)
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Menos redondeo, radio 15

        super.paintComponent(g2);
        g2.dispose();
    }

    // Añadir efecto de elevación
    private class ButtonMouseListener extends MouseAdapter {
        @Override
        public void mouseEntered(MouseEvent e) {
            // Ajustar la elevación del botón al pasar el mouse
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(100, 100, 100, 80), 2), // Borde más sutil
                    BorderFactory.createEmptyBorder(6, 14, 6, 14)
            ));
            setBackground(new Color(70, 130, 180, 180)); // Aumentar opacidad para simular elevación
            repaint();
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
            setBackground(backgroundColor); // Volver al color original
            repaint();
        }
    }
}
