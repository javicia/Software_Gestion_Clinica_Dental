package com.clinicadental.init;

import com.clinicadental.utils.HibernateUtil;
import com.clinicadental.view.init.MainScreen;

public class Main {
    public static void main(String[] args) {

        MainScreen mainScreen = new MainScreen();
        mainScreen.setVisible(true);

        // Añadir un shutdown hook para cerrar la SessionFactory al finalizar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            HibernateUtil.getSessionFactory().close();
        }));
    }

    }
