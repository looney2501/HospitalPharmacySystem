package services;

import domain.entities.Medication;
import domain.entities.User;
import domain.enums.UserType;
import repository.MedicationRepository;
import repository.UserRepository;
import services.exceptions.ServicesException;
import services.validators.MedicationValidator;

import java.util.List;

/**
 * 
 */
public class Services {

    private final UserRepository userRepository;
    private final MedicationRepository medicationRepository;

    public Services(UserRepository userRepository, MedicationRepository medicationRepository) {
        this.userRepository = userRepository;
        this.medicationRepository = medicationRepository;
    }

    public User login(String username, String password) throws ServicesException {
        User user = userRepository.findByUsernameAndPassword(username, password);
        if (user == null) {
            throw new ServicesException("User does not exist!");
        }
        else {
            return user;
        }
    }

    public List<Medication> getAllMedications() {
        return medicationRepository.getAll();
    }

    public void addMedication(String name, String producer, Integer stock, String description) {
        Medication medication = new Medication(name, producer, stock, description);
        MedicationValidator.validate(medication);
        medicationRepository.add(medication);
    }

    public void deleteMedication(Integer id) {
        if (id == null) {
            throw new ServicesException("ID cannot be null!");
        }
        Medication medication = medicationRepository.find(id);
        if (medication == null) {
            throw new ServicesException("Medication does not exist!");
        }
        medicationRepository.delete(medication);
    }

}