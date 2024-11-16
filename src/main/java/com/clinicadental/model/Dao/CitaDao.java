package com.clinicadental.model.Dao;

import com.clinicadental.model.Entity.Cita;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CitaDao {

    public void saveCita(Cita cita) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(cita);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateCita(Cita cita) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(cita);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteCita(Cita cita) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            Cita citaToDelete = session.get(Cita.class, cita.getId_cita());

            if (citaToDelete != null) {
                session.delete(citaToDelete);
                System.out.println("Cita con ID " + cita.getId_cita() + " eliminada de la base de datos."); // Depuración
                transaction.commit();
            } else {
                System.out.println("No se encontró la cita en la base de datos.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }


    public Cita getCitaById(int id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Cita.class, id);
        }
    }

    public List<Cita> getAllCitas() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("SELECT c FROM Cita c JOIN FETCH c.paciente JOIN FETCH c.doctor", Cita.class).list();
        }
    }


    public Paciente findByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Paciente WHERE nombre = :name", Paciente.class)
                    .setParameter("name", name)
                    .uniqueResult();
        }
    }

    public void deleteCitasByDoctorId(int doctorId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Eliminar todas las citas asociadas al doctor
            session.createQuery("DELETE FROM Cita WHERE doctor.idDoctor = :doctorId")
                    .setParameter("doctorId", doctorId)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteCitasByPacienteId(int pacienteId) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            // Eliminar todas las citas asociadas al paciente
            session.createQuery("DELETE FROM Cita WHERE paciente.idPaciente = :pacienteId")
                    .setParameter("pacienteId", pacienteId)
                    .executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }



}
