package com.clinicadental.model.Dao;

import com.clinicadental.model.Entity.Doctor;
import com.clinicadental.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;


import java.util.List;

public class DoctorDao {

    public void saveDoctor(Doctor doctor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(doctor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void updateDoctor(Doctor doctor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(doctor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public void deleteDoctor (Doctor doctor) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.delete(doctor);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    public Doctor findByName(String fullName) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            // Divide el nombre completo en nombre y apellidos
            String[] parts = fullName.split(", ");
            if (parts.length < 2) {
                // Si no hay suficientes partes para nombre y apellidos, retorna null
                return null;
            }
            String lastName = parts[0].trim();
            String firstName = parts[1].trim();

            return session.createQuery("FROM Doctor WHERE lower(trim(apellidos)) = :lastName AND lower(trim(nombre)) = :firstName", Doctor.class)
                    .setParameter("lastName", lastName.toLowerCase())
                    .setParameter("firstName", firstName.toLowerCase())
                    .uniqueResult();
        }
    }




    public List<Doctor> getAllDoctor() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Doctor", Doctor.class).list();
        }
    }
    public List<Doctor> findDoctorByName(String name) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from Doctor where nombre = :name", Doctor.class)
                    .setParameter("name", name)
                    .list();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}
