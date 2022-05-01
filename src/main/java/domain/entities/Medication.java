package domain.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

import java.util.*;

/**
 * 
 */
@Entity
@Table(name = "medications")
public class Medication {

    @Id
    @GeneratedValue(generator = "incrementor")
    @GenericGenerator(name = "incrementor", strategy = "increment")
    private Integer id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String producer;

    /**
     *
     */
    private Integer stock;

    /**
     *
     */
    private String description;

    /**
     * Default constructor
     */
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
}