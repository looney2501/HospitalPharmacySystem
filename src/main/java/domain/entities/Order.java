package domain.entities;

import domain.enums.OrderStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(generator = "incrementor")
    @GenericGenerator(name = "incrementor", strategy = "increment")
    private Integer id;

    @Column(name = "medical_section")
    private String medicalSection;

    private LocalDateTime timestamp;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderMedication> medications = new ArrayList<>();

    public Order() {
    }

    public Order(String medicalSection, LocalDateTime timestamp, OrderStatus status) {
        this.medicalSection = medicalSection;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Order(Integer id, String medicalSection, LocalDateTime timestamp, OrderStatus status) {
        this.id = id;
        this.medicalSection = medicalSection;
        this.timestamp = timestamp;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMedicalSection() {
        return medicalSection;
    }

    public void setMedicalSection(String medicalSection) {
        this.medicalSection = medicalSection;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<OrderMedication> getMedications() {
        return medications;
    }

    public void setMedications(List<OrderMedication> medications) {
        this.medications = medications;
    }

    public void addMedication(Medication medication, Integer quantity) {
        OrderMedication orderMedication = new OrderMedication(this, medication, quantity);
        medications.add(orderMedication);
        medication.getOrders().add(orderMedication);
    }

    public void removeMedication(Medication medication) {
        for (Iterator<OrderMedication> iterator = medications.iterator(); iterator.hasNext(); ) {
            OrderMedication orderMedication = iterator.next();
            if (orderMedication.getOrder().equals(this) && orderMedication.getMedication().equals(medication)) {
                iterator.remove();
                orderMedication.getMedication().getOrders().remove(orderMedication);
                orderMedication.setMedication(null);
                orderMedication.setOrder(null);
            }
        }
    }
}
