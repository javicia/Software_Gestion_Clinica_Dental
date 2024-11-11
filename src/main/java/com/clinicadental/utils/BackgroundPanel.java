package com.clinicadental.utils;

import javax.swing.*;
import java.awt.*;

public class BackgroundPanel extends JPanel {

    private final Image backgroundImage;

    public BackgroundPanel(Image image) {
        this.backgroundImage = image;
        setLayout(new BorderLayout());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Escalar imagen para cubrir todo el panel
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}

