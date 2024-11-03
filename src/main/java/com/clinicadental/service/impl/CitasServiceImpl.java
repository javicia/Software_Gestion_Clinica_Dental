package com.clinicadental.service.impl;

import com.clinicadental.model.Dao.CitaDao;
import com.clinicadental.model.Entity.Cita;
import com.clinicadental.service.ICitasService;

import java.util.List;

public class CitasServiceImpl implements ICitasService {

    private CitaDao citaDao = new CitaDao();

    @Override
    public void saveCita(Cita cita) {
        citaDao.saveCita(cita);
    }

    @Override
    public void updateCita(Cita cita) {
        citaDao.updateCita(cita);
    }

    @Override
    public void deleteCita(Cita cita) {
        citaDao.deleteCita(cita);
    }

    @Override
    public List<Cita> getAllCitas() {
        return citaDao.getAllCitas();
    }

    @Override
    public Cita getCitaById(int id) {
        return citaDao.getCitaById(id);
    }
}
