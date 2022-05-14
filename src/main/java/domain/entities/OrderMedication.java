package domain.entities;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "order_medications")
public class OrderMedication {
    @Embeddable
    public static class OrderMedicationId implements Serializable {
        @Column(name = "order_id")
        private Integer orderId;

        @Column(name = "medication_id")
        private Integer medicationId;

        public OrderMedicationId() {
        }

        public OrderMedicationId(Integer orderId, Integer medicationId) {
            this.orderId = orderId;
            this.medicationId = medicationId;
        }

        public Integer getOrderId() {
            return orderId;
        }

        public void setOrderId(Integer orderId) {
            this.orderId = orderId;
        }

        public Integer getMedicationId() {
            return medicationId;
        }

        public void setMedicationId(Integer medicationId) {
            this.medicationId = medicationId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            OrderMedicationId that = (OrderMedicationId) o;
            return Objects.equals(orderId, that.orderId) && Objects.equals(medicationId, that.medicationId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(orderId, medicationId);
        }
    }

    @EmbeddedId
    private OrderMedicationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("orderId")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("medicationId")
    private Medication medication;

    private Integer quantity;

    public OrderMedication() {
    }

    public OrderMedication(Order order, Medication medication, Integer quantity) {
        this.order = order;
        this.medication = medication;
        this.quantity = quantity;
        this.id = new OrderMedicationId(order.getId(), medication.getId());
    }

    public OrderMedicationId getId() {
        return id;
    }

    public void setId(OrderMedicationId id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Medication getMedication() {
        return medication;
    }

    public void setMedication(Medication medication) {
        this.medication = medication;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderMedication that = (OrderMedication) o;
        return Objects.equals(order, that.order) && Objects.equals(medication, that.medication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(order, medication);
    }
}
