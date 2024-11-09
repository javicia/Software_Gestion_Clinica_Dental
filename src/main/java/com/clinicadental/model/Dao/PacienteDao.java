package com.clinicadental.model.Dao;

import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.model.Entity.Paciente;
import com.clinicadental.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PacienteDao {

    public void savePaciente(Paciente paciente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(paciente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updatePaciente(Paciente paciente) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(paciente);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deletePaciente(Paciente paciente) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.delete(paciente);  // Eliminar el paciente
            transaction.commit();  // Confirmar la transacción
            System.out.println("Paciente eliminado correctamente.");
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();  // Deshacer cambios en caso de error
            }
            e.printStackTrace();
        } finally {
            session.close();  // Cerrar la sesión
        }
    }

    public List<Paciente> obtenerTodos() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Paciente", Paciente.class).list();
        }
    }

    public Paciente findByName(String fullName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Divide el nombre completo en nombre y apellidos
            String[] parts = fullName.split(", ");
            if (parts.length < 2) {
                // Si no hay suficientes partes para nombre y apellidos, retorna null
                return null;
            }
            String lastName = parts[0].trim();
            String firstName = parts[1].trim();

            return session.createQuery("FROM Paciente WHERE lower(trim(apellidos)) = :lastName AND lower(trim(nombre)) = :firstName", Paciente.class)
                    .setParameter("lastName", lastName.toLowerCase())
                    .setParameter("firstName", firstName.toLowerCase())
                    .uniqueResult();
        }
    }
    public List<Paciente> findListPacienteByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Paciente where nombre = :name", Paciente.class)
                    .setParameter("name", name)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
