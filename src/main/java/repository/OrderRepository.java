package repository;

import domain.entities.Order;
import domain.enums.OrderStatus;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;

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
}
