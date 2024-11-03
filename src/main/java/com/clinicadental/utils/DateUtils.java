package com.clinicadental.utils;

import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private static final SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

    // Método para formatear fechas
    public static String formatFecha(Date date) {
        return dateFormat.format(date);
    }

    // Método para formatear horas
    public static String formatHora(Date date) {
        return timeFormat.format(date);
    }

    // Método para analizar fechas desde texto
    public static Date parseFecha(String fechaText) {
        try {
            return dateFormat.parse(fechaText);
        } catch (ParseException e) {
            return null; // Retorna null si la fecha no es válida
        }
    }

    // Clase interna para formatear la fecha seleccionada en el JDatePicker
    private static class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
        @Override
        public Object stringToValue(String text) throws ParseException {
            return dateFormat.parse(text);
        }

        @Override
        public String valueToString(Object value) {
            if (value instanceof Date) {
                return dateFormat.format((Date) value);
            }
            return ""; // Devuelve una cadena vacía si el valor no es una fecha
        }
    }
}
