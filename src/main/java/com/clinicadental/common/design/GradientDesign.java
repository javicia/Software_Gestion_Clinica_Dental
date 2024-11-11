package com.clinicadental.common.design;

import javax.swing.*;
import java.awt.*;

public class GradientDesign extends JPanel {
    private final Color color1;
    private final Color color2;

    public GradientDesign(Color color1, Color color2) {
        this.color1 = new Color(color1.getRed(), color1.getGreen(), color1.getBlue(), 150); // Transparencia
        this.color2 = new Color(color2.getRed(), color2.getGreen(), color2.getBlue(), 150); // Transparencia
        setOpaque(false); // Aseguramos transparencia
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        // Crear un degradado de color1 a color2
        GradientPaint gradientPaint = new GradientPaint(0, 0, color1, 0, height, color2);
        g2d.setPaint(gradientPaint);
        g2d.fillRect(0, 0, width, height);

        super.paintComponent(g);
    }
}

