package com.clinicadental.init;

import com.clinicadental.controller.paciente.PacienteAddEditController;
import com.clinicadental.controller.paciente.PacienteTableController;
import com.clinicadental.utils.HibernateUtil;
import com.clinicadental.view.paciente.PacienteForm;
import com.clinicadental.view.paciente.PacienteTable;

public class Main {
    public static void main(String[] args) {

        PacienteTable pacienteTableView = new PacienteTable();
        PacienteTableController pacienteTableController = new PacienteTableController(pacienteTableView);
        pacienteTableView.setVisible(true);

        // Añadir un shutdown hook para cerrar la SessionFactory al finalizar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            HibernateUtil.getSessionFactory().close();
        }));
    }

    }
