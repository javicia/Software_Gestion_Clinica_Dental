package com.clinicadental.model.Entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "Citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_cita;

    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Temporal(TemporalType.TIME)
    private Date hora;

    private String motivo;

    @ManyToOne
    @JoinColumn(name = "id_paciente")
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_doctor")
    private Doctor doctor;
}
