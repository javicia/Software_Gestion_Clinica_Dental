package com.clinicadental.utils;

import java.util.Locale;
import java.util.ResourceBundle;

public class Internalizacion {
    public static void main(String[] args) {
        // Cargar el bundle de recursos para inglés
        Locale locale = new Locale("en", "US");
        ResourceBundle bundle = ResourceBundle.getBundle("translations.messages", locale);

        // Obtener y mostrar el mensaje en inglés
        System.out.println(bundle.getString("greeting"));  // Imprime: Hello

        // Cambiar a español
        locale = new Locale("es", "ES");
        bundle = ResourceBundle.getBundle("translations.messages", locale);

        // Obtener y mostrar el mensaje en español
        System.out.println(bundle.getString("greeting"));  // Imprime: Hola

        // Cambiar a catalán
        locale = new Locale("ca", "ES");  // Catalán de España
        bundle = ResourceBundle.getBundle("translations.messages", locale);

        // Obtener y mostrar el mensaje en catalán
        System.out.println(bundle.getString("greeting"));  // Imprime: Hola (o la traducción en catalán)
    }
}
