package repository;

import domain.entities.Medication;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * 
 */
public class MedicationRepository {

    private final SessionFactory sessionFactory;

    public MedicationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    /**
     * 
     */
    public List<Medication> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Medication> medications = session.createQuery("from Medication", Medication.class)
                    .list();
            session.getTransaction().commit();
            return medications;
        }
    }

    /**
     * @param medication
     */
    public void add(Medication medication) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(medication);
            session.getTransaction().commit();
        }
    }

}