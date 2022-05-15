package repository;

import domain.entities.Medication;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

public class MedicationRepository {
    private final SessionFactory sessionFactory;

    public MedicationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Medication> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Medication> medications = session.createQuery("select m from Medication m", Medication.class).getResultList();
            session.getTransaction().commit();
            return medications;
        }
    }

    public void add(Medication medication) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(medication);
            session.getTransaction().commit();
        }
    }

    public Medication find(Integer id) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Medication medication = session.find(Medication.class, id);
            session.getTransaction().commit();
            return medication;
        }
    }

    public void delete(Medication medication) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.remove(medication);
            session.getTransaction().commit();
        }
    }

}