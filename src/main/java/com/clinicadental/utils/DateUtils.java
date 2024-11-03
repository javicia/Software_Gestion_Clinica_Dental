package com.clinicadental.utils;

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
}
