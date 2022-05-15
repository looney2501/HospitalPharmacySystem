package domain.dtos;

import domain.entities.Medication;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;


public class MedicationDTO {
    private Medication medication;
    private Integer quantity;

    public MedicationDTO(Medication medication) {
        this.medication = medication;
        this.quantity = 1;
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
        MedicationDTO that = (MedicationDTO) o;
        return Objects.equals(medication, that.medication);
    }

    @Override
    public int hashCode() {
        return Objects.hash(medication);
    }
}
