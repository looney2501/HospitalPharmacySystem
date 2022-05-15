package repository;

import domain.entities.Medication;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Transactional
public class MedicationRepository {
    private final SessionFactory sessionFactory;

    public MedicationRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Medication> getAll() {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Medication> medications = session.createQuery(
                    "select m " +
                            "from Medication m "
                    , Medication.class).getResultList();
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

    public void modify(Medication newMedication) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Medication oldMedication = session.getReference(newMedication);
            oldMedication.setName(newMedication.getName());
            oldMedication.setDescription(newMedication.getDescription());
            oldMedication.setProducer(newMedication.getProducer());
            oldMedication.setStock(newMedication.getStock());
            session.persist(oldMedication);
            session.getTransaction().commit();
        }
    }

}