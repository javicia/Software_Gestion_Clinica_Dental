package com.clinicadental.utils;

import com.toedter.calendar.JCalendar;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarUtils {

    // Método para mostrar el JCalendar en un JDialog y actualizar el JTextField con la fecha seleccionada
    public static void mostrarCalendario(JFrame parent, JTextField fechaField) {
        JDialog dialog = new JDialog(parent, "Seleccionar Fecha", true);
        dialog.setSize(300, 300);
        dialog.setLocationRelativeTo(parent);

        JCalendar calendar = new JCalendar();
        calendar.addPropertyChangeListener("calendar", evt -> {
            Date selectedDate = calendar.getDate();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            fechaField.setText(sdf.format(selectedDate));
            dialog.dispose();
        });

        dialog.add(calendar);
        dialog.setVisible(true);
    }

    // Método para mostrar el selector de tiempo en intervalos de 15 minutos y actualizar el JTextField con la hora seleccionada
    public static void mostrarSelectorHora(JFrame parent, JTextField horaField) {
        JDialog dialog = new JDialog(parent, "Seleccionar Hora", true);
        dialog.setSize(200, 150);
        dialog.setLocationRelativeTo(parent);

        // Crear un modelo con intervalos de 15 minutos
        DefaultComboBoxModel<String> timeModel = new DefaultComboBoxModel<>();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);

        int selectedIndex = 0;
        Calendar now = Calendar.getInstance();
        now.set(Calendar.SECOND, 0);
        now.set(Calendar.MILLISECOND, 0);

        // Llenar el modelo y encontrar el índice más cercano a la hora actual
        for (int i = 0; i < 96; i++) { // 24 horas * 4 intervalos de 15 minutos
            timeModel.addElement(sdf.format(cal.getTime()));
            if (cal.getTimeInMillis() <= now.getTimeInMillis()) {
                selectedIndex = i; // Mantener el índice más cercano a la hora actual
            }
            cal.add(Calendar.MINUTE, 15);
        }

        JComboBox<String> timeComboBox = new JComboBox<>(timeModel);
        timeComboBox.setSelectedIndex(selectedIndex); // Seleccionar el índice más cercano a la hora actual

        JButton selectButton = new JButton("Seleccionar");
        selectButton.addActionListener(e -> {
            String selectedTime = (String) timeComboBox.getSelectedItem();
            if (selectedTime != null) {
                horaField.setText(selectedTime);
                dialog.dispose(); // Cerrar el diálogo después de seleccionar la hora
            }
        });

        dialog.setLayout(new BorderLayout());
        dialog.add(timeComboBox, BorderLayout.CENTER);
        dialog.add(selectButton, BorderLayout.SOUTH);
        dialog.setVisible(true);
    }
}
