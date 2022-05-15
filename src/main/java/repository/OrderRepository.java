package repository;

import domain.dtos.MedicationDTO;
import domain.entities.Medication;
import domain.entities.Order;
import domain.enums.OrderStatus;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

@Transactional
public class OrderRepository {
    private final SessionFactory sessionFactory;

    public OrderRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Order> getAllOrdersByStatusAndSection(OrderStatus orderStatus, String section) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            List<Order> orders = session.createQuery(
                    "select o " +
                            "from Order o " +
                            "join fetch o.medications om " +
                            "join fetch om.medication " +
                            "where o.status = :orderStatus and o.medicalSection = :section " +
                            "order by o.timestamp desc ",
                            Order.class
                    ).setParameter("orderStatus", orderStatus)
                    .setParameter("section", section)
                    .getResultList();
            session.getTransaction().commit();
            return orders;
        }
    }

    public void add(Order order, List<MedicationDTO> medicationDTOs) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            medicationDTOs.forEach(x -> {
                Medication medication = session.getReference(x.getMedication());
                Integer quantity = x.getQuantity();
                order.addMedication(medication, quantity);
            });
            session.persist(order);
            session.getTransaction().commit();
        }
    }

    public void modify(Order newOrder) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            Order oldOrder = session.getReference(newOrder);
            oldOrder.setMedicalSection(newOrder.getMedicalSection());
            oldOrder.setStatus(newOrder.getStatus());
            oldOrder.setTimestamp(newOrder.getTimestamp());
            session.persist(oldOrder);
            session.getTransaction().commit();
        }
    }
}
