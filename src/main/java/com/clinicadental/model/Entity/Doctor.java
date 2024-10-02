package com.clinicadental.model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Doctores")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id_doctor;
    private String nombre;
    private String apellidos;
    private Integer numColegiado;
    private String dni;
    private String telefono;
    private String direccion;
    private Integer codPostal;
    private String email;




    // Getters y setters


    public int getId_doctor() {
        return id_doctor;
    }

    public void setId_doctor(int id_doctor) {
        this.id_doctor = id_doctor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Integer getNumColegiado() {
        return numColegiado;
    }

    public void setNumColegiado(Integer numColegiado) {
        this.numColegiado = numColegiado;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Integer getCodPostal() {
        return codPostal;
    }

    public void setCodPostal(Integer codPostal) {
        this.codPostal = codPostal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
