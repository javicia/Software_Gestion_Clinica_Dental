package com.clinicadental.utils;

import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.service.IDoctorService;
import com.clinicadental.service.IPacienteService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class ValidatorUtil {

    // Validación de DNI (8 dígitos seguidos de una letra)
    public static boolean validarDNI(String dni) {
        String dniPattern = "\\d{8}[A-Za-z]";
        return dni != null && Pattern.matches(dniPattern, dni);
    }

    // Validación de teléfono (solo números, longitud entre 9 y 12 dígitos)
    public static boolean validarTelefono(String telefono) {
        String telefonoPattern = "\\d{9,12}";
        return telefono != null && Pattern.matches(telefonoPattern, telefono);
    }

    // Validación de email (formato estándar)
    public static boolean validarEmail(String email) {
        String emailPattern = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return email != null && Pattern.matches(emailPattern, email);
    }

    // Validación de número de colegiado (debe ser un número entero positivo)
    public static boolean validarNumColegiado(String numColegiado) {
        try {
            int numero = Integer.parseInt(numColegiado);
            return numero > 0; // Debe ser positivo
        } catch (NumberFormatException e) {
            return false; // No es un número válido
        }
    }

    // Validación de fecha (Formato dd/MM/yyyy)
    public static Date validarFecha(String fecha) {
        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd/MM/yyyy");
        formatoFecha.setLenient(false);
        try {
            return formatoFecha.parse(fecha);
        } catch (ParseException e) {
            return null;
        }
    }

    // Validación de hora (Formato HH:mm)
    public static Date validarHora(String hora) {
        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
        formatoHora.setLenient(false);
        try {
            return formatoHora.parse(hora);
        } catch (ParseException e) {
            return null;
        }
    }

    // Validación de existencia de paciente
    public static Paciente validarPaciente(String nombrePaciente, IPacienteService pacienteService) {
        List<Paciente> pacientes = pacienteService.findListPacienteByName(nombrePaciente);
        if (pacientes == null || pacientes.isEmpty()) {
            return null;
        } else if (pacientes.size() > 1) {
            return null;  // Si hay más de uno, indicar que se necesita especificar más detalles
        }
        return pacientes.get(0);
    }

    // Validación de existencia de doctor
    public static Doctor validarDoctor(String nombreDoctor, IDoctorService doctorService) {
        List<Doctor> doctores = doctorService.findDoctorByName(nombreDoctor);
        if (doctores == null || doctores.isEmpty()) {
            return null;
        } else if (doctores.size() > 1) {
            return null;  // Si hay más de uno, indicar que se necesita especificar más detalles
        }
        return doctores.get(0);
    }
}
