package com.clinicadental.utils;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Crea la SessionFactory desde hibernate.cfg.xml
            return (SessionFactory) new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            // Manejo de excepción+
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
