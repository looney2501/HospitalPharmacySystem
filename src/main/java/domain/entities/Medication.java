package domain.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "medications")
public class Medication {
    @Id
    @GeneratedValue(generator = "incrementor")
    @GenericGenerator(name = "incrementor", strategy = "increment")
    private Integer id;

    private String name;

    private String producer;

    private Integer stock;

    private String description;

    @OneToMany(mappedBy = "medication", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderMedication> orders = new ArrayList<>();

    public Medication() {
    }

    public Medication(String name, String producer, Integer stock, String description) {
        this.name = name;
        this.producer = producer;
        this.stock = stock;
        this.description = description;
    }

    public Medication(Integer id, String name, String producer, Integer stock, String description) {
        this.id = id;
        this.name = name;
        this.producer = producer;
        this.stock = stock;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<OrderMedication> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderMedication> orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Medication that = (Medication) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}