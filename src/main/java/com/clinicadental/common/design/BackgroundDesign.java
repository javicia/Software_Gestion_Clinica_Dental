package com.clinicadental.common.design;

import javax.swing.*;
import java.awt.*;

public class BackgroundDesign extends JPanel {

    private final Image backgroundImage;

    public BackgroundDesign(Image image) {
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

