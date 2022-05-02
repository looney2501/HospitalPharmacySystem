package services.validators;

import domain.entities.Medication;
import services.exceptions.ServicesException;

public interface MedicationValidator {
    static void validate(Medication medication) {
        if (medication.getName().equals("") || medication.getProducer().equals("") || medication.getStock() < 0 || medication.getDescription().equals("")) {
            throw new ServicesException("Complete all fields!");
        }
    }
}
